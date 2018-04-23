package com.cn.keywords;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;

import com.cn.keywords.util.EncodingDetect;
import com.cn.tbmsm.util.BigDecimalUtil;
import com.cn.tbmsm.util.ValueComparator;

public class ANSJTest {
	private static ArrayList<String> FileList = new ArrayList<String>();

	/**
	 * 判断字符串是否为中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!isChinese(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符是否为中文
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {

		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS

				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS

				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A

				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION

				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION

				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

			return true;

		}

		return false;

	}

	public static List<String> readDirs(String filepath) throws FileNotFoundException, IOException {
		try {
			File file = new File(filepath);
			if (file.isDirectory()) {
				String[] flist = file.list();
				for (int i = 0; i < flist.length; i++) {
					File newfile = new File(filepath + "\\" + flist[i]);
					if (!newfile.isDirectory()) {
						FileList.add(newfile.getAbsolutePath());
					} else if (newfile.isDirectory()) // if file is a
														// directory,call
														// ReadDirs
					{
						readDirs(filepath + "\\" + flist[i]);
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return FileList;
	}

	/**
	 * 获取文件内容
	 * 
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public static String getFileContent(String filepath) throws FileNotFoundException, IOException {
		StringBuffer strSb = new StringBuffer();
		// 获取文件编码方式
		String fileEncode = EncodingDetect.getJavaEncode(filepath);
		System.out.println(fileEncode);
		InputStreamReader inStrR = new InputStreamReader(new FileInputStream(filepath), fileEncode); // byte
		BufferedReader br = new BufferedReader(inStrR);
		String line = br.readLine();
		while (line != null) {
			strSb.append(line).append("\r\n");
			line = br.readLine();
		}
		br.close();
		return strSb.toString();
	}

	public static void tf_idf(List<String> filelst) throws IOException {
		File fileresult = new File("src/main/java/com/cn/keywords/TF-IDF2kfp5.txt");
		File topkey = new File("src/main/java/com/cn/keywords/TopKeyword2kfp5.txt");
		FileWriter fw1 = new FileWriter(topkey.getAbsoluteFile());
		BufferedWriter bw1 = new BufferedWriter(fw1);
		FileWriter fw = new FileWriter(fileresult.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		KeyWordComputer kwc = new KeyWordComputer(100);
		HashMap<String, Double> topKeyword = new HashMap<String, Double>();
		HashMap<String, HashMap<String, Double>> resTfIdf = new HashMap<String, HashMap<String, Double>>();
		String title = "";
		String content = "";
		for (String file : filelst) {
			System.out.println(file);
			int keynum = 0;//取每个文件前五个关键词
			HashMap<String, Double> tfidf = new HashMap<String, Double>();
			try {
				content = getFileContent(file);
				title = new File(file).getName();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 获取关键词与得分
			Collection<Keyword> keywords = kwc.computeArticleTfidf(title, content);
			for (Keyword k : keywords) {
				double score = BigDecimalUtil.round(k.getScore(), 4);
				String key = k.getName().trim();
				// 去掉乱码关键词
				if (isChinese(key)&&(keynum<5)) {
					keynum++;
					tfidf.put(k.getName(), score);
					if (topKeyword.get(key) == null) {
						topKeyword.put(key, score);
					} else {
						BigDecimal data1 = new BigDecimal(score);
						BigDecimal data2 = new BigDecimal(topKeyword.get(key));
						if (BigDecimalUtil.compare(data1, data2) == 1) {
							topKeyword.put(key, score);
						}
					}
				}
			}
			Map<String, Double> sorted = ValueComparator.sortByValues(tfidf);
			if (sorted.size() > 0) {
				bw.write(title + "\n");
				bw.write(sorted.toString() + "\n");
			}
			// resTfIdf.put(title, tfidf);
		}
		bw.close();
		Map<String, Double> sortedTop = ValueComparator.sortByValues(topKeyword);
		Iterator<Entry<String, Double>> iterSort = sortedTop.entrySet().iterator();
		while (iterSort.hasNext()) {
			Map.Entry entry = (Map.Entry) iterSort.next();
			String word = entry.getKey().toString();
			String score = entry.getValue().toString();
			bw1.write(word+ "\n");
		}
		bw1.close();
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
//		String file = "E:/IPv6File/2000";
//		List<String> filelst = readDirs(file);
//		Long time1 = System.nanoTime();
//		tf_idf(filelst);
//		Long time2 = System.nanoTime();
//		double t3 = (time2 - time1) / 1000000;
//		System.out.printf("时间\n" + t3+"ms");
		KeyWordComputer kwc = new KeyWordComputer(100);
		String title="环境保护法个人情况";
		String content = "";
		Collection<Keyword> keywords = kwc.computeArticleTfidf(title, content);
		String keylst = "";
		for(Keyword key:keywords) {
			keylst+=key.getName();
		}
		System.out.print(keylst);
	}

}
