package com.cn.tbmsm.tree;

import java.io.Serializable;

import com.cn.tbmsm.util.Helper;

public class FunctionY implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7925757412270369592L;
	private int t;
	private int r;
	private int l;
	private double x;
	private double rof;
	public FunctionY(double x,int t,int r,int l,double rof){
		this.t =t;
		this.r = r;
		this.l = l;
		this.x=x;
		this.rof=rof;
	}
	public double computeByY(double y){
		double s=Helper.aoppf(x, y, t, r, l, rof);
		return s;
	}
}
