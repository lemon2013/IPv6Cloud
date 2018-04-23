package com.cn.tbmsm.tree;

import java.util.LinkedList;

public class Queue {
	private LinkedList<Node> list;  
	
    /** 
     * @return the list 
     */  
    public LinkedList<Node> getList() {  
        return list;  
    }  
  
    /** 
     * @param list the list to set 
     */  
    public void setList(LinkedList<Node> list) {  
        this.list = list;  
    }  
  
     public Queue(){  
         list = new LinkedList<Node>();  
     }   
       
     public void inQueue(Node node){  
         list.add(node);  
     }  
       
     public Node outQueue(){  
        return list.removeFirst();
     }  
       
     public boolean isEmpty(){  
        return list.isEmpty();
     }  
}
