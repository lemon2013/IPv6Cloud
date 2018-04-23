package com.cn.tbmsm.bdmrs;

import java.util.Random;

public class BDMRS {
	public int[] getS(int size){
		int[] S = new int[size];
		for(int i=0;i<size;i++){
			Random r=new Random(); 
			S[i]=r.nextInt(2);
		}
		return S;
	}
//	public int
	public static void main(String[] args) { 
		  java.util.Random r=new java.util.Random(); 
		for(int i=0;i<10;i++){ 
		    System.out.println(r.nextInt(2)); 
		} 
	}
}
