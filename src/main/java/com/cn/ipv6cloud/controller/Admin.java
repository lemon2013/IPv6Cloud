package com.cn.ipv6cloud.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.twonote.rgwadmin4j.model.User;

import com.cn.ipv6cloud.ceph.api.CephAPI;
import com.cn.ipv6cloud.ceph.api.impl.CephAPIImpl;
import com.cn.ipv6cloud.ceph.api.model.ClusterStat;
import com.cn.ipv6cloud.ceph.api.model.MonInfo;
import com.cn.ipv6cloud.ceph.api.model.MonmapInfo;
import com.cn.ipv6cloud.ceph.api.model.OSDCommitApply;
import com.cn.ipv6cloud.ceph.api.model.OSDLocation;
import com.cn.ipv6cloud.ceph.api.model.PgPoolsStat;
import com.cn.ipv6cloud.ceph.api.model.PoolRWO;
import com.cn.ipv6cloud.ceph.api.model.OSDUti;
import com.cn.ipv6cloud.ceph.api.model.PGsOSD;
import com.cn.ipv6cloud.ceph.api.model.PoolStat;
import com.cn.ipv6cloud.ceph.s3.CephRGWAdmin;
import com.cn.ipv6cloud.ceph.s3.CephRGWS3;
import com.cn.ipv6cloud.ceph.s3.models.UserInfo;
import com.cn.ipv6cloud.permission.AdminPermission;
import com.cn.ipv6cloud.util.IPv6CloudUtils;
import com.cn.tbmsm.des.DES;
import com.cn.tbmsm.des.DESStr;
import com.cn.tbmsm.tbmom.TBMOM;
import com.cn.tbmsm.util.ConstantsUtil;
import com.cn.tbmsm.util.RSID;
import com.cn.tbmsm.util.TreeUtil;
import com.google.common.base.Strings;

import net.sf.json.JSONArray;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.cn.ipv6cloud.rsa.RSACoder;
import java.security.Key;
@Controller
@RequestMapping("/admin")
public class Admin {
	private static CephRGWS3 client;
	private static CephRGWAdmin admin;
	private static TBMOM tbmom;
	private static HashMap<String, Integer> map;
	private static KeyWordComputer kwc;
	private static CephAPI api;
	static {
		System.out.println("初始化索引树");
		tbmom = new TBMOM();
		System.out.println("初始化关键词集合");
		map = TreeUtil.readHashKeyFromFile(ConstantsUtil.ENCKEYWORDS_DIR);
		System.out.println("初始化关键词切分");
		kwc = new KeyWordComputer(1000);
		kwc.computeArticleTfidf("", "");
		System.out.println("初始化S3管理用户");
		admin = new CephRGWAdmin(ConstantsUtil.ADMIN_ACCESSKEY, ConstantsUtil.ADMIN_SECRETKEY,
				ConstantsUtil.ADMIN_ENDPOINT);
		System.out.println("初始化S3普通用户");
		client = new CephRGWS3(ConstantsUtil.ADMIN_ACCESSKEY, ConstantsUtil.ADMIN_SECRETKEY, ConstantsUtil.ENDPOINT);
		api = new CephAPIImpl(ConstantsUtil.CEPH_REST_API);
		System.out.println("初始化完成");
	}

	@RequestMapping("/")
	public String Initializendex(Model model) {
		return "redirect:ceph/index";
	}
    
