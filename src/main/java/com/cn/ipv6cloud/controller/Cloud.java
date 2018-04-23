package com.cn.ipv6cloud.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cn.ipv6cloud.ceph.s3.CephRGWS3;
import com.cn.tbmsm.des.DES;
import com.cn.tbmsm.des.DESStr;
import com.cn.tbmsm.tbmom.TBMOM;
import com.cn.tbmsm.util.ConstantsUtil;
import com.cn.tbmsm.util.RSID;
import com.cn.tbmsm.util.TreeUtil;


import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

@Controller
@RequestMapping("/cloud")
public class Cloud {
	private static CephRGWS3 client;
	private static TBMOM tbmom;
	private static HashMap<String, Integer> map;
	private static KeyWordComputer kwc;
	static {
		tbmom = new TBMOM();
		map = TreeUtil.readHashKeyFromFile(ConstantsUtil.ENCKEYWORDS_DIR);
		kwc = new KeyWordComputer(1000);
		kwc.computeArticleTfidf("", "");
	}
	@RequestMapping("/")
	public String Index(Model model) {
		return "redirect:storage/object";
	}
	@RequestMapping("/index")
	public String Init(@ModelAttribute("accessKey") String accessKey,@ModelAttribute("secretKey") String secretKey,Model model) {
		client = new CephRGWS3(accessKey, secretKey, ConstantsUtil.ENDPOINT);
		return "redirect:storage/object";
	}
	@RequestMapping("/storage/index")
	public String Storage(Model model) {
		return "/storagecloud/index";
	}

	@RequestMapping("/storage/object")
	public String Object(Model model) {
		return "/storagecloud/object";
	}
	@RequestMapping("/share/index")
	public String Share(Model model) {
		return "/sharecloud/index";
	}
	@RequestMapping("/share/sstorage")
	public String SStorage(Model model) {
		return "/sharecloud/sstorage";
	}
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

	@RequestMapping("/storage/bucket")
	@ResponseBody
	public List<Bucket> Bucket(Model model) {
		return client.listBuckets();
	}

	@RequestMapping("/storage/objectlist")
	@ResponseBody
	public List<S3ObjectSummary> ObjcetList(Model model) {
		return client.listAllfile();
	}

	@RequestMapping("/share/sharelist")
	@ResponseBody
	public List<S3ObjectSummary> ShareList(Model model) {
		return client.listsharefile();
	}
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