/******************************************************************************
 * Copyright (C) 2016 Pengtian Yue
 * All Rights Reserved.
 * �����Ϊ����Խ�������ơ�δ��������ʽ����ͬ�⣬�����κθ��ˡ�����
 * ����ʹ�á����ơ��޸Ļ򷢲������.
 *****************************************************************************/
package com.cn.tbmsm.util;

import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

/**
 * @Description:
 * @author Pengtian Yue
 * @since JDK1.7
 */
public class ConstantsUtil {
	public static Pairing pairing = PairingFactory.getPairing("a.properties");
	public static final byte[] SYSTEM_BASE = { 114, -59, -58, 39, -57, 13, -66, -45, 30, -18, -105, -61, 84, 102, -122,
			122, 14, 122, -10, 50, 29, -127, 43, -23, -101, -76, 66, -47, -67, -118, -59, 12, -35, -81, 105, 122, -114,
			12, 46, 75, 0, 13, -93, 31, -1, 86, 41, 120, 32, -42, 105, 76, 94, -115, 38, 64, -65, -50, 51, -17, -61,
			-106, 116, 118, 2, 79, -51, 99, 101, -33, -81, 92, 3, -37, 10, 115, -75, 105, 44, -6, 23, 45, -55, 71, -4,
			41, -23, 41, -113, -123, -83, 73, -30, -79, -41, -41, -101, 81, 80, -108, -42, 117, 21, 84, -42, 21, 7, 1,
			-37, -54, 27, 107, -40, 98, 64, 12, 21, 71, -68, 93, -49, -2, -101, -76, -25, 3, 31, -120 };
	public static final byte[] SYSTEM_RS = { 63, 103, -26, -47, -16, -27, 36, 27, -107, -125, -11, -9, -27, -104, 35,
			35, -31, 66, -80, -107 };
	public static final byte[] USER1 = { 11, -99, 23, -11, 101, -37, -96, -40, 77, -22, -21, 34, -113, 4, 7, -128, -94,
			-81, 40, -114 };
	public static final byte[] USER2 = { 1, 65, -78, 76, 61, 24, 73, -57, 36, 104, -108, 44, 28, -5, 14, -15, -71, -47,
			-34, 93 };
	public static final byte[] USER3 = { 96, 85, -1, 11, 32, 118, -2, -99, 0, -92, 31, -7, -32, -126, -78, 109, -5, 102,
			65, -65 };
	public static final byte[] USER4 = { 38, -108, -89, -10, -18, 51, 85, -97, 108, -9, -102, -124, -25, 0, -21, 48, 17,
			-104, 110, -55 };
	public static final byte[] USER5 = { 103, 21, 47, -15, -18, -116, -12, -2, -118, 79, 27, 106, -106, -67, -34, -24,
			-95, 33, 42, 10 };
	/**
	 * dir根据目录配置
	 */
	public static final String INDEXTREE_DIR = "C:/Program Files/encfile/indexTree2295k2kfp5.txt";
	public static final String ENCKEYWORDS_DIR = "C:/Program Files/encfile/hashTopKeyword2kfp5.txt";
	public static final String KEYWORDS_DIR = "C:/Program Files/encfile/mapnoencTopKeyword2kfp5.txt";
	public static final String CONFIG_DIR = "C:/Program Files/encfile/a.properties";
//	public static final String ADMIN_ACCESSKEY = "29YAB6D3BVRBQQDFVLHI";
//	public static final String ADMIN_SECRETKEY = "QVPTxEvZHxQJhNdR58tZCfsgyP37jOKBKiPg1TaU";
//	public static final String ENDPOINT = "http://[2001:250:4402:2001:20c:29ff:fe25:8888]";
//	public static final String ADMIN_ENDPOINT = "http://[2001:250:4402:2001:20c:29ff:fe25:8888]/admin";
//	public static final String CEPH_REST_API = "http://[2001:250:4402:2001:20c:29ff:fe25:8888]:5001/api/v0.1/";
	
	 public static final String ADMIN_ACCESSKEY = "1IDIU3TJ8JSRMFGY23F4";
	 public static final String ADMIN_SECRETKEY = "FAPYWItKoCHjSZYzjamtjpRdU1bP0kgMowQa2SaU";
	 public static final String ENDPOINT = "http://[2001:da8:270:2018:f816:3eff:fe9d:44dc]:7480";
	 public static final String ADMIN_ENDPOINT = "http://[2001:da8:270:2018:f816:3eff:fe9d:44dc]:7480/admin";
	 public static final String CEPH_REST_API = "http://[2001:da8:270:2018:f816:3eff:fe9d:44dc]:80/api/v0.1/";
	
}
