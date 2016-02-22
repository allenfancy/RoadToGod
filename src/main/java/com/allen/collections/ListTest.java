package com.allen.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ListTest {

	public static void main(String[] args){
		testCollections();
		//reverse();
	}
	
	private static void testCollections(){
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(23);
		list.add(33);
		list.add(3);
		list.add(43);
		list.add(13);
		Integer min = Collections.min(list);
		Integer max = Collections.max(list);
		System.out.println(min.intValue());
		System.out.println(max.intValue());
		/*Collections.reverse(list);
		Iterator<Integer> it = list.iterator();
		while(it.hasNext()){	
			System.out.println(it.next());
		}*/
		ArrayList<Integer> lists = (ArrayList<Integer>) list.clone();
		Iterator<Integer> it1 = lists.iterator();
		while(it1.hasNext()){	
			System.out.println(it1.next());
		}
		System.out.println();
		System.out.println(list.subList(0, 2).size());
		System.out.println(list.subList(0, 2).get(0));
	}
	public static void reverse(){
		List<Integer> list = new ArrayList<Integer>();
		list.add(23);
		list.add(33);
		list.add(123);
		list.add(433);
		list.add(134);
		list.add(35);
		list.add(463);
		list.add(137);
		list.add(38);
		list.add(436);
		list.add(134);
		list.add(34343);
		list.add(434);
		list.add(131);
		list.add(322);
		list.add(433);
		list.add(132);
		list.add(31);
		list.add(4312);
		list.add(139);
		list.add(23);
		list.add(33);
		list.add(123);
		list.add(433);
		list.add(134);
		list.add(35);
		list.add(463);
		list.add(137);
		list.add(38);
		list.add(436);
		list.add(134);
		list.add(34343);
		list.add(434);
		list.add(131);
		list.add(322);
		list.add(433);
		list.add(132);
		list.add(31);
		list.add(4312);
		list.add(139);
		Collections.reverse(list);
		System.out.println(list.size());
	}
	private static void testBasic(){
		List<String> list = new ArrayList<String>();
		list.add("allen");
		list.add("allen1");
		list.add("allen2");
		list.add("allen3");
		System.out.println(list.toArray(new String[]{}));
		Iterator<String> it = list.iterator();
		while(it.hasNext()){
			String str = it.next();
			System.out.println(str);
			if(str.contains("all")){
				it.remove();
			}
		}
		System.out.println(list.size());
	}
}
