package com.allen.orign;

import java.util.ArrayList;
import java.util.List;

public class StringInternPermOOM {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> lists = new ArrayList<String>();
		while(true){
			lists.add("内存溢出了，".intern());
		}
	}

}
