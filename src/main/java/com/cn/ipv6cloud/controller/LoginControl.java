/**
 *  Copyright (C) 2018 HUN.lemon. All right reserved.
 *
 *  
 *  https://lemon2013.github.io
 *
 *  created on 2018年4月19日 下午4:52:56
 *
 */
package com.cn.ipv6cloud.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.twonote.rgwadmin4j.model.User;

import com.cn.ipv6cloud.ceph.s3.CephRGWAdmin;
import com.cn.tbmsm.util.ConstantsUtil;

/**
 * <p>
 * Title: LoginControl
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: HUN.lemon
 * </p>
 * 
 * @author lemon
 * @date 2018年4月19日
 * 
 */
@Controller
public class LoginControl {
	private static CephRGWAdmin admin;
	static {
		admin = new CephRGWAdmin(ConstantsUtil.ADMIN_ACCESSKEY, ConstantsUtil.ADMIN_SECRETKEY,
				ConstantsUtil.ADMIN_ENDPOINT);
	}

	/**
	 * 
	 * <p>
	 * Title: login
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param session
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login")
	public String login(HttpSession session, String username,String password,HttpServletRequest request,RedirectAttributes model) throws Exception {
		// 在Session里保存信息
		User userInfo = admin.getUserInfo(username);
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String msg = "";
		if(userInfo!=null) {
			if (password.equals(userInfo.getEmail())) {
				session.setAttribute("username", username);
				session.setAttribute("date", dFormat.format(new Date()));
				if("lemon".equals(username)) {
					session.setAttribute("role", "admin");
					return "redirect:admin/ceph/index";
				}
				session.setAttribute("role", "cloud");
				model.addFlashAttribute("accessKey", userInfo.getS3Credentials().get(0).getAccessKey());
				model.addFlashAttribute("secretKey", userInfo.getS3Credentials().get(0).getSecretKey());
				// 重定向
				return "redirect:cloud/index";
			} else {
				System.out.println("密码错误");
				msg = "密码错误";
			}
		}else {
			System.out.println("用户不存在");
			msg = "用户不存在";
			
		}
		model.addFlashAttribute("msg", msg);
		request.setAttribute("msg",msg);
		return "login";
	}

	/**
	 * 
	 * <p>
	 * Title: logout
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) throws Exception {
		// 清除Session
		session.invalidate();

		return "redirect:index";
	}
	@RequestMapping(value = "/err")
	public String Err(Model model,@ModelAttribute("msg")String msg) throws Exception {
		model.addAttribute("msg",msg);
		return "err";
	}
}
