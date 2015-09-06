package com.java.password.entropy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Generic quicksort algorithm. It can be used to short frequencies
 * according to letters etc.
 * http://en.wikibooks.org/wiki/Algorithm_Implementation/Sorting/Quicksort
 * @author john
 *
 */
public class QuickSort {
 
        @SuppressWarnings("unchecked")
        public static <E extends Comparable<? super E>> List<E>[] split(List<E> v) {
                List<E>[] results = (List<E>[])new List[] { new LinkedList<E>(), new LinkedList<E>() };
                Iterator<E> it = v.iterator();
                E pivot = it.next();
                while (it.hasNext()) {
                        E x = it.next();
                        if (x.compareTo(pivot) < 0) results[0].add(x);
                        else results[1].add(x);
                }
                return results;
        }
 
        public static <E extends Comparable<? super E>> List<E> quicksort(List<E> v) {
                if (v == null || v.size() <= 1) return v;
                final List<E> result = new LinkedList<E>();
                final List<E>[] lists = split(v);
                result.addAll(quicksort(lists[0]));
                result.add(v.get(0));
                result.addAll(quicksort(lists[1]));
                return result;
        }

  /**
   * just a demo of sorting
   * @return
   */
	public static List<Integer> Alphabet(){
	    int length = 1000;
	    List<Integer> list = new ArrayList<Integer>();
	
	    list.add(1);
	    list.add(20);
	    list.add(10);
	    list.add(13);
	    return list;
	}
}
