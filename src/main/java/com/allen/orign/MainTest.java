package com.allen.orign;
/**
1.avap -verbose 命令查看发编译过来class文件的信息

 javap -verbose com.allen.orign.MainTest
Classfile /Users/allen/git/RoadToGod/src/main/java/com/allen/orign/MainTest.class
  Last modified 2016-1-16; size 374 bytes
  MD5 checksum a4d619684226224600dc8c3a67f3c953
public class com.allen.orign.MainTest
  minor version: 0
  major version: 51
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #3.#18         //  java/lang/Object."<init>":()V
   #2 = Class              #19            //  com/allen/orign/MainTest
   #3 = Class              #20            //  java/lang/Object
   #4 = Utf8               <init>
   #5 = Utf8               ()V
   #6 = Utf8               Code
   #7 = Utf8               LocalVariableTable
   #8 = Utf8               this
   #9 = Utf8               Lcom/allen/orign/MainTest;
  #10 = Utf8               main
  #11 = Utf8               ([Ljava/lang/String;)V
  #12 = Utf8               args
  #13 = Utf8               [Ljava/lang/String;
  #14 = Utf8               a
  #15 = Utf8               I
  #16 = Utf8               b
  #17 = Utf8               c
  #18 = NameAndType        #4:#5          //  "<init>":()V
  #19 = Utf8               com/allen/orign/MainTest
  #20 = Utf8               java/lang/Object
{
  public com.allen.orign.MainTest();
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0       
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return        
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
               0       5     0  this   Lcom/allen/orign/MainTest;

  public static void main(java.lang.String[]);
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=4, args_size=1
         0: iconst_1      
         1: istore_1      
         2: iconst_2      
         3: istore_2      
         4: iload_1       
         5: iload_2       
         6: iadd          
         7: istore_3      
         8: return        
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
               0       9     0  args   [Ljava/lang/String;
               2       7     1     a   I
               4       5     2     b   I
               8       1     3     c   I
}

 *一些常用参数的说明：
 *常量池描述信息是在编译时就确定的。所谓常量池就是要记录一些常量，这些常量通常池就是要记录一些常量，这些常量通常包含：类名
 *方法名，属性名，类型，修饰符，字符串常量，记录它们的入口位置(符号#带上一个数字，可以理解为一个入口标志位)、一些对象的常量值。
 * 1.iconst_1:将int类型的值1推送到栈顶
 * 2.istore_1:将栈顶的元素弹出，复制给第二个slot的本地变量
 * 这俩个虚拟的指令相当于 int a = 1;
 * iconst相关的指令包括：iconst_m1 iconst_[0-5],对应到虚指令的范围是[0x03~0x08]
 * 表示[-1~5]之间的数字常量加载到栈顶，如果不是这个范围的数字，就bipush指令。
 * istore_1 是赋值给slot为起始位置的本地变量，istore_0才是复制给第一个slot起始位置，那么第0个本地变量是什么呢?是Main方法传入的String[]参数，同样的，如果是非静态方法
 * this将作为任何方法的第一个本地变量
 * 
 * LocalVariableTable列表中每一行代表一个本地变量，每一行解释如下：
 *  Start  代表本地变量在虚指令作用域的起始位置(如第一个本地变量args是从0开始的)
 *  Length 代表本地变量在虚指令列表作用域的长度
 *  Slot   代表本地变量的slot其实位置编码
 *  Name   代表本地变量的名称，也就是本地定义的名称
 *  Signature 代表本地变量的类型
 *  
 *  常量池中的一些常量参数的讲解：
 *  demo1：
 *  	const #1 = Method #3.#18  // java/lang/Object."<init>":()v
 *  	入口位置#1，代表一个方法入口，方法入口由入口#3和#18组成，中间用一个"."
 *      const #3 = class  #20     //java/lang/Object
 *      const #18 = NameAndType  #4:#5
 *      入口#3是一个class，class是一种引用，所以它引用了入口#20的常量池
 *      入口#21代表一个表示名称和类型(NameAndType),分别由入口#4 和 入口#5组成
 *      const #4 = UTF8	<init>
 *      const #5 = UTF8 ()v
 *      入口#4是一个常量池内容，<init>;代表构造方法
 *      入口#5是一个真正的常量，值为()V,代表没有入口参数，返回值为void，将入口#4,#5反推到入口#18，就代表：名称为构造方法的名称，入口参数个数为0，返回值为void。
 *      入口#20是一个常量，它的值是"java/lang/Object",但这只是一个字符串值，反推到入口3，要求这个字符串代表一个类，那么代表的类是java.lang.Object
 *      综合起来:java.lang.Object类的构造方法，入口参数个数为0，返回值为void，其实在const #1后面的备注中已经标识出来
 *      （这在字节码中本身不存在，只是javap工具帮助合成的）
 *  
 * 
 */
public class MainTest {

	public static void main(String[] args){
		int a = 1;
		int b = 2;
		int c = a + b;
		
	}
}
