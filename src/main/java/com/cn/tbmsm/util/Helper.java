package com.cn.tbmsm.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;

public class Helper {
	public static double function(int t, int r, int l) {
		double result = 0;
		for (int j = 0; j <= t; j++) {
			for (int k = 0; k <= t; k++) {
				double re = 0.0;
				for (int i = 1; i <= j - 2; i++) {
					re += 1 + l;
				}
				re = re * r;
				result += j * k * (((1 + l) ^ (j - 1)) + re);
			}
		}
		System.out.println(result);
		return result;

	}

	public static double log(double value, double base) {
		return Math.log(value) / Math.log(base);
	}

	public static double aoppf(double x, double y, int t, int r, int l,double rof) {
		double result = 0.0;
		for (int j = 0; j <= t; j++) {
			for (int k = 0; k <= t; k++) {
				result += 0.0000000000001*j * k * m(x, j, r, l) * m(y, k, r, l);
			}
		}
		result += rof;
		return result;
	}
	public static double aoppfY(double x, int t, int r, int l,double rof){
		double result = 0.0;
		
		return result;
	}
	public static double m(double x, double j, int r, int l) {
		double result = 0.0;
		if (j == 0) {
			result = 1;
		} else if (j == 1) {
			result = x;
		} else {
			result = (m(x, j - 1, r, l) + r * x) * (1 + l);
		}
		return result;
	}
	/** 
     * @param key 
     * @param prime 
     *            31 131 1313 13131 131313 etc.. 
     * @return 
     */  
    public static int BKDRHash(String key, int prime) {  
        int hash = 0;  
        int n = key.length();  
        for (int i = 0; i < n; ++i)  
            hash = prime * hash + key.charAt(i);  
        return (hash & 0x7FFFFFFF);  
    }
    public void isEmpty(String indexFile,int size) throws IOException{
    	FileReader file = new FileReader(indexFile);
    	int[] flag = new int[size];
		BufferedReader bufFile = new BufferedReader(file);
		String vector = "";
		while ((vector = bufFile.readLine()) != null) {
			String[] score = vector.split("\\s");
			for(int i = 0; i < size; i++){
				double b = 0.0;
				BigDecimal data2 = new BigDecimal(b);
				BigDecimal data1 = new BigDecimal(Double.parseDouble(score[i]));
				int result = data1.compareTo(data2);
				if(result!=0){
					flag[i]=1;
				}
			}
		}
		bufFile.close();
		for(int i=0;i<size;i++){
			if(flag[i]==0){
				System.out.println(i+1);
			}
		}
    }
	public static void main(String[] args) throws IOException {
		Helper helper = new Helper();
//		System.out.println(helper.BKDRHash("S151000904", 31));
		// helper.function(10, 17, 23);
//		 double l = helper.log(helper.function(3, 5, 8), 2);
//		 System.out.println(l);
//		 System.out.println(Math.pow(2, 7));
//		 System.out.println(Math.pow(2, 8));
//		double l = helper.aoppf(0.0134, helper.BKDRHash("S151000904", 31), 3, 5, 7,200);
//		System.out.println(l);
//		System.out.println(helper.m(1, 3, 3, 7));
//		helper.m(x, j, r, l)
//		double l2 = helper.aoppf(1, 0.46, 3, 5, 7,200);
//		System.out.println(l2);
//		double l3 = helper.aoppf(0.0004, 2, 3, 5, 7);
//		System.out.println(l3);
//		double l4 = helper.aoppf(0.4, 2, 3, 5, 7);
//		System.out.println(l4);
		PrintStream  out= new PrintStream ("E:/result/4000keywords/empty.txt");
		System.setOut(out);  
		helper.isEmpty("E:/result/4000keywords/2500s.txt", 4000);
	}
}
