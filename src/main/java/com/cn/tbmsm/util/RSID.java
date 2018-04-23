/******************************************************************************
 * Copyright (C) 2017 Pengtian Yue
 * All Rights Reserved.
 * �����Ϊ����Խ�������ơ�δ��������ʽ����ͬ�⣬�����κθ��ˡ�����
 * ����ʹ�á����ơ��޸Ļ򷢲������.
 *****************************************************************************/
package com.cn.tbmsm.util;

/**
 * @Description: TODO
 * @author Pengtian Yue
 * @since JDK1.7
 * @history 2017-1-24
 * */
public class RSID {
	private String FID;
	private double Score;
	private String Owner;
	public RSID(String id, double s) {
		FID = id;
		Score = s;
	}
	public RSID(String id, double s,String owner) {
		FID = id;
		Score = s;
		Owner = owner;
	}
	public void display(){
		System.out.println("文件ID="+FID+",得分="+Score+",拥有者="+Owner);
	}
	/**
	 * @return the fID
	 */
	public String getFID() {
		return FID;
	}

	/**
	 * @param fID
	 *            the fID to set
	 */
	public void setFID(String fID) {
		FID = fID;
	}

	/**
	 * @return the socre
	 */
	/**
	 * @return the score
	 */
	public double getScore() {
		return Score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(double score) {
		this.Score = score;
	}
	public String getOwner() {
		return Owner;
	}
	public void setOwner(String owner) {
		Owner = owner;
	}
}
