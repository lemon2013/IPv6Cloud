/******************************************************************************
 * Copyright (C) 2017 Pengtian Yue
 * All Rights Reserved.
 * �����Ϊ����Խ�������ơ�δ��������ʽ����ͬ�⣬�����κθ��ˡ�����
 * ����ʹ�á����ơ��޸Ļ򷢲������.
 *****************************************************************************/
package com.cn.tbmsm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.cn.tbmsm.prmsm.PRMSM;
import com.cn.tbmsm.tree.FileIndexTree;

/**
 * @Description: TODO
 * @author Pengtian Yue
 * @since JDK1.7
 * @history 2017年1月23日 彭天越 新建
 */
public class TreeUtil {
	public static void writeTreeToFile(FileIndexTree index, String fileName) {
		File file = new File(fileName);
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(file);
			ObjectOutputStream objOut = new ObjectOutputStream(outputStream);
			objOut.writeObject(index);
			objOut.flush();
			objOut.close();
			// System.out.println("index写入文件成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("index写入文件失败");
			e.printStackTrace();
		}

	}

	public static FileIndexTree readTreeFromFile(String fileName) {
		FileIndexTree tree = null;
		File file = new File(fileName);
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			tree = (FileIndexTree) objectInputStream.readObject();
			objectInputStream.close();
			// System.out.println("读取成功");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tree;
	}
	@SuppressWarnings("unchecked")
	public static HashMap<String,Integer> readHashKeyFromFile(String fileName) {
		HashMap<String,Integer> map = null;
		File file = new File(fileName);
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			map = (HashMap<String,Integer>) objectInputStream.readObject();
			objectInputStream.close();
			// System.out.println("读取成功");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	public static PRMSM readPNodeFromFile(String fileName) {
		PRMSM nodelist = null;
		File file = new File(fileName);
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			nodelist = (PRMSM) objectInputStream.readObject();
			objectInputStream.close();
			// System.out.println("读取成功");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nodelist;
	}

	public static void writePNodeToFill(PRMSM queue, String fileName) {
		File file = new File(fileName);
		FileOutputStream outputStream;
		// list.add("dasd");
		try {
			outputStream = new FileOutputStream(file);
			ObjectOutputStream objOut = new ObjectOutputStream(outputStream);
			objOut.writeObject(queue);
			objOut.flush();
			objOut.close();
			// System.out.println("index写入文件成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("nodelist写入文件失败");
			e.printStackTrace();
		}
	}
	/**
	 * list转json
	 * @param lst
	 * @return
	 */
	public static JSONArray listToJson(List<RSID> lst) {
		JSONArray json = new JSONArray();
		for(RSID item : lst){
            JSONObject jo = new JSONObject();
            try {
            	jo.put("id", item.getFID());
				jo.put("score", item.getScore());
				jo.put("owner", item.getOwner());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            json.put(jo);
        }
		return json;
	}
	public static void main(String arg[]) throws IOException {
		ArrayList<Double> scoreList = new ArrayList<Double>();
		scoreList.add(9.8);
		scoreList.add(3.8);
		scoreList.add(19.8);

		Collections.sort(scoreList, Collections.reverseOrder());
		Iterator<Double> iterator = scoreList.iterator();
		while (iterator.hasNext()) {
			System.out.print(iterator.next() + " ");
		}
	}
}
