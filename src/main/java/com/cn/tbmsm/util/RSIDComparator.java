/******************************************************************************
 * Copyright (C) 2017 Pengtian Yue
 * All Rights Reserved.
 * �����Ϊ����Խ�������ơ�δ��������ʽ����ͬ�⣬�����κθ��ˡ�����
 * ����ʹ�á����ơ��޸Ļ򷢲������.
 *****************************************************************************/
package com.cn.tbmsm.util;

import java.util.Comparator;

/**
 * @Description: TODO
 * @author Pengtian Yue
 * @since JDK1.7
 * @history 2017��1��24�� ����Խ �½�
 */
public class RSIDComparator implements Comparator<RSID> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(RSID o1, RSID o2) {
		// TODO Auto-generated method stub
		
		if (o1.getScore() < o2.getScore()) {
			return 1;
		} else {
			return -1;
		}
	}

	

}