	@AdminPermission
	@RequestMapping("/storage/index")
	public String Storage(Model model) {
		return "/storage/index";
	}
	@AdminPermission
	@RequestMapping("/storage/object")
	public String Object(Model model) {
		return "/storage/object";
	}
	@AdminPermission
	@RequestMapping("/setting/user")
	public String User(Model model) {
		return "/setting/user";
	}
	@AdminPermission
	@RequestMapping("/login/index")
	public String Login(Model model) {
		return "/index";
	}
	@AdminPermission
	@RequestMapping(value = "/login/index/check", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String loginCheck(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		String userData = request.getParameter("userData");
		JSONArray array = JSONArray.fromObject(userData);
		net.sf.json.JSONObject obj = (net.sf.json.JSONObject) array.get(0);// 获取一个拥有username和emailRSABytes的对象
		String username = obj.get("userName").toString();
		String emailRSA1 = obj.get("email1").toString();
		String emailRSA2 = obj.get("email2").toString();

		String publicKey;
		String privateKey;
		Map<String, Key> keyMap = RSACoder.initKey();
		publicKey = RSACoder.getPublicKey(keyMap);
		privateKey = RSACoder.getPrivateKey(keyMap);

		byte[] emailData1 = RSACoder.decryptByPrivateKey(emailRSA1, privateKey);
		String email1 = new String(emailData1);
		byte[] emailData2 = RSACoder.decryptByPrivateKey(emailRSA2, privateKey);
		String email2 = new String(emailData2);
		String email = email1 + email2;
		System.out.println(email);

		JSONObject jsonObject = new JSONObject();
		System.out.println(username);
		System.out.println(email);
		User user = admin.getUserInfo(username);
		try {
			if (user != null) {
				if (email == user.getEmail()) {
					// 创建session对象
					HttpSession session = request.getSession();
					// 把用户数据保存在session域对象中
					session.setAttribute("loginName", username);
					session.setMaxInactiveInterval(20 * 60);
					System.out.println(request.getContextPath());
					response.sendRedirect(request.getContextPath() + "/admin/storage/index");
					jsonObject.put("code", "1");
					jsonObject.put("msg", "登录成功");
				} else {
					jsonObject.put("code", "-2");
					jsonObject.put("msg", "用户名或邮箱不正确");
				}
			} else {
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "用户不存在");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping("/ceph/index")
	public String CephIndex(Model model) {
		ClusterStat clusterStat = api.getClusterStat();
		model.addAttribute("health", clusterStat.getHealth());
		model.addAttribute("version", api.getVersion());
		model.addAttribute("monnum", clusterStat.getMonmap().getNumber());
		model.addAttribute("monname", clusterStat.getMonmap().getName());
		model.addAttribute("osdnum", clusterStat.getOsdmap().getOsdNum());
		model.addAttribute("innum", clusterStat.getOsdmap().getInNum());
		model.addAttribute("upnum", clusterStat.getOsdmap().getUpNum());
		model.addAttribute("pgnum", clusterStat.getPgmap().getPgNum());
		model.addAttribute("pgavail", clusterStat.getPgmap().getAvail());
		model.addAttribute("objectnum", clusterStat.getPgmap().getObjects());
		model.addAttribute("poolnum", clusterStat.getPgmap().getPoolNum());
		model.addAttribute("data", clusterStat.getPgmap().getData());

		PgPoolsStat poolsStat = api.getPGPoolsInfo();
		for (int i = 0; i < poolsStat.getPoolsStat().size(); i++) {
			PoolStat poolStat = poolsStat.getPoolsStat().get(i);
			String str = IPv6CloudUtils.formatPoolStat(poolStat);
			model.addAttribute("Pool" + i, str);
		}
		return "/ceph/index";
	}
	@AdminPermission
	@RequestMapping(value = "/setting/user/add", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String AddUser(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String displayName = request.getParameter("displayName");
		String email = request.getParameter("email");
		String enabled = request.getParameter("enabled");
		String suspended = request.getParameter("suspended");
		String maxBuckets;
		Map<String, String> parameters = new HashMap<String, String>();
		if (enabled == "true") {
			maxBuckets = request.getParameter("max-buckets");
			if (!Strings.isNullOrEmpty(maxBuckets)) {
				parameters.put("max-buckets", maxBuckets);
			}
		}
		if (!Strings.isNullOrEmpty(displayName)) {
			parameters.put("display-name", displayName);
		}
		if (!Strings.isNullOrEmpty(email)) {
			parameters.put("email", email);
		}
		if (!Strings.isNullOrEmpty(suspended)) {
			parameters.put("suspended", suspended);
		}
		User user = admin.createS3User(userId, parameters);
		JSONObject jsonObject = new JSONObject();
		try {
			if (user == null) {
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "用户已经存在");
			} else {
				jsonObject.put("code", "1");
				jsonObject.put("msg", "添加用户成功");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/setting/user/del", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String delUser(@RequestParam(value = "dataArr[]", required = false, defaultValue = "") String[] dataArr,
			HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			for (int i = 0; i < dataArr.length; i++) {
				admin.removeUser(dataArr[i]);
			} // 方法没有返回值能这样写吗？
			jsonObject.put("code", "1");
			jsonObject.put("msg", "用户删除成功");
		} catch (Throwable e) {
			try {
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "用户删除失败");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/setting/user/edit", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String editUser(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String displayName = request.getParameter("displayName");
		String email = request.getParameter("email");
		String enabled = request.getParameter("enabled");
		Map<String, String> parameters = new HashMap<String, String>();
		// 麻烦你跟我解析一下下面这几行代码
		if (Strings.isNullOrEmpty(displayName)) {
			System.out.println(displayName);
			parameters.put("display-name", displayName);
		}
		if (Strings.isNullOrEmpty(email)) {
			parameters.put("email", email);
		}
		if (Strings.isNullOrEmpty(enabled)) {
			parameters.put("suspended", enabled);
		}
		JSONObject jsonObject = new JSONObject();
		try {
			admin.modifyUser(userId, parameters);
			jsonObject.put("code", "1");
			jsonObject.put("msg", "用户信息修改成功");
		} catch (Throwable e) {
			try {
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "用户信息修改失败");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/storage/bucket/add", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String AddBucket(HttpServletRequest request) {
		String bucketname = request.getParameter("name");
		JSONObject jsonObject = new JSONObject();
		try {
			client.createBucket(bucketname);
			jsonObject.put("code", "1");
			jsonObject.put("msg", "创建桶成功");
		} catch (Throwable e) {
			try {
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "创建桶失败");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/storage/bucket/del", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String delBucket(@RequestParam(value = "dataArr[]", required = false, defaultValue = "") String[] dataArr,
			HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			for (int i = 0; i < dataArr.length; i++) {
				String bucketName = dataArr[i];
				client.deleteBucket(bucketName);
			}
			jsonObject.put("code", "1");
			jsonObject.put("msg", "创建桶成功");
		} catch (Throwable e) {
			try {
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "创建桶失败");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		return jsonObject.toString();
	}

	/**
	 * 获取文件下载链接
	 * 
	 * @param request
	 * @return
	 */
	@AdminPermission
	@RequestMapping(value = "/storage/file/downloadurl", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String DownloadUrl(HttpServletRequest request) {
		String bucketname = request.getParameter("bucketname");
		String objName = request.getParameter("objname");
		System.out.println(bucketname + objName);
		JSONObject jsonObject = new JSONObject();
		try {
			String url = client.getFileUrl(bucketname, objName);
			jsonObject.put("code", "1");
			jsonObject.put("msg", "获取下载链接成功");
			jsonObject.put("url", url);
			jsonObject.put("filename", objName);
		} catch (Throwable e) {
			try {
				jsonObject.put("code", "-1");
				jsonObject.put("msg", "获取下载链接失败");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/storage/index/upload", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String Upload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
		String bucketName = request.getParameter("bucketName");
		JSONObject jsonObject = new JSONObject();
		try {
			String path = request.getSession().getServletContext().getRealPath("/");
			String newPath = path + new Date().getTime() + file.getOriginalFilename();
			System.out.println(newPath);
			File newFile = new File(newPath);
			file.transferTo(newFile);
			client.uploadObject(bucketName, file.getOriginalFilename(), newFile);
			jsonObject.put("msg", "成功");
			jsonObject.put("code", "1");
		} catch (JSONException | IllegalStateException | IOException e) {
			e.printStackTrace();
		}

		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/storage/index/delete", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String deleteFile(@RequestParam(value = "dataArr[]", required = false, defaultValue = "") String[] dataArr,
			HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			for (int i = 0; i < dataArr.length; i++) {
				String[] arr = dataArr[i].split("&");
				String bucketName = arr[0];
				String objectName = arr[1];
				client.deleteObject(bucketName, objectName);
			}
			jsonObject.put("msg", "成功");
			jsonObject.put("code", "1");
		} catch (JSONException | IllegalStateException e) {
			e.printStackTrace();
		}

		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/share/search", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String Search(@RequestParam(value = "info", required = false, defaultValue = "") String info,
			HttpServletRequest request) {
		// 初始化查询结果
		tbmom.getIndex().clearList();
		JSONObject jsonObject = new JSONObject();
		try {
			JSONObject myJsonObject = new JSONObject(info);
			String title = myJsonObject.getString("keys");
			int num = Integer.valueOf(myJsonObject.getString("num"));
			String content = "";
			KeyWordComputer kwc1 = new KeyWordComputer(1000);
			Collection<Keyword> keywords = kwc1.computeArticleTfidf(title, content);
			HashMap<String, Integer> map1 = TreeUtil.readHashKeyFromFile(ConstantsUtil.KEYWORDS_DIR);
			System.out.println(keywords);
			String keylst = "";
			String[] arr = new String[keywords.size()];
			int index = 0;
			for (Keyword key : keywords) {
				arr[index++] = key.getName();
				keylst += "  " + key.getName();
			}
			double[] q = new double[2295];
			int knum = 0;
			for (String key : arr) {
				if (map1.containsKey(key)) {
					q[map1.get(key)] = 1.0;
					knum++;
					System.out.println(map1.get(key));
				}
			}
			System.out.println("匹配关键词" + knum + "个");
			if (knum > 0) {
				tbmom.getIndex().GDFS(tbmom.getIndex().getRoot(), q, num);
			}
			ArrayList<RSID> rList = tbmom.getIndex().getrList();
			jsonObject.put("keywords", "[" + keylst + "]");
			jsonObject.put("tabledata", rList);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping("/share/index")
	public String Share(Model model) {
		return "/share/index";
	}
	@AdminPermission
	@RequestMapping("/storage/bucket")
	@ResponseBody
	public List<Bucket> Bucket(Model model) {
		return client.listBuckets();
	}
	@AdminPermission
	@RequestMapping("/storage/objectlist")
	@ResponseBody
	public List<S3ObjectSummary> ObjcetList(Model model) {
		return client.listAllfile();
	}
	@AdminPermission
	@RequestMapping("/share/sharelist")
	@ResponseBody
	public List<S3ObjectSummary> ShareList(Model model) {
		return client.listsharefile();
	}
	@AdminPermission
	@RequestMapping("/setting/userlist")
	@ResponseBody
	public List<UserInfo> Userlst(Model model) {
		return admin.listUser();
	}
	@AdminPermission
	@RequestMapping(value = "/ceph/index/rwrspeed", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String WROSpeed(HttpServletRequest request) {
		PoolRWO poolRWO = api.getPoolStats(".rgw.buckets");
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("msg", "成功");
			jsonObject.put("code", "1");
			jsonObject.put("rd", poolRWO.getRd());
			jsonObject.put("wr", poolRWO.getWr());
			jsonObject.put("op", poolRWO.getOp());
		} catch (JSONException | IllegalStateException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/ceph/osd/pgosd", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String PgOSD(@RequestParam(value = "id", required = false, defaultValue = "") String id,
			HttpServletRequest request) {
		List<String> osdLst = api.getOSDLst();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("osd_num", osdLst.size());
			PGsOSD pg = api.getPgByOSD(id);
			jsonObject.put("num_pg", pg.getPgOsd().size());
		} catch (JSONException | IllegalStateException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/ceph/osds", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String OSDs(HttpServletRequest request) {
		List<String> osdLst = api.getOSDLst();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("osds", osdLst);
		} catch (JSONException | IllegalStateException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/ceph/osd/osduti", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String OSDUti(@RequestParam(value = "id", required = false, defaultValue = "") String id,
			HttpServletRequest request) {
		List<OSDUti> osdLst = api.getOSDUtilization().getOsdLst();
		HashMap<String, OSDUti> map = new HashMap<String, OSDUti>();
		for (OSDUti osd : osdLst) {
			map.put(osd.getId(), osd);
		}
		JSONObject jsonObject = new JSONObject();
		try {
			if (map.containsKey(id)) {
				jsonObject.put("per", Double.parseDouble(map.get(id).getUseper()));
			} else {
				jsonObject.put("per", 0);
			}
		} catch (JSONException | IllegalStateException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping("/ceph/osdlst")
	@ResponseBody
	public List<OSDUti> OSDlst(Model model) {
		return api.getOSDUtilization().getOsdLst();
	}
	@AdminPermission
	@RequestMapping("/ceph/osd")
	public String OSDIndex(Model model) {
		return "ceph/osd";
	}
	@AdminPermission
	@RequestMapping(value = "/ceph/osd/var", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String VarOSD(@RequestParam(value = "id", required = false, defaultValue = "") String id,
			HttpServletRequest request) {
		List<OSDUti> osdLst = api.getOSDUtilization().getOsdLst();
		HashMap<String, OSDUti> map = new HashMap<String, OSDUti>();
		for (OSDUti osd : osdLst) {
			map.put(osd.getId(), osd);
		}
		JSONObject jsonObject = new JSONObject();
		try {
			if (map.containsKey(id)) {
				jsonObject.put("var", Double.parseDouble(map.get(id).getVar()));
			} else {
				jsonObject.put("var", 0);
			}
		} catch (JSONException | IllegalStateException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/ceph/osd/use", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String UseOSD(@RequestParam(value = "id", required = false, defaultValue = "") String id,
			HttpServletRequest request) {
		List<OSDUti> osdLst = api.getOSDUtilization().getOsdLst();
		HashMap<String, OSDUti> map = new HashMap<String, OSDUti>();
		for (OSDUti osd : osdLst) {
			map.put(osd.getId(), osd);
		}
		JSONObject jsonObject = new JSONObject();
		try {
			if (map.containsKey(id)) {
				String strUse = map.get(id).getUse().replaceAll("[a-zA-Z]", "");
				String strAva = map.get(id).getAvail().replaceAll("[a-zA-Z]", "");
				jsonObject.put("use", Double.parseDouble(strUse));
				jsonObject.put("ava", Double.parseDouble(strAva));
			} else {
				jsonObject.put("use", 0);
				jsonObject.put("ava", 0);
			}
		} catch (JSONException | IllegalStateException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/ceph/osd/perf", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String OSDPerf(@RequestParam(value = "id", required = false, defaultValue = "") String id,
			HttpServletRequest request) {
		List<OSDCommitApply> osdLst = api.getOSDPerfSummary().getOsdPerfLst();
		HashMap<String, OSDCommitApply> map = new HashMap<String, OSDCommitApply>();
		for (OSDCommitApply osd : osdLst) {
			map.put(osd.getOsdId(), osd);
		}
		JSONObject jsonObject = new JSONObject();
		try {
			if (map.containsKey(id)) {
				String strApp = map.get(id).getFsApplyLatency();
				String strComm = map.get(id).getFsCommitLatency();
				jsonObject.put("apply", Double.parseDouble(strApp));
				jsonObject.put("commit", Double.parseDouble(strComm));
			} else {
				jsonObject.put("apply", 0);
				jsonObject.put("commit", 0);
			}
		} catch (JSONException | IllegalStateException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/ceph/index/objnum", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String ObjectCluster(HttpServletRequest request) {
		ClusterStat clusterStat = api.getClusterStat();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("msg", "成功");
			jsonObject.put("code", "1");
			jsonObject.put("obj_num", Integer.valueOf(clusterStat.getPgmap().getObjects()));
		} catch (JSONException | IllegalStateException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/ceph/index/use", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String DataCluster(HttpServletRequest request) {
		ClusterStat clusterStat = api.getClusterStat();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("msg", "成功");
			jsonObject.put("code", "1");
			String str = clusterStat.getPgmap().getUsed();
			jsonObject.put("use", IPv6CloudUtils.formatSize(str));
		} catch (JSONException | IllegalStateException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/ceph/osd/loca", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String OSDLoca(@RequestParam(value = "id", required = false, defaultValue = "") String id,
			HttpServletRequest request) {
		OSDLocation osdLoca = api.getOSDLocation(Integer.valueOf(id));

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("ip", osdLoca.getIp());
			jsonObject.put("host", osdLoca.getCrushLocation().getHost());
			jsonObject.put("root", osdLoca.getCrushLocation().getRoot());
		} catch (JSONException | IllegalStateException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/ceph/index/moninfo", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String MonInfo(HttpServletRequest request) {
		ClusterStat clusterStat = api.getClusterStat();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("msg", "成功");
			jsonObject.put("code", "1");
			MonmapInfo mon = clusterStat.getMonmap();
			List<MonInfo> lst = mon.getMons();
			StringBuffer sb = new StringBuffer();
			for (MonInfo item : lst) {
				sb.append(item.getName());
				sb.append("\n");
			}
			jsonObject.put("moninfo", sb.toString());
		} catch (JSONException | IllegalStateException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/ceph/index/osdinfo", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String OSDInfo(HttpServletRequest request) {
		List<String> osdLst = api.getOSDLst();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("msg", "成功");
			jsonObject.put("code", "1");
			StringBuffer sb = new StringBuffer();
			for (String item : osdLst) {
				OSDLocation osdLoca = api.getOSDLocation(Integer.valueOf(item));
				sb.append(osdLoca.getCrushLocation().getHost() + "=");
				sb.append(osdLoca.getIp());
				sb.append("\n");
			}
			jsonObject.put("osdinfo", sb.toString());
		} catch (JSONException | IllegalStateException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping("/share/sstorage")
	public String SStorage(Model model) {
		return "/share/sstorage";
	}
	@AdminPermission
	@RequestMapping(value = "/share/getkeywords", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String Keywords(@RequestParam(value = "info", required = false, defaultValue = "") String info,
			HttpServletRequest request) {
		StringBuffer sb = new StringBuffer("");
		System.out.println(info);
		JSONObject jsonObject = new JSONObject();
		try {
			JSONObject myJsonObject = new JSONObject(info);
			String strFields = myJsonObject.getString("fields");
			int num = Integer.valueOf(myJsonObject.getString("num"));
			KeyWordComputer kwc1 = new KeyWordComputer(num);
			Long time1 = System.nanoTime();
			Collection<Keyword> keywords = kwc1.computeArticleTfidf(strFields, "");
			Long time2 = System.nanoTime();
			for (Keyword k : keywords) {
				sb.append(k.getName());
				sb.append("\t");
			}
			double time = (time2 - time1) / 1000000;
			jsonObject.append("keywords", sb.toString());
			jsonObject.append("time", time);
			jsonObject.append("code", 1);
		} catch (JSONException e) {
			try {
				jsonObject.append("keywords", "输入格式出错");
				jsonObject.append("time", 0);
				jsonObject.append("code", 0);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			System.out.println(e);
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/share/encku1", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String EncKeywordUser1(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			String enck = tbmom.encKeywordUser1(keyword);
			jsonObject.append("enck", enck);
			jsonObject.append("code", 1);
		} catch (JSONException e) {
			System.out.println(e);
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/share/encku2", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String EncKeywordUser2(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			String enck = tbmom.encKeywordUser2(keyword);
			jsonObject.append("enck", enck);
			jsonObject.append("code", 1);
		} catch (JSONException e) {
			System.out.println(e);
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/share/equal", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String Equal(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			JSONObject myJsonObject = new JSONObject(keyword);
			String k1 = myJsonObject.getString("k1");
			String k2 = myJsonObject.getString("k2");
			boolean eq = tbmom.isEqual(k1, k2);
			jsonObject.append("eq", eq);
			jsonObject.append("code", 1);
		} catch (JSONException e) {
			System.out.println(e);
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/share/encstr", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String EncString(@RequestParam(value = "data", required = false, defaultValue = "") String data,
			HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			DESStr des = new DESStr("lemon");// 自定义密钥
			String eVal = des.encrypt(data);
			jsonObject.append("encV", eVal);
			jsonObject.append("code", 1);
		} catch (JSONException e) {
			System.out.println(e);
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/share/decstr", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String DecString(@RequestParam(value = "data", required = false, defaultValue = "") String data,
			HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			DESStr des = new DESStr("lemon");// 自定义密钥
			String eVal = des.decrypt(data);
			jsonObject.append("decV", eVal);
			jsonObject.append("code", 1);
		} catch (JSONException e) {
			System.out.println(e);
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/share/file/enc", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String EncFile(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			String path = request.getSession().getServletContext().getRealPath("/");
			String newPath = path + new Date().getTime() + file.getOriginalFilename();
			String fileName = "E" + new Date().getTime() + file.getOriginalFilename();
			String newEPath = path + fileName;
			File newFile = new File(newPath);
			file.transferTo(newFile);
			DES des = new DES();
			des.encryptFile(newPath, newEPath);
			newFile.delete();
			jsonObject.put("url", fileName);
			jsonObject.put("msg", "成功");
			jsonObject.put("code", "1");
		} catch (JSONException | IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping(value = "/share/file/dec", produces = { "application/text;charset=UTF-8" })
	@ResponseBody
	public String DecFile(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			String path = request.getSession().getServletContext().getRealPath("/");
			String newPath = path + new Date().getTime() + file.getOriginalFilename();
			String fileName = "D" + new Date().getTime() + file.getOriginalFilename();
			String newEPath = path + fileName;
			File newFile = new File(newPath);
			file.transferTo(newFile);
			DES des = new DES();
			des.decryptorFile(newPath, newEPath);
			newFile.delete();
			jsonObject.put("url", fileName);
			jsonObject.put("msg", "成功");
			jsonObject.put("code", "1");
		} catch (JSONException | IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}
	@AdminPermission
	@RequestMapping("/share/download")
	public ResponseEntity<byte[]> download(HttpServletRequest request, @RequestParam("filename") String filename,
			Model model) throws Exception {
		String path = request.getServletContext().getRealPath("/"); // 获取文件所在路径
		File file = new File(path + File.separator + filename);
		HttpHeaders headers = new HttpHeaders();
		String downloadFileName = new String(filename.getBytes("UTF-8"), "ISO-8859-1"); // 少了这句，可能导致下载中文文件名的文档，只有后缀名的情况
		headers.setContentDispositionFormData("attachment", downloadFileName);// 告知浏览器以下载方式打开
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);// 设置MIME类型
		ResponseEntity<byte[]> byt = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers,
				HttpStatus.CREATED);
		file.delete();
		return byt;
	}
}