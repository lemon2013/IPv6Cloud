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

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cn.ipv6cloud.permission.AdminPermission;

import java.lang.reflect.Method;

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
public class RoleInterceptor implements HandlerInterceptor {

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
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 获取请求的URL
		boolean isPass = true;
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			if (method.getAnnotation(AdminPermission.class) != null) {
				isPass = isAdmin(request);
			}
		}
		if (!isPass) {// 未授权，返回401信息
			request.getRequestDispatcher("error/401").forward(request, response);
			return false;
		}
		return true;
	}

	private boolean isAdmin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("role");
		if ("admin".equals(role)) {
			return true;
		}
		return false;
	}
}
