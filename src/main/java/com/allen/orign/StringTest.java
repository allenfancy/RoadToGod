package com.allen.orign;
/**
 assfile /Users/allen/git/RoadToGod/src/main/java/com/allen/orign/StringTest.class
  Last modified 2016-1-16; size 537 bytes
  MD5 checksum 5a335db247843447ab13a3562f0ebae4
public class com.allen.orign.StringTest
  minor version: 0
  major version: 51
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #6.#20         //  java/lang/Object."<init>":()V
   #2 = String             #21            //  ab1
   #3 = Fieldref           #22.#23        //  java/lang/System.out:Ljava/io/PrintStream;
   #4 = Methodref          #24.#25        //  java/io/PrintStream.println:(Z)V
   #5 = Class              #26            //  com/allen/orign/StringTest
   #6 = Class              #27            //  java/lang/Object
   #7 = Utf8               <init>
   #8 = Utf8               ()V
   #9 = Utf8               Code
  #10 = Utf8               LocalVariableTable
  #11 = Utf8               this
  #12 = Utf8               Lcom/allen/orign/StringTest;
  #13 = Utf8               test1
  #14 = Utf8               a
  #15 = Utf8               Ljava/lang/String;
  #16 = Utf8               b
  #17 = Utf8               StackMapTable
  #18 = Class              #28            //  java/lang/String
  #19 = Class              #29            //  java/io/PrintStream
  #20 = NameAndType        #7:#8          //  "<init>":()V
  #21 = Utf8               ab1
  #22 = Class              #30            //  java/lang/System
  #23 = NameAndType        #31:#32        //  out:Ljava/io/PrintStream;
  #24 = Class              #29            //  java/io/PrintStream
  #25 = NameAndType        #33:#34        //  println:(Z)V
  #26 = Utf8               com/allen/orign/StringTest
  #27 = Utf8               java/lang/Object
  #28 = Utf8               java/lang/String
  #29 = Utf8               java/io/PrintStream
  #30 = Utf8               java/lang/System
  #31 = Utf8               out
  #32 = Utf8               Ljava/io/PrintStream;
  #33 = Utf8               println
  #34 = Utf8               (Z)V
{
  public com.allen.orign.StringTest();
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0       
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return        
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
         0       5     0     this   Lcom/allen/orign/StringTest;
 
 *例子：
 *	const #2 = Sting #21 //ab1
 *  代表将会有一个String类型的引用入口，而引用的是入口#21的内容
 *  const #21 = utf8  ab1;
 *  代表常量池中会存放内容ab1
 *  综合：一个String对象的常量，存放的值是ab1
 *  
 *  例子：
 *  const #3 = FieldRef    #22.#23
 *  const #4 = MethodRef   #24.#25
 *  入口 #3代表一个属性，这个属性引用了入口#22类、 入口23的具体属性
 *  入口 #4代表一个方法，引用了入口#24类、入口#26的具体方法
 *   #22 = Class              #30            //  java/lang/System
  	 #23 = NameAndType        #31:#32        //  out:Ljava/io/PrintStream;
  	 #24 = Class              #29            //  java/io/PrintStream
  	 #25 = NameAndType        #33:#34        //  println:(Z)V
  	 入口#22 代表一个类(class),它也是一个引用，引用了入口#30的常量
  	 入口#23 代表一个名称和类型(NameAndType),分别对应入口#31:#32
  	 入口#24 代表一个类的引用，具体引用到入口#30
  	 入口#25 是一个返回值+引用类型，对应的入口为#33:#34
  	  #29 = Utf8               java/io/PrintStream
  	  #30 = Utf8               java/lang/System
  	  #31 = Utf8               out
  	  #32 = Utf8               Ljava/io/PrintStream;
      #33 = Utf8               println
      #34 = Utf8               (Z)V
      入口#29 对应常量池的值为 java/io/PrintStream,反推到入口#24，代表类java.lang.PrintStream
      入口#30 对应常量池的值为 java/lang/System,反推到入口#22，代表类 java.lang.System
      入口#31 对应常量池的值为 out，，反推到入口#23，而入口#23要求名称和类型，这里返回的是名称
      入口#32 对应常量池的值为 Ljava/io/PrintStream,反推到入口#23，这里得到的是类型，即位out的类型为java.io.PrintStream
      入口#33 对应常量池的值为 println,反推到入口#25，代表名称为println.
      入口#34 对应常量池的值为 (Z)V;反推到入口#25，代表的入口参数为Z(代表boolean类型)，返回值类型是V（代表的是void）
      
      综合：
      入口#3 是获得到java/lang/System类的属性out，out的类型是Ljava/io/PrintSystem
      入口#4 是调用java/io/PrintStream类的Println()方法，方法的返回值类型为void，入口类型是Boolean
      
     详解test1()方法中的一些参数：
     stack = 1， locals = 1，args_size = 1
     这一行是所有的方法都会有的。
     stack ：代表栈顶的单位大小(每个大小为一个slot，每个slot是4个字节的宽度)，当需要使用一个数据时，它首先会被
     放入栈顶，使用完后写回到本地变量或者主存中。这里的栈的宽度是1，其中是代表有一个this将会被使用。
     locals：是本地变量的slot个数，但是并不代表与stack宽度是一致的，本地变量在这个方法生命周期内，局部变量最多的时候，需要多大的宽度来存放数据
     (double long 会占用俩个slot).
     args_size： 代表的入口参数的个数，不再是slot的个数，即使传入一个long,也只会记录1.
     
     0:aload_0:第1个0代表虚指令中行号(后面会用到，，方法的body部分第几个字节)。每个方法从0开始顺序递增，但是可以跳跃，跳跃的原因在于一些指令还会接操作
     的内容，这些操作的内容可能来自常量池，也可以标识是第几个slot的本地变量，因此需要占用一定的空间。
     aload_0 指令是将 『第一个』slot所在的本地变量推到栈顶，并且这个本地变量是引用类型的，相关的指令有：aload_[0-3](范围是:0x2a-0x2d).
     如果超过4个，则会使用"aload + 本地变量的slot位置"来完成（此时会多占用1个字节来存放），而aload_[0-3]是通过具体的几个指令直接完成的。
     
     1: invokespecial  #1   //Method java/lang/Object."<init>":()V
     指令中的第二个行号，执行invokespecial指令，当发生构造方法调用，父类的构造方法调用、非静态的private方法调用时会使用该指令，这里需要从常量池中获取一个方法，
     这个地方会占用2个字节的宽度，加上指令本身就是3个字节，因为下一个行号是4.
     
     4: return
     最后一行是一个return，我们虽然没有写return，但是在JVM中会自动在编译时加上。
     
     LocalVariableTable：
     Start		Length		Slot		Name 		Signature
     0			 5			 0			this		com/allen/orign/StringTest
     
     代表本地变量的列表，本地变量的作用域其实位置为0，作用域宽度为5(0~4),slot的起始位置也是0，名称为this，类型为com/allen/orign/StringTest。
 
 
 */
public class StringTest {

	private static void test1(){
		String a = "a"+"b"+1;
		String b = "ab1";
		System.out.println(a == b);
	}
}
