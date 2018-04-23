package com.cn.tbmsm.prmsm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.cn.tbmsm.tree.FunctionY;
import com.cn.tbmsm.tree.PNode;
import com.cn.tbmsm.util.BigDecimalUtil;
import com.cn.tbmsm.util.ConstantsUtil;
import com.cn.tbmsm.util.Helper;
import com.cn.tbmsm.util.JPBCUtil;
import com.cn.tbmsm.util.ValueComparator;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class PRMSM implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1381844930502387567L;
	private ArrayList<PNode> queue;

	public PRMSM() {
		queue = new ArrayList<PNode>();
	}

	public ArrayList<byte[]> computeTrap(String[] strQuery) {
		ArrayList<byte[]> list = new ArrayList<byte[]>();
		Pairing pairing = PairingFactory.getPairing("a.properties");
		Element elmentk1 = pairing.getZr().newRandomElement();
		elmentk1.setFromBytes(ConstantsUtil.USER2);
		Element elmentk2 = pairing.getZr().newRandomElement();
		elmentk2.setFromBytes(ConstantsUtil.USER3);
		Element elmentbase = pairing.getG1().newRandomElement();
		elmentbase.setFromBytes(ConstantsUtil.SYSTEM_BASE);
		for (int i = 0; i < strQuery.length; i++) {
			Random r = new Random();
			BigInteger ru = BigInteger.valueOf(Math.abs(r.nextInt()));
			// Element elment1 = elment.duplicate().mul(ru);
			Element elment2 = (JPBCUtil.hashToZp(pairing, strQuery[i].trim())).mul(ru);
			Element T1 = elmentbase.duplicate().powZn(elment2.duplicate());
			Element T2 = elmentbase.duplicate().pow(ru);
			BigInteger rs = BigInteger.valueOf(Math.abs(r.nextInt()));
			Element T3 = T1.duplicate().powZn(elmentk1.duplicate().mulZn(elmentk2).duplicate()).pow(rs);
			Element T4 = T2.duplicate().powZn(elmentk1.duplicate());
			Element T5 = T4.duplicate().pow(rs);
			Element T6 = elmentbase.powZn(elmentk1.duplicate().mulZn(elmentk2.duplicate()).mul(rs));
			list.add(T3.duplicate().toBytes());
			list.add(T4.duplicate().toBytes());
			list.add(T5.duplicate().toBytes());
			list.add(T6.duplicate().toBytes());
		}
		return list;
	}

	public ArrayList<PNode> getQueue() {
		return queue;
	}

	public void setQueue(ArrayList<PNode> queue) {
		this.queue = queue;
	}

	public String[] readQueryWords(int size, String str) throws IOException {
		String[] strQuery = new String[size];
		FileReader keywordSetFile = new FileReader(str);
		/** ��ȡ�ؼ��ʼ��� */
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

	public ArrayList<PNode> setQueue(String indexFile, String keywordfile, String owner) throws IOException {
		ArrayList<String> topKeyWordlist = new ArrayList<String>();
		FileReader keywordSetFile = new FileReader(keywordfile);
		/** ��ȡ�ؼ��ʼ��� */
		BufferedReader buf = new BufferedReader(keywordSetFile);
		String readLine = "";
		while ((readLine = buf.readLine()) != null) {
			topKeyWordlist.add(readLine.trim());
		}
		buf.close();
		FileReader file = new FileReader(indexFile);
		BufferedReader bufFile = new BufferedReader(file);
		String vector = "";
		int lineNum = 0;
		while ((vector = bufFile.readLine()) != null) {
			lineNum++;
			PNode leafNode = new PNode(topKeyWordlist.size());
			leafNode.setFID(String.valueOf(lineNum));
			String[] score = vector.split("\\s");
			ArrayList<FunctionY> functionList = new ArrayList<FunctionY>();
			ArrayList<EncKeyWord> enckeywordList = new ArrayList<EncKeyWord>();
			for (int i = 0; i < topKeyWordlist.size(); i++) {
				double b = 0.0;
				BigDecimal data2 = new BigDecimal(b);
				BigDecimal data1 = new BigDecimal(Double.parseDouble(score[i]));
				int result = data1.compareTo(data2);
				if (result != 0) {
					FunctionY funcy = new FunctionY(Double.parseDouble(score[i]), 3, 5, 7, 200);
					EncKeyWord enckeyword = new EncKeyWord(topKeyWordlist.get(i));
					score[i] = String.valueOf(BigDecimalUtil.round(
							Helper.aoppf(Double.parseDouble(score[i]), Helper.BKDRHash(owner, 31), 3, 5, 7, 200), 4));
					functionList.add(funcy);
					enckeywordList.add(enckeyword);
				}
			}
			leafNode.setScore(score);
			leafNode.setFunctionList(functionList);
			leafNode.setFOwner(owner);
			leafNode.setEnckeywordList(enckeywordList);
			queue.add(leafNode);

		}
		bufFile.close();
		return queue;
	}

	public Map<String, Double> search(ArrayList<byte[]> list, int K) {
		HashMap<String, Double> rslist = new HashMap<String, Double>();
		Pairing pairing = PairingFactory.getPairing("a.properties");
		Element elmentbase = pairing.getG1().newRandomElement();
		elmentbase.setFromBytes(ConstantsUtil.SYSTEM_BASE);
		Element T1 = pairing.getG1().newRandomElement();
		Element T2 = pairing.getG1().newRandomElement();
		Element T3 = pairing.getG1().newRandomElement();
		Element S = pairing.getG1().newRandomElement();
		for (int j = 0; j < queue.size(); j++) {
			PNode node = queue.get(j);
			double score = 0.0;
			ArrayList<EncKeyWord> enckeywordlist = node.getEnckeywordList();
			for (int i = 0; i < list.size() - 3;) {
				T1.setFromBytes(list.get(i));
				T2.setFromBytes(list.get(i + 1));
				T3.setFromBytes(list.get(i + 2));
				S.setFromBytes(list.get(i + 3));
				for (int k = 0; k < enckeywordlist.size(); k++) {
					Element E1 = pairing.getG1().newRandomElement();
					Element E2 = pairing.getG1().newRandomElement();
					E1.setFromBytes(enckeywordlist.get(k).getE1());
					E2.setFromBytes(enckeywordlist.get(k).getE2());
					if (pairing.pairing(E1, T3).isEqual(pairing.pairing(E2, T1).mul(pairing.pairing(S, T2)))) {
						score += Double.valueOf(node.getScore()[k]);
					}
				}
				rslist.put(node.getFID(), score);
				i += 4;
			}
		}
		Map<String, Double> sorted_map2 = ValueComparator.sortByValues(rslist);
		return sorted_map2;
	}

	public static void main(String[] args) throws IOException {
		
	}
}