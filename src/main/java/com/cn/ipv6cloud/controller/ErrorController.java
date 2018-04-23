/**
 *  Copyright (C) 2018 HUN.lemon. All right reserved.
 *
 *  
 *  https://lemon2013.github.io
 *
 *  created on 2018年4月20日 下午4:19:10
 *
 */
package com.cn.ipv6cloud.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * Title: ErrorController
 * </p>
 * <p>
 * Description: 
 * </p>
 * <p>
 * Company: HUN.lemon
 * </p>
 * @author lemon
 * @date 2018年4月20日
 * 
 */
@Controller  
@RequestMapping("error")  
public class ErrorController {  
    private static final String BASE_DIR = "error/";  
    @RequestMapping("401")  
    public String handle1(HttpServletRequest request,Model model){
    	HttpSession session = request.getSession();
    	String role = (String) session.getAttribute("role");
    	model.addAttribute("role", role);
        return BASE_DIR + "401";  
    }  
    @RequestMapping("404")  
    public String handle2(HttpServletRequest request,Model model){ 
    	HttpSession session = request.getSession();
    	String role = (String) session.getAttribute("role");
    	model.addAttribute("role", role);
        return BASE_DIR + "404";  
    }  
    @RequestMapping("500")  
    public String handle3(HttpServletRequest request,Model model){ 
    	HttpSession session = request.getSession();
    	String role = (String) session.getAttribute("role");
    	model.addAttribute("role", role);
        return BASE_DIR + "500";  
    }  
}  
