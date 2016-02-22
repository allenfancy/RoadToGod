package com.allen.orign;
/***
 * 
 * Integer问题1：
 * Integer a = Integer.valueOf(127);
 * Integer b = Integer.valueOf(127);
 * System.out.println(a == b);  true
 * Integer a = Integer.valueOf(180);
 * Integer b = Integer.valueOf(180);
 * System.out.println(a == b);  false
 * 原因:
 * 在Integer中写了一个IntegerCache对象
 * 在这个对象中，存储的内容为一个缓存的对象数组cache。
 * 当参数范围-128 > x < 127的时候走这个Cache。而不是new 一个新的对象
 */
import java.lang.*;
public  class IntegerOri{

	public static void main(String[] args){
		Integer a = new Integer(180);//Integer.valueOf(220);
		Integer b = new Integer(180);//Integer.valueOf(220);
		
		Integer c = 130;
		Integer d = 130;
	
		Integer e = Integer.valueOf(180);
		Integer f = Integer.valueOf(180);
		
		Integer g = Integer.valueOf(280);
		Integer h = Integer.valueOf(280);
		
		System.out.println(a == b);
		System.out.println(c == d);
		System.out.println(e == f);
		System.out.println(g == h);
		System.out.println("Equals :     ----- ");
		System.out.println(a.equals(b));
		System.out.println(c.equals(d));
		System.out.println(e.equals(f));
		System.out.println(g.equals(h));
	}

}
