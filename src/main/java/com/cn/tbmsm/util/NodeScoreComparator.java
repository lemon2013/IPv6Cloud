package com.cn.tbmsm.util;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import com.cn.tbmsm.tree.PNode;

public class NodeScoreComparator implements Comparator<Long> {
	Map<PNode, Double> base;

	public NodeScoreComparator(Map<PNode, Double> base) {
		this.base = base;
	}

	public int compare(String a, String b) {
		if (base.get(a).doubleValue() >= base.get(b).doubleValue()) {
			return -1;
		} else {
			return 1;
		}
	}

	public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
		Comparator<K> NodeScoreComparator = new Comparator<K>() {
			public int compare(K k1, K k2) {
				int compare = map.get(k2).compareTo(map.get(k1));
				if (compare == 0)
					return 1;
				else
					return compare;
			}
		};
		Map<K, V> sortedByValues = new TreeMap<K, V>(NodeScoreComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}

	public int compare(Long o1, Long o2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
