package com.allen.orign;

import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * 1. 迷惑： equals()重写后，一般会重写hashCode()方法吗？
 * 	  hashCode()方法提供了对象的hashCode值，它与equals()一样在Object类中提供，不过它是一个native(本地)方法，它的返回值默认与System.identityashCode(object)一致。
 *    这个数字具有一定的标识对象的意义，但绝对不是等价于地址
 *    hashCode的作用：它为了生产一个可以标准对象的数字，不论如何复杂的一个对象都可以用一个数字来标识。为什么需要用一个数字来标识对象呢？因为想将对象用在算法中，如果不这样，许多算法还得去组装数字
 *    
 * 2.String.eqauls()源码归纳：
 * 	 1.判定传入的对象和当前对象是否为同一个对象，如果是就直接返回true。
 *   2.判定传入对象的类型是否为String，若不是则返回false
 *   3.判定出入的String和当前的String的长度是否一致，若不一致就返回false
 *   4.循环对比俩个字符串的char[]数组，一个个对比字符是否一致，若存在不一致的情况，则直接返回fasle
 *   5.循环结束都没有找到不匹配的，最后返回true
 *   
 *
 */
public class StringOri {
	//给出一个排序的规则
	final static Comparator CHINA_COMPARE = Collator.getInstance(Locale.CHINA);
	public static void main(String[] args){
		
		//test1();
		// test2();
		//test3();
		test4();
	}
	
	/**
	 * == :用于匹配内存单元上得内容，其实就是一个数字，计算机内部也只有数字。
	 *     而在Java语言中，当"=="匹配的时候，其实就是对比俩个内存单元的内容是否一样。
	 * 如果是原始类型 byte,boolean,short char int long float double就是直接比较他们的值。
	 * 如果是引用(Reference),比较的就是引用的值。即为对象的逻辑地址。如果俩个引用发生"=="操作，就比较俩个对象的地址值是否一样。
	 * 换句话说，如果俩个引用所保存的对象是同一个对象，则返回true，否则返回false，(如果引用指向的是null,其实这也是一个JVM赋予给它的某个指定的值)。
	 * 
	 * eqauls()方法，首先是定义在Object类中被定义的。它的定义中就是使用"=="方式来匹配的。也就是，如果不去重写eqauls()方法，并且对应的类或者父类中没有重写
	 * eqauls()方法，那么就默认的eqauls()操作就是比较对象的地址
	 */
	private static void test1(){
		String a = "a"+"b"+1;
		String b ="ab1";
		System.out.println(a == b);
		System.out.println(a.equals(b));
	}
	
	private static void test2(){
		String a = "a";
		String b = a + "b";
		final String c = "a";
		String d = c + "b";
		String e = getA() + "b";
		String compare = "ab";
		System.out.println(b == compare); //false
		System.out.println(d == compare); // true
		System.out.println(e == compare); // false
		
	}
	/**
	 * intern()方法的作用：对于同一个值的字符串保证全局唯一性
	 * 如何保证全局唯一性：
	 * 	当调用intern()方法时，JVM会在这个常量池中通过equals()方法查找是否保存等值的String，如果存在，则直接返回常量池中这个String对象的地址；
	 *  若没有找到，则会创建等值的字符串(即等值的char[]数组字符串，但是char[]是新开辟的一份拷贝空间)，然后再返回这个新创建空间的地址。
	 *  只有是同样的字符串，当调用intern()方法时，都会得到常量池中对应String的引用，所以俩个字符串通过intern()操作后用等号可以匹配的
	 *  
	 *  字符串"ab"本身就在常量池中，当发生new String(b)操作的时候，仅仅进行了char[]数组的拷贝，创建了一个String实例。当这个新创建的实例发生
	 *  intern()操作时，在常量池中是能找到这个对象的。
	 */
	private static void test3(){
		String a = "a";
		String b = a + "b";
		String c = "ab";
		String d = new String(b);
		System.out.println(b == c);
		System.out.println(c == d);
		System.out.println(c == d.intern());
		System.out.println(b.intern() == d.intern());
	}
	private static String getA(){
		return "a";
	}
	
	private static void test4(){
		sortList();
		sortArray();
	}
	
	private static void sortList(){
		List<String> list = Arrays.asList("张三","李四","王五");
		Collections.sort(list,CHINA_COMPARE);
		for(String str : list){
			System.out.println(str);
		}
	}
	
	private static void sortArray(){
		String [] arr = {"张三","李四","王五"};
		Arrays.sort(arr,CHINA_COMPARE);
		for(String str : arr){
			System.out.println(str);
		}
	}
}
