/**
 *  Copyright (C) 2018 HUN.lemon. All right reserved.
 *
 *  
 *  https://lemon2013.github.io
 *
 *  created on 2018年3月10日 下午5:35:51
 *
 */
package com.cn.ipv6cloud.util;

import com.cn.ipv6cloud.ceph.api.model.PoolStat;

/**
 * <p>
 * Title: IPv6CloudUtils
 * </p>
 * <p>
 * Description: 
 * </p>
 * <p>
 * Company: HUN.lemon
 * </p>
 * @author lemon
 * @date 2018年3月10日
 * 
 */
public class IPv6CloudUtils {
	/**
	 * 
	 * <p>
	 * Title: formatPoolStat
	 * </p>
	 * <p>
	 * Description: 
	 * </p>
	 * @param poolStat
	 * @return
	 */
	public static String formatPoolStat(PoolStat poolStat) {
		StringBuffer sb = new StringBuffer("[");
		sb.append(poolStat.getLogSize()+",");
		sb.append(poolStat.getUp()+",");
		sb.append(poolStat.getStatSum().getNumObj()+",");
		sb.append(poolStat.getStatSum().getNumObjDirty()+",");
		sb.append(poolStat.getStatSum().getNumRead()+",");
		sb.append(poolStat.getStatSum().getNumWrite()+",");
		sb.append(poolStat.getStatSum().getNumObjRecovered()+",");
		sb.append(poolStat.getStatSum().getNumScrubErr()+",");
		sb.append(poolStat.getStatSum().getNumDeepScrubErr()+",");
		sb.append(poolStat.getStatSum().getNumObjMisplaced()+",");
		sb.append(poolStat.getStatSum().getNumObjMissPrimary()+",");
		sb.append(poolStat.getStatSum().getNumObjUnfound()+"]");
		
		return sb.toString();
	}
	public static long formatSize(String str) {
		long size = 0;
		if(str.contains("MB")) {
			size=Long.valueOf(str.replaceAll("[a-zA-Z]", "").trim());
		}
		return size;
		
	}
}
