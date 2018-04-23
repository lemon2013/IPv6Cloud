/**
 *  Copyright (C) 2018 HUN.lemon. All right reserved.
 *
 *  
 *  https://lemon2013.github.io
 *
 *  created on 2018年4月19日 下午4:56:24
 *
 */
package com.cn.ipv6cloud.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * Title: LoginInterceptor
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
public class LoginInterceptor implements HandlerInterceptor {

	/*
	 * (non-Javadoc) <p> Title: afterCompletion </p> <p> Description: </p>
	 * 
	 * @param arg0
	 * 
	 * @param arg1
	 * 
	 * @param arg2
	 * 
	 * @param arg3
	 * 
	 * @throws Exception
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc) <p> Title: postHandle </p> <p> Description: </p>
	 * 
	 * @param arg0
	 * 
	 * @param arg1
	 * 
	 * @param arg2
	 * 
	 * @param arg3
	 * 
	 * @throws Exception
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.
	 * http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc) <p> Title: preHandle </p> <p> Description: </p>
	 * 
	 * @param arg0
	 * 
	 * @param arg1
	 * 
	 * @param arg2
	 * 
	 * @return
	 * 
	 * @throws Exception
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.
	 * http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 获取请求的URL
		String url = request.getRequestURI();
		if (url.indexOf("login") >= 0) {
			return true;
		}
		if (url.indexOf("resources") >= 0) {
			return true;
		}
		if (url.indexOf("error") >= 0) {
			return true;
		}
		// 获取Session
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (username != null) {
			return true;
		}
		// 不符合条件的，跳转到登录界面
		request.getRequestDispatcher("/WEB-INF/admin/view/login.jsp").forward(request, response);
		return false;
	}
}
