package com.cn.tbmsm.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class FileRead {
	public static void constractFileVector(String fileName, String keywordSets, String indexfile, String indexid)
			throws IOException {
		File index = new File(indexfile);
		FileWriter fw1 = new FileWriter(index.getAbsoluteFile());
		BufferedWriter bw1 = new BufferedWriter(fw1);
		File fileID = new File(indexid);
		FileWriter fw = new FileWriter(fileID.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		// HashMap<String, double[]> fileIndex = new HashMap<String,
		// double[]>();
		FileReader file = new FileReader(fileName);
		FileReader keywordSetFile = new FileReader(keywordSets);
		HashMap<String, Integer> keywordMap = new HashMap<String, Integer>();
		BufferedReader buf = new BufferedReader(keywordSetFile);
		int lineNum = 0;
		String readLine = "";
		while ((readLine = buf.readLine()) != null) {
			lineNum++;
			keywordMap.put(readLine.trim(), lineNum);
		}
		buf.close();
		int M = lineNum;
		BufferedReader bufFile = new BufferedReader(file);
		int fileNum = 0;
		String fileline = "";
		String filePath = "";
		while ((fileline = bufFile.readLine()) != null) {
			if (fileNum % 2 == 0) {
				filePath = fileline.trim();
			} else {
				double[] vector = new double[M];
				String strTemp = fileline.substring(1, fileline.length() - 1);
				String[] strArr = strTemp.split(",");
				for (int i = 0; i < strArr.length; i++) {
					String[] keyAndScore = strArr[i].split("=");
					double score = Double.valueOf(keyAndScore[1]);
					String str = keyAndScore[0].trim(); // 去掉空格
					if (keywordMap.containsKey(str)) {
						vector[keywordMap.get(str) - 1] = score;
					}
				}
				bw.write(filePath + "\n");
				for (int i = 0; i < M; i++) {
					bw1.write(vector[i] + "\t");
				}
				bw1.write("\n");
				// fileIndex.put(filePath, vector);
			}
			fileNum++;
		}
		bw.close();
		bw1.close();
		bufFile.close();
	}

	public static void main(String arg[]) throws IOException {
		Long time2 = System.nanoTime();
		FileRead.constractFileVector("E:/IPv6File/tfidf/TF-IDF2kfp5.txt", "E:/IPv6File/tfidf/TopKeyword2kfp5.txt",
				"E:/IPv6File/tfidf/2295k2kfp5.txt", "E:/IPv6File/tfidf/fileID2k.txt");
		Long time3 = System.nanoTime();
		double t1 = (time3 - time2) / 1000000;
		System.out.println("时间" + t1 + "ms");
	}
}
