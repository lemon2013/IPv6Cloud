package com.cn.tbmsm.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import com.cn.tbmsm.prmsm.EncKeyWord;

public class Node implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8987609829716512260L;
	private String[] score;
	private int keywordSize;
	private String FID;
	private String FOwner;
	private Node leftNode;
	private Node rightNode;
	private ArrayList<EncKeyWord> enckeywordList;
	private ArrayList<FunctionY> functionList;
	public Node(int M) {
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

	public Node getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(Node leftNode) {
		this.leftNode = leftNode;
	}

	public Node getRightNode() {
		return rightNode;
	}

	public void setRightNode(Node rightNode) {
		this.rightNode = rightNode;
	}

	public int getKeywordSize() {
		return keywordSize;
	}

	public void setKeywordSize(int keywordSize) {
		this.keywordSize = keywordSize;
	}

}
