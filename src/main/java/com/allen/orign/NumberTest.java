package com.allen.orign;
/**
  public static void test();
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=3, locals=4, args_size=0
         0: iconst_1      //将int类型的常量值1推送到栈顶
         1: istore_0      //将栈顶抛出的数据赋值给第1个slot所在的int类型的本地变量中
         2: iconst_1      //将int类型的常量1推送到栈顶
         3: istore_1      //将栈顶排除的数据赋值给第2个slot所在的int类型的本地变量中
         4: iconst_1      //与int类型的常量值1推送到栈顶
         5: istore_2      //将栈顶抛出的数据赋值给第3个slot所在的int类型的本地变量中
         6: iconst_1      //将int类型为的常量为1推送到栈顶
         7: istore_3      //将栈顶抛出的数据复制给第4个slot所在的int类型的本地变量中
         8: iinc          0, 1      //将第一个slot所在的int类型的本地变量自加1
        11: iinc          1, 1      //将第二个slot所在的int类型的本地变量自
        14: iload_2       			//将第三个slot所在的int类型的本地变量放入栈顶
        15: iinc          2, 1		//将第三个slot所在的int类型的本地变量加1
        18: istore_2      			//将栈顶抛出的数据写入到第三个slot所在的int类型的本地变量
        19: iinc          3, 1      //将第四个slot位置所在的int类型的本地变量自增1
        22: iload_3       		    //将第四个slot位置所在的int类型所在的int类型的本地变量加载到栈顶
        23: istore_3      			//将栈顶数据抛出，写入到第四个slot所在的int类型的本地变量中
        24: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
        27: new           #4                  // class java/lang/StringBuilder
        30: dup           
        31: invokespecial #5                  // Method java/lang/StringBuilder."<init>":()V
        34: iload_0       
        35: invokevirtual #6                  // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        38: ldc           #7                  // String \t
        40: invokevirtual #8                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        43: iload_1       
        44: invokevirtual #6                  // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        47: ldc           #7                  // String \t
        49: invokevirtual #8                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        52: iload_2       
        53: invokevirtual #6                  // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        56: ldc           #7                  // String \t
        58: invokevirtual #8                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        61: iload_3       
        62: invokevirtual #6                  // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        65: invokevirtual #9                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        68: invokevirtual #10                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        71: return        
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
          2      70     0     a       I      // 本地变量a，int类型，作用域从第2行开始，作用域范围是70行
          4      68     1     b       I      // 本地变量b, int类型，作用域从第4行开始，作用域范围是68行
          6      66     2     c       I      // 本地变量c，int类型，作用域从第6行开始，作用域范围是66行
          8      64     3     d       I      // 本地变量d，int类型，作用域从第8行开始，作用域范围是64行
}

NOTE：
	slot其实是4个字节的长度，可以理解为字长的意思，任何一个数据至少会分配一个slot，即使一个byte也就是这样；long，double会占用2个slot。
 *
 *
 */
public class NumberTest {

	public static void main(String[] args){
		test();
	}
	
	public static void test(){
		int a = 1,b=1,c=1,d=1;
		a++;
		++b;
		c = c++;
		d = ++d;
		
		System.out.println(a +"\t"+b+"\t" +c+"\t"+d);
	}
}
