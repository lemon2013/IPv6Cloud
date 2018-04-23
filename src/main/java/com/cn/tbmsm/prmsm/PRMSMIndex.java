package com.cn.tbmsm.prmsm;

import java.io.Serializable;
import java.util.LinkedList;

import com.cn.tbmsm.tree.PNode;

public class PRMSMIndex implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4301818441389255871L;
	private LinkedList<PNode> queue;

	public LinkedList<PNode> getQueue() {
		return queue;
	}

	public void setQueue(LinkedList<PNode> queue) {
		this.queue = queue;
	}
}
