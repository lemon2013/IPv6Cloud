package com.cn.tbmsm.tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.cn.tbmsm.util.BigDecimalUtil;
import com.cn.tbmsm.util.ConstantsUtil;
import com.cn.tbmsm.util.Helper;
import com.cn.tbmsm.util.JPBCUtil;
import com.cn.tbmsm.util.RSID;
import com.cn.tbmsm.util.RSIDComparator;
import com.cn.tbmsm.util.TreeUtil;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class FileIndexTree implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4942946752214153963L;
	/**
	 * 根节点
	 */
	private Node root;
	LinkedList<Node> queue;
	private int keywordSize;
	private double minScore;
	private ArrayList<RSID> rList;

	public FileIndexTree(int keysize) {
		rList = new ArrayList<RSID>();
		minScore = 0.0;
		keywordSize = keysize;
	}

	public FileIndexTree(int keysize, LinkedList<Node> queue, String owner) {
		rList = new ArrayList<RSID>();
		minScore = 0.0;
		keywordSize = keysize;
		// Long time3 = System.nanoTime();
		setQueue(queue, owner);
		// Long time4 = System.nanoTime();
		createTree(queue, owner);
		// Long time5 = System.nanoTime();
		// double t3 = (time4 - time3) / 1000000;
		// double t4 = (time5 - time4) / 1000000;
		// System.out.println("设置队列耗时：" + t3 + "\n构建树耗时:" + t4);
	}

	public FileIndexTree(int keysize, String indexFile, String owner) {
		rList = new ArrayList<RSID>();
		minScore = 0.0;
		keywordSize = keysize;
		// Long time3 = System.nanoTime();
		try {
			setQueue(indexFile, owner);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Long time4 = System.nanoTime();
		createTree(queue, owner);
		// Long time5 = System.nanoTime();
		// double t3 = (time4 - time3) / 1000000;
		// double t4 = (time5 - time4) / 1000000;
		// System.out.println("设置队列耗时：" + t3 + "\n构建树耗时:" + t4);
	}
	public FileIndexTree(int keysize, String indexFile, String fileID,String owner) {
		rList = new ArrayList<RSID>();
		minScore = 0.0;
		keywordSize = keysize;
		try {
			setQueue(indexFile, fileID,owner);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createTree(queue, owner);
	}
	public void setQueue(LinkedList<Node> queue, String owner) {
		queue = new LinkedList<Node>();
		Iterator<Node> it = queue.iterator();
		while (it.hasNext()) {
			Node s = it.next();
			ArrayList<FunctionY> arraylist = s.getFunctionList();
			String[] score = new String[arraylist.size()];
			for (int i = 0; i < arraylist.size(); i++) {
				FunctionY y = arraylist.get(i);
				score[i] = String.valueOf(y.computeByY(Helper.BKDRHash("owner", 31)));
			}
			s.setScore(score);
			s.setFOwner(owner);
			this.queue.add(s);
		}
	}

	/**
	 * @return the rList
	 */
	public ArrayList<RSID> getrList() {
		return rList;
	}

	/**
	 * @param rList
	 *            the rList to set
	 */
	public void setrList(ArrayList<RSID> rList) {
		this.rList = rList;
	}

	public void setQueue(String indexFile, String owner) throws IOException {
		queue = new LinkedList<Node>();
		FileReader file = new FileReader(indexFile);
		BufferedReader bufFile = new BufferedReader(file);
		String vector = "";
		int lineNum = 0;
		while ((vector = bufFile.readLine()) != null) {
			lineNum++;
			Node leafNode = new Node(keywordSize);
			leafNode.setFID(String.valueOf(lineNum));
			leafNode.setLeftNode(null);
			leafNode.setRightNode(null);
			String[] score = vector.split("\\s");
			ArrayList<FunctionY> functionList = new ArrayList<FunctionY>();
			for (int i = 0; i < score.length; i++) {
				FunctionY funcy = new FunctionY(Double.parseDouble(score[i]), 3, 5, 7, 200);
				score[i] = String.valueOf(BigDecimalUtil.round(
						Helper.aoppf(Double.parseDouble(score[i]), Helper.BKDRHash("S151000904", 31), 3, 5, 7, 200),
						4));
				functionList.add(funcy);
			}
			leafNode.setScore(score);
			leafNode.setFunctionList(functionList);
			leafNode.setFOwner(owner);
			queue.add(leafNode);
		}
		bufFile.close();
	}
	public void setQueue(String indexFile,String fileID, String owner) throws IOException {
		queue = new LinkedList<Node>();
		FileReader file = new FileReader(indexFile);
		BufferedReader bufFile = new BufferedReader(file);
		FileReader fileIDReader = new FileReader(fileID);
		BufferedReader bufFileID = new BufferedReader(fileIDReader);
		String vector = "";
		String idnum = "";
		HashSet<String> set = new HashSet<String>();
		while (((vector = bufFile.readLine()) != null)&&((idnum=bufFileID.readLine())!=null)) {
			String fileid = idnum.trim();
			set.add(fileid);
			Node leafNode = new Node(keywordSize);
			leafNode.setFID(fileid);
			leafNode.setLeftNode(null);
			leafNode.setRightNode(null);
			String[] score = vector.split("\\s");
			ArrayList<FunctionY> functionList = new ArrayList<FunctionY>();
			for (int i = 0; i < score.length; i++) {
				FunctionY funcy = new FunctionY(Double.parseDouble(score[i]), 3, 5, 7, 200);
				score[i] = String.valueOf(BigDecimalUtil.round(
						Helper.aoppf(Double.parseDouble(score[i]), Helper.BKDRHash("S151000904", 31), 3, 5, 7, 200),
						4));
				functionList.add(funcy);
			}
			leafNode.setScore(score);
			leafNode.setFunctionList(functionList);
			leafNode.setFOwner(owner);
			queue.add(leafNode);
		}
		System.out.println(set.size());
		bufFile.close();
		bufFileID.close();
	}
	public LinkedList<Node> getQueue() {
		return queue;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public void encKeyword(String keywordSets) throws IOException {
		Pairing pairing = PairingFactory.getPairing("a.properties");
		Element elment = pairing.getZr().newRandomElement();
		elment.setFromBytes(ConstantsUtil.SYSTEM_RS);
		Element elmentbase = pairing.getG1().newRandomElement();
		elmentbase.setFromBytes(ConstantsUtil.SYSTEM_BASE);
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
			list.add(E1.duplicate().toBytes());
			list.add(E2.duplicate().toBytes());
		}
		buf.close();
	}

	public Node generateParentNode(Node left, Node right) {
		Node parent = new Node(keywordSize);
		parent.setLeftNode(left);
		parent.setRightNode(right);
		parent.setFID("null");
		String[] vector = new String[keywordSize];
		for (int i = 0; i < keywordSize; i++) {
			BigDecimal data1 = new BigDecimal(Double.valueOf(left.getScore()[i]));
			BigDecimal data2 = new BigDecimal(Double.valueOf(right.getScore()[i]));
			if (BigDecimalUtil.compare(data1, data2) == 1) {
				vector[i] = left.getScore()[i];
				parent.getFunctionList().add(i, left.getFunctionList().get(i));
			} else {
				vector[i] = right.getScore()[i];
				parent.getFunctionList().add(i, right.getFunctionList().get(i));
			}
		}
		parent.setScore(vector);
		parent.setFOwner(left.getFOwner());
		if(parent==null){
			System.out.println("生成父节点");
		}
		return parent;
	}

	public void createTree(LinkedList<Node> queueList, String owner) {
		while (queueList.size() > 1) {
			Node tempNode = new Node(keywordSize);
			Node left = new Node(keywordSize);
			Node right = new Node(keywordSize);
			LinkedList<Node> tempNodelist = new LinkedList<Node>();
			if (queueList.size() % 2 == 0) {
				Iterator<Node> iterator = queueList.iterator();
				int flag = 1;
				while (iterator.hasNext()) {
					if (flag == 1) {
						left = iterator.next();
						flag = 2;
					} else {
						right = iterator.next();
						tempNode = generateParentNode(left, right);
						tempNodelist.add(tempNode);
						flag = 1;
					}
				}
			} else {
				Iterator<Node> iterator = queueList.iterator();
				int flag = 1;
				int stopFlag = 0;
				int size = queueList.size() - 1;
				while (iterator.hasNext() || stopFlag == size) {
					stopFlag++;
					if (flag == 1) {
						left = iterator.next();
						flag = 2;
					} else {
						right = iterator.next();
						tempNode = generateParentNode(left, right);
						tempNodelist.add(tempNode);
						flag = 1;
					}
				}
				tempNode = generateParentNode(tempNodelist.removeLast(), queueList.getLast());
				tempNodelist.add(tempNode);
			}
			queueList.clear();
			queueList.addAll(tempNodelist);
		}
		setRoot(queueList.getFirst());
		root.setFOwner(owner);
		root.setFID("root");
	}

	private double getRScore(Node node, double[] q) {
		double score = 0.0;
		String[] nscore = node.getScore();
		for (int i = 0; i < q.length; i++) {
			score += q[i] * Double.parseDouble(nscore[i]);
		}
		return Math.round(score * 10000) / 10000.0000;
		// return score;
	}

	public void GDFS(Node node, double[] q, int K) {
		if (rList == null) {
			rList = new ArrayList<RSID>();
		}
		double score = getRScore(node, q);
		if (node.getFID().equals("root")||node.getFID().equals("null")) {
			if (score > minScore) {
				GDFS(node.getLeftNode(), q, K);
				GDFS(node.getRightNode(), q, K);
			}
		} else {
			if (score > minScore) {
				// System.out.println(rList.size());
				if (rList.size() == K) {
					rList.remove(rList.size() - 1);
					rList.add(new RSID(node.getFID(), score,node.getFOwner()));
					RSIDComparator rsidComparator = new RSIDComparator();
					Collections.sort(rList, rsidComparator);
					minScore = rList.get(rList.size() - 1).getScore();
				} else if (rList.size() == K - 1) {
					rList.add(new RSID(node.getFID(), score,node.getFOwner()));
					RSIDComparator rsidComparator = new RSIDComparator();
					Collections.sort(rList, rsidComparator);
					minScore = rList.get(rList.size() - 1).getScore();
				} else {
					rList.add(new RSID(node.getFID(), score,node.getFOwner()));
					RSIDComparator rsidComparator = new RSIDComparator();
					Collections.sort(rList, rsidComparator);
				}
			}
		}
	}
	public void clearList() {
		rList = new ArrayList<RSID>();
		minScore = 0.0;
	}
	public static void main(String arg[]) throws IOException {
//		Long time2 = System.nanoTime();
//		// ArrayList<FileIndexTree> indextreelist = new
//		// ArrayList<FileIndexTree>();
////		 FileIndexTree indextree = new FileIndexTree(1000);
////		 indextree.setQueue("E:/result/1000keywords/1k.txt","E:/result/1000keywords/6kfileIDname.txt", "lemon");
//		// LinkedList<Node> linklist = indextree.getQueue();
//		// System.out.println(linklist.size());
//		// LinkedList<Node> linklist = indextree.g;
//		 FileIndexTree indextree = new FileIndexTree(2295,
//		 "E:/IPv6File/tfidf/2295k2kfp5.txt","E:/IPv6File/tfidf/fileID2k.txt", "lemon");
//		Long time3 = System.nanoTime();
//		// indextree.encKeyword("E:/keyword/top4000.txt");
//		Long time5 = System.nanoTime();
//		 TreeUtil.writeTreeToFile(indextree,
//		 "E:/IPv6File/tfidf/indexTree2295k2kfp5.txt");
//		// FileIndexTree indextreemerg = new FileIndexTree(4000, linklist,
//		// "merg");
		Long time4 = System.nanoTime();
		FileIndexTree indextree = TreeUtil.readTreeFromFile("E:/result/query/q5u100/indexTree1k.txt");
////		Node root = tree.getRoot();
		 double[] q = new double[100];
		 for(int i=0;i<5;i++){
			 q[i]=1.0;
		 }
		 Long time6 = System.nanoTime();
		 indextree.GDFS(indextree.getRoot(), q, 1000);
		 Long time7 = System.nanoTime();
		 System.out.println("相关文件"+indextree.getrList().size());
		 ArrayList<RSID> rList = indextree.getrList();
		 Iterator<RSID> iterator = rList.iterator();
		 while (iterator.hasNext()) {
		 iterator.next().display();
		 }
//		double t1 = (time3 - time2) / 1000000;
//		double t2 = (time5 - time2) / 1000000;
//		double t3 = (time5 - time3) / 1000000;
		 double t4 = (time6 - time4) / 1000000;
		 double t5 = (time7 - time6) / 1000000;
//		System.out.println("创建树耗时：" + t1 + "ms\n" + "关键词加密耗时：" + t3 + "\n创建索引耗时：" + t2+ "读取文件耗时：" + t4 + "ms\n"+ "搜索耗时：" + t5 + "ms");
//		// "ms\n"
//		// + "读取文件耗时：" + t3 + "ms\n" + "搜索耗时：" + t4 + "ms");
		 System.out.println("读索引文件：" + t4 + "ms\n" + "搜索耗时：" + t5 +
		 "ms\n");
	}
}
