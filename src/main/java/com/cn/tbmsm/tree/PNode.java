package com.cn.tbmsm.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import com.cn.tbmsm.prmsm.EncKeyWord;

public class PNode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5679958315594385292L;
	/**
	 * 
	 */
	private String[] score;
	private int keywordSize;
	private String FID;
	private String FOwner;
	private ArrayList<EncKeyWord> enckeywordList;
	private ArrayList<FunctionY> functionList;
	public PNode(int M) {
		keywordSize = M;
		score = new String[M];
		functionList = new ArrayList<FunctionY>();
		enckeywordList=new ArrayList<EncKeyWord>();
	}

	public ArrayList<EncKeyWord> getEnckeywordList() {
		return enckeywordList;
	}

	public void setEnckeywordList(ArrayList<EncKeyWord> enckeywordList) {
		this.enckeywordList = enckeywordList;
	}

	public ArrayList<FunctionY> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(ArrayList<FunctionY> functionList) {
		this.functionList = functionList;
	}

	public String getFOwner() {
		return FOwner;
	}

	public void setFOwner(String fOwner) {
		FOwner = fOwner;
	}

	public String[] getScore() {
		return score;
	}

	public void setScore(String[] socreFid) {
		score = Arrays.copyOf(socreFid, keywordSize);
	}

	public String getFID() {
		return FID;
	}

	public void setFID(String fID) {
		FID = fID;
	}

}
