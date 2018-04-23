package com.cn.tbmsm.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ValueComparator implements Comparator<Long> {

	Map<String, Double> base;

	public ValueComparator(Map<String, Double> base) {
		this.base = base;
	}

	public int compare(String a, String b) {
		if (base.get(a).doubleValue() >= base.get(b).doubleValue()) {
			return -1;
		} else {
			return 1;
		}
	}

	public static <K, V extends Comparable<V>> Map<K, V> sortByValues(
			final Map<K, V> map) {
		Comparator<K> valueComparator = new Comparator<K>() {
			public int compare(K k1, K k2) {
				int compare = map.get(k2).compareTo(map.get(k1));
				if (compare == 0)
					return 1;
				else
					return compare;
			}
		};
		Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}

	 public static void main(String[] args) {
	 HashMap<String, Double> map = new HashMap<String, Double>();
	 map.put("dasd", 99.5);
	 map.put("dfsaf", 67.2);
	 map.put("fdsf", 67.5);
	 map.put("fdsfwe", 67.6);
	
//	 ValueComparator bvc = new ValueComparator(map);
//	 TreeMap<String, Double> sorted_map = new TreeMap<String, Double>();
	
	 System.out.println("unsorted map: " + map);
	
//	 sorted_map.putAll(map);
	
//	 System.out.println("results: " + sorted_map);
	
	 Map<String, Double> sorted_map2 = sortByValues(map);
	
	 System.out.println("results2: " + sorted_map2);
	 }

	public int compare(Long o1, Long o2) {
		// TODO Auto-generated method stub
		return 0;
	}
}
