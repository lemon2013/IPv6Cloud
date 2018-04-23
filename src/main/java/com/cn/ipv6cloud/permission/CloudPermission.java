/**
 *  Copyright (C) 2018 HUN.lemon. All right reserved.
 *
 *  
 *  https://lemon2013.github.io
 *
 *  created on 2018年4月20日 下午3:00:39
 *
 */
package com.cn.ipv6cloud.permission;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
/**
 * <p>
 * Title: CloudPermission
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
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CloudPermission {
}
