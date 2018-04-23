package com.cn.tbmsm.tbmom;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.cn.tbmsm.sha.SHAUtil;
import com.cn.tbmsm.tree.FileIndexTree;
import com.cn.tbmsm.util.ConstantsUtil;
import com.cn.tbmsm.util.JPBCUtil;
import com.cn.tbmsm.util.TreeUtil;
import com.google.common.base.Strings;

public class TBMOM {
	public static FileIndexTree tree;
	static {
//		 tree =
//		 TreeUtil.readTreeFromFile(ConstantsUtil.INDEXTREE_DIR);
		 tree = new FileIndexTree(1000);
	}

	public FileIndexTree getIndex() {
		return tree;
	}

	/**
	 * 关键词集合转HashMap存储
	 * 
	 * @param keywordSets
	 * @return
	 */
	public HashMap<String, Integer> keywordToHashMap(String keywordSets, String hashfile) {
		HashMap<String, Integer> kmap = new HashMap<String, Integer>();
		File file = new File(hashfile);
		ObjectOutputStream oos = null;
		FileReader keywordSetFile = null;
		try {
			keywordSetFile = new FileReader(keywordSets);
			oos = new ObjectOutputStream(new FileOutputStream(file));
			/** 读取关键词集合 */
			BufferedReader buf = new BufferedReader(keywordSetFile);
			String readLine = "";
			int val = 0;
			while ((readLine = buf.readLine()) != null) {
				String strHashMeg = SHAUtil.SHA512(readLine);
				kmap.put(strHashMeg, val++);
			}
			oos.writeObject(kmap);
			oos.close();
			keywordSetFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	/**
	 * 关键词集合转HashMap存储不加密
	 * 
	 * @param keywordSets
	 * @return
	 */
	public HashMap<String, Integer> keywordToMap(String keywordSets, String hashfile) {
		HashMap<String, Integer> kmap = new HashMap<String, Integer>();
		File file = new File(hashfile);
		ObjectOutputStream oos = null;
		FileReader keywordSetFile = null;
		try {
			keywordSetFile = new FileReader(keywordSets);
			oos = new ObjectOutputStream(new FileOutputStream(file));
			/** 读取关键词集合 */
			BufferedReader buf = new BufferedReader(keywordSetFile);
			String readLine = "";
			int val = 0;
			while ((readLine = buf.readLine()) != null) {
				kmap.put(readLine.trim(), val++);
			}
			oos.writeObject(kmap);
			oos.close();
			keywordSetFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	public double[] trapToVector(ArrayList<byte[]> trapList, String encKeyword)
			throws IOException, ClassNotFoundException {
		Pairing pairing = PairingFactory.getPairing("a.properties");
		File file = new File(encKeyword);
		FileInputStream inputStream = new FileInputStream(file);
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		ArrayList<byte[]> list = (ArrayList<byte[]>) objectInputStream.readObject();
		double[] vector = new double[list.size() / 2];
		Element E1 = pairing.getG1().newElement();
		Element E2 = pairing.getG1().newElement();
		Element E3 = pairing.getG1().newElement();
		Element E4 = pairing.getG1().newElement();
		for (int i = 0; i < trapList.size() - 1; i++) {
			E1.setFromBytes(trapList.get(i));
			E2.setFromBytes(trapList.get(i + 1));
			i++;
			for (int j = 0; j < list.size() - 1; j++) {
				E3.setFromBytes(list.get(j));
				E4.setFromBytes(list.get(j + 1));
				if (pairing.pairing(E1, E4).isEqual(pairing.pairing(E2, E3))) {
					vector[(j + 1) / 2] = 1.0;
					j = list.size() - 1;
				}
			}
		}
		return vector;
	}

	public ArrayList<byte[]> computeTrap(String[] strQuery) {
		ArrayList<byte[]> list = new ArrayList<byte[]>();
		Pairing pairing = PairingFactory.getPairing("a.properties");
		Element elment = pairing.getZr().newRandomElement();
		elment.setFromBytes(ConstantsUtil.USER1);
		Element elmentbase = pairing.getG1().newRandomElement();
		elmentbase.setFromBytes(ConstantsUtil.SYSTEM_BASE);
		for (int i = 0; i < strQuery.length; i++) {
			Random r = new Random();
			BigInteger ru = BigInteger.valueOf(Math.abs(r.nextInt()));
			Element elment1 = elment.duplicate().mul(ru);
			Element elment2 = elment1.duplicate().mul(JPBCUtil.hashToZp(pairing, strQuery[i].trim()));
			Element E1 = elmentbase.duplicate().powZn(elment2.duplicate());
			Element E2 = elmentbase.duplicate().powZn(elment1.duplicate());
			list.add(E1.duplicate().toBytes());
			list.add(E2.duplicate().toBytes());
		}
		return list;
	}
	public String encKeywordUser1(String str) {
		StringBuffer list = new StringBuffer("");
		Pairing pairing = PairingFactory.getPairing(ConstantsUtil.CONFIG_DIR);
		Element elment = pairing.getZr().newRandomElement();
		elment.setFromBytes(ConstantsUtil.USER1);
		Element elmentbase = pairing.getG1().newRandomElement();
		elmentbase.setFromBytes(ConstantsUtil.SYSTEM_BASE);
		Random r = new Random();
		BigInteger ru = BigInteger.valueOf(Math.abs(r.nextInt()));
		Element elment1 = elment.duplicate().mul(ru);
		Element elment2 = elment1.duplicate().mul(JPBCUtil.hashToZp(pairing, str.trim()));
		Element E1 = elmentbase.duplicate().powZn(elment2.duplicate());
		Element E2 = elmentbase.duplicate().powZn(elment1.duplicate());
		list.append("E1="+E1.toString()+"\n\n");
		list.append("E2="+E2.toString());
		return list.toString();
	}
	public boolean isEqual(String k1,String k2) {
		if(Strings.isNullOrEmpty(k1)||Strings.isNullOrEmpty(k2)) {
			return false;
		}
		Pairing pairing = PairingFactory.getPairing("a.properties");
		Element elment = pairing.getZr().newRandomElement();
		elment.setFromBytes(ConstantsUtil.USER1);
		Element elmentU2 = pairing.getZr().newRandomElement();
		elmentU2.setFromBytes(ConstantsUtil.USER2);
		Element elmentbase = pairing.getG1().newRandomElement();
		elmentbase.setFromBytes(ConstantsUtil.SYSTEM_BASE);
		Random r = new Random();
		BigInteger ru = BigInteger.valueOf(Math.abs(r.nextInt()));
		Element elment1 = elment.duplicate().mul(ru);
		Element elment2 = elment1.duplicate().mul(JPBCUtil.hashToZp(pairing, k1.trim()));
		Element E1 = elmentbase.duplicate().powZn(elment2.duplicate());
		Element E2 = elmentbase.duplicate().powZn(elment1.duplicate());
		BigInteger ro = BigInteger.valueOf(Math.abs(r.nextInt()));
		Element elment3 = elmentU2.duplicate().mul(ro);
		Element elment4 = elment3.duplicate().mul(JPBCUtil.hashToZp(pairing, k2.trim()));
		Element E3 = elmentbase.duplicate().powZn(elment4.duplicate());
		Element E4 = elmentbase.duplicate().powZn(elment3.duplicate());
		Long time1 = System.nanoTime();
		boolean eq = pairing.pairing(E1, E4).isEqual(pairing.pairing(E2, E3));
		Long time2 = System.nanoTime();
		System.out.println((time2 - time1) / 1000000);
		return eq;
	}
	public String encKeywordUser2(String str) {
		StringBuffer list = new StringBuffer("");
		Pairing pairing = PairingFactory.getPairing("a.properties");
		Element elment = pairing.getZr().newRandomElement();
		elment.setFromBytes(ConstantsUtil.USER2);
		Element elmentbase = pairing.getG1().newRandomElement();
		elmentbase.setFromBytes(ConstantsUtil.SYSTEM_BASE);
		Random r = new Random();
		BigInteger ru = BigInteger.valueOf(Math.abs(r.nextInt()));
		Element elment1 = elment.duplicate().mul(ru);
		Element elment2 = elment1.duplicate().mul(JPBCUtil.hashToZp(pairing, str.trim()));
		Element E1 = elmentbase.duplicate().powZn(elment2.duplicate());
		Element E2 = elmentbase.duplicate().powZn(elment1.duplicate());
		list.append("E1="+E1.toString()+"\n\n");
		list.append("E2="+E2.toString());
		return list.toString();
	}
	public void readEnckeyword(String encKeyword) throws IOException, ClassNotFoundException {
		Pairing pairing = PairingFactory.getPairing("a.properties");
		File file = new File(encKeyword);
		FileInputStream inputStream = new FileInputStream(file);
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		ArrayList<byte[]> list = (ArrayList<byte[]>) objectInputStream.readObject();
		Element E1 = pairing.getG1().newElement();
		byte[] b = list.get(0);
		E1.setFromBytes(list.get(0));
		System.out.println(E1);
	}

	public void encKeyword(String keywordSets, String encKeyword) throws IOException {
		Pairing pairing = PairingFactory.getPairing("a.properties");
		Element elment = pairing.getZr().newRandomElement();
		elment.setFromBytes(ConstantsUtil.SYSTEM_RS);
		Element elmentbase = pairing.getG1().newRandomElement();
		elmentbase.setFromBytes(ConstantsUtil.SYSTEM_BASE);
		// PrintStream out = new PrintStream("E:/keyword/log.txt");
		// System.setOut(out);
		// System.out.println(elmentbase.duplicate());
		File file = new File(encKeyword);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		ArrayList<byte[]> list = new ArrayList<byte[]>();
		FileReader keywordSetFile = new FileReader(keywordSets);
		/** 读取关键词集合 */
		BufferedReader buf = new BufferedReader(keywordSetFile);
		String readLine = "";
		while ((readLine = buf.readLine()) != null) {
			Random r = new Random();
			BigInteger r0 = BigInteger.valueOf(Math.abs(r.nextInt()));
			Element elment1 = elment.duplicate().mul(r0);
			Element elment2 = elment1.duplicate().mul(JPBCUtil.hashToZp(pairing, readLine.trim()));
			Element E1 = elmentbase.duplicate().powZn(elment2.duplicate());
			Element E2 = elmentbase.duplicate().powZn(elment1.duplicate());
			// System.out.println(E1);
			list.add(E1.duplicate().toBytes());
			list.add(E2.duplicate().toBytes());
			// System.out.println(E1.duplicate());
			// Element E3 = pairing.getG1().newElement();
			// E3.setFromBytes(list.get(0));
			// System.out.println("查看第一个list元素");
			// System.out.println(E3);
		}
		oos.writeObject(list);
		oos.close();
		// Element E1 = pairing.getG1().newElement();
		// E1.setFromBytes(list.get(0));
		// System.out.println("list");
		// System.out.println(E1);
		// bw.close();
		buf.close();
	}

	public void searchTopK(int k) {

	}

	public String[] readQueryWords(int size, String str) throws IOException {
		String[] strQuery = new String[size];
		FileReader keywordSetFile = new FileReader(str);
		/** 读取关键词集合 */
		BufferedReader buf = new BufferedReader(keywordSetFile);
		String readLine = null;
		int num = 0;
		while ((readLine = buf.readLine()) != null) {
			strQuery[num] = readLine.trim();
			num++;
		}
		buf.close();
		return strQuery;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
//		String strbase = System.getProperty("user.dir")+"\\src\\main\\java\\com\\cn\\keywords\\indexTree2295k2kfp5.txt";
//		System.out.println(strbase);
		TBMOM tbmom = new TBMOM();
//		tbmom.encKeyword("E:/IPv6File/tfidf/TopKeyword2kfp5.txt", "E:/IPv6File/tfidf/EncTopKeyword2kfp5.txt");
//		tbmom.keywordToMap("E:/IPv6File/tfidf/TopKeyword2kfp5.txt", "E:/IPv6File/tfidf/mapnoencTopKeyword2kfp5.txt");
	}
}
