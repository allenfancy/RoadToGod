package com.allen.orign;

import java.util.ArrayList;
import java.util.List;

/***
 1.Class字节码的加载
 	ClassLoader继承关系从BootStrapClassLoader 开始，也是由最先加载类，然后是ExtClassLoader，接下来是AppClassLoader(应用程序默认的)，
 	最后是用户自己ClassLoader(通常在容器下编写代码，都是由容器自定义创建的ClassLoader创建的类)
  用途：
  	1.BootStrapClassLoader 主要是用于加载一些Java自带的核心类(EXP:java.lang.*),通常这些核心类的CLass被签名，不能被替换，它是由JVM内核实现的，
  	  在Hotspot VM由C++实现的，有了它加载最核心的内容，才会有后面的CLassLoader的存在。
  	2.ExtClassLoader 是用来加载 jre/lib/ext/目录下的jar包得，用户也可以将自己写的Jar包放到这个目录下，通过ExtClassLoader来加载。
  	3.AppClassLoader 是用户 可见的ClassLoader，它加载的是classpath目录下得内容，也就是和classpath相关的类，在默认情况下都是由它来加载。
  	4.用户自定义的ClassLoader要加载的内容可能不存在系统的classpath范围内，设置不是Class文件或Jar文件，也就是加载方式是可以完全自己定义。
  	
  	用户自定义的ClassLoader一般继承于URLClassLoader，也可以继承ClassLoader或者SecureClassLoader，他们之间本身也是继承关系，根据实际情况重写不同的方法即可。
  	
  	说明：
  	Class本身也是一种对象，适用于描述普通Java对象的格式的一种对象，既然是对象，那么它的加载也需要一个过程。
  	第一步：读取文件，首先找到.class文件，它将以『类全名+ClassLoader名称』作为唯一标识，加载于方法区内部。
    在加载过程中，首先申请父ClassLoader来加载，也就是尽量用父类的ClassLoader来加载，如果父类的ClassLoader无法加载这个类，则会由子类来加载，最终没有找到对应类，则会抛出ClassNotFoundException。
 	第二步：链接。这个动作内部就是要对加载的字节码进行解析，校验，看是否符合字节码的规范，如果不符合就会抛出ClassNotFoundError异常。
 		Note：第一步仅仅是找到要读取的文件，分配了内存，也只是一个简单的bytep[]数组，这一步才会为Class对象分配内存，两者完全不一样。
 			  这一步可选的是讲常量池中的符号引入用解析为直接引用
 	第三步：初始化。会调用这个Class对象自身的构造函数，对静态变量，Static块进行赋值(通过javap指令可以发现，其实许多静态变量的赋值会在编译时放入在static块中)
 	总结：所有的类在使用前都必须被加载和初始化，被初始化过程由<clinit>方法来确保它是线程安全的(包括static块必须要执行完成，才能被使用)。如果有多个线程同时尝试访问该类，则必须等待static块执行完成，否则都将被阻塞。
    
    在程序启动的时候，如果需要查看加载了那些类的话。使用-XX:+TraceClassLoading来实现
 
 	ClassLoader补充知识点：
 		1.同一个"进程"内任何一个ClassLoader加载的Class都可以由任意一段程序直接或者间接地访问到。如：在常见的Web容器中(Tomcat)中加载不同的deploy会进行相互隔离，它使用了不同的ClassLoader来加载不同的deploy，
 		  也就是并非真正意义上得隔离。要想跨ClassLoader访问一些信息是比较麻烦的，不过也并非不可能，例如使用JMX就可以做。
 		2.在Class加载过程中，对于同名的类型(package路径以及类名都是一样的)，在同一个ClassLoader中只会被加载一次(除非被卸载或被重新定义)
 		3.JVM做FULL GC的时候 ，允许对Class执行unload操作，但是苛刻的条件是响应的ClassLoader下所有的Class都没有实例的应用，否则这个ClassLoader及它下面的所有的CLass都释放不掉。跟踪类的卸载，可以通过添加启动参数:
 			-XX:+TraceClassUnloading达到目的
 		4.如果希望任意一段代码中所创建的对象是通过自定义的ClassLoader创建的，就需要这段代码的调用者所在类是通过相应的ClassLoader加载的，因此调用者通常需要制定ClassLoader去实例化才能达到效果，其次，自定义的ClassLoader
 		  所加载的调用调用者的类一般不放在默认的classpath后面，否则系统将会被系统的ClassLoader所加载。如果确实遇到这样的情况，那么这个ClassLoader在创建的时候，强制将parentClassLoader设置为空，也能达到对应的目的。
 		5.查看一个类的本身是使用哪一个ClassLoader来加载的。直接使用类名.class.getClassLoader()
 		6.当使用Class.forName()的时候，有一个重载方法可以带上制定的ClassLoader，如果没有带上ClassLoader，则默认与"调用者当前类"加载的ClassLoader一致
 		7.CLassLoader自身也是一个Java类，因此它本身也需要加载，加载的顺序是先由JVM内在级别的ClassLoader开始的。即：CLassLoader本身是一类更为特殊的Class，在没有ClassLoader时谁来加载它呢？只能由JVM内核来加载。
 		8.当通过一个指定的ClassLoader调用方法loadClass("类名")加载一个类的时候，这个ClassLoader会首先判断自身是否加载该类，如果未加载，就到parentClassLoader找是否加载该类，依次类推。如果最终没有找到，则会从顶层
 		  ClassLoader开始去尝试加载该类，如果父类无法加载，子类在尝试加载，如果在这个ClassLoader所能找到的路径下，没有找到这个类，则会抛出ClassNotFoundException。
 		9.加载类的时候，如果类文件出现了问题(字节码构造不符合规范，可以自己篡改一个Class文件)；类中引用了native方法，但是缺少对应的动态链接库(.dll、.so文件)；由于代码问题直接或者间接出现对当前类的使用，导致加载失败，
 		  此时会爆出来NoClassDefFoundError
 	   10.当用到反射时，反射的代码通常会将类的操作实体做一份拷贝给我使用。如果频繁大量的使用反射，为了提升效率，可以将相关的Class，method,filed等信息保存起来，便于复用
 	   11.ClassLoader内部有一个defineClass方法(方法是protected类型，继承才能使用)，通过传入的byte[]数组就可以动态加载Class类
 	   12.Class加载的时候，如果父类并没有被加载到相关的ClassLoader中，则会先加载父类，并且会先执行父类的static块(这个顺序似乎不是很重要，因为程序中必须要等待相应的Class加载完成后才能使用对象)
 	   
  JIT，会动态内联以及代码的进一步优化并将它尽量Cache住。在写代码的时候，应该考虑是否可能被JIT优化。
  引入JIT原因：在JVM加载过程中会产生很多的中间代码，会坐一些优化后转为目标代码开始运行，运行的时就会有JIT的介入。
  JIT优化会将优化后的本地代码存放在CodeCache中，这个空间的默认大小是48MB，可以使用参数：
  	参数： -XX:ReservedCodeCacheSize=128M来修改这个空间的大小
 	参数： -XX:+Use CodeCacheFlushing可以对CodeCache进行清理。
 	另外使用：
 	参数： -XX:ClCompilerCount的设置相对较大可以在一定程度上提升JIT编译的速度(默认为2)。在高并发系统中适当设置可以有效地提升性能。   
 
  疑问1：同名的类不是只能被加载1次吗？
  答：	 一个ClassLoader对同一个类只能加载1次，不同的ClassLoader分别加载同一个类。
  疑问2：除了ClassLoader加载多个类Class外，直接修改这个Class可以吗？
  答	：	可以，"字节码增强"可以做到
  加载这么多的Class，当Class不需要的时候GC会把他们回收
  
 2.字节码增强
 	AOP技术其实是字节码增强技术，JVM提供的动态代理追根到底也是字节码增强技术。
 	字节码增强技术有俩种实现机制：一种是通过创建原始类的子类，即是动态创建的这个类继承原来的类，现在的SpringAOP也正是通过这样的方式来实现的。它们创建的子类通常以"原始类的名称"为前缀，再加上一个奇怪的后缀，以避免类名重复、
 							 一种是很暴力的，即直接修改原先的Class字节码，在许多类的跟踪过程中会使用到此技术。
 	实现字节码增强通过如下俩个步骤：
 	1.在内存中获取到原始的字节码，然后通过一些"开源届"写好的API来修改它的byte[]数组，得到一个新的byte[]数组
 	2.将这个新的byte[]数组写到PermGem区域，也就是加载它或者替换原来的Class字节码(字节码增强还可以在进程外调用完成)
 	开源中的API有ASM，javassist，BCEL，SEPP，CGLib(基于ASM)等基础技术。
 	获取新的字节数组后，就要来加载它。其中一种方式是基于Java的Instrument API来实现，另一种方式是通过指定的CLassLoader来完成。
 3.HotSpot VM
 	Heap(大小使用-Xms设置)堆分为Young(使用-Xmn来设置) 和 Old，根据不同的场景设置不同的Yonug和Old的大小和比例值
 	查看默认值方法有如下俩种：
 	1.jinfo 来获取
 	2.-XX:+PrintFlagsFinal获取结果(JDK1.6 update21开始支持)
 	使用-XX:+PrintFlagsInitial输出真正的默认参数列表，只不过这个参数就像是程序中写好的默认值，并不是系统启动时设置的默认值。
 	JVM真正用的是什么呢？
 	例如：-XX:NewSize -XX:MaxNewSize,设置-Xmn后相当于将这俩个设置为一样大，也有用参数-XX:NewRatio来设置。
 	Heap总大小使用-Xms来设置它的初始值(这部分不包括PermGen(永久代)的大小)。使用-Xmx来设置它的上限，在服务器端我们通常将这俩个值设置为一样大。因为大小的不断调整只是为了给某些程序节省开销。
 	通用Java还有俩个参数来控制，当GC后剩余空间百分比小于-XX:MinHeapFreeRatio时，JVM的堆内存开始变大(一般输出为40，表示40%)，
 	反之，当GC后空闲内存大于-XX:MaxHeapFreeRatio时(一般为70，代表70%)，JVM堆内存开始变小
 	Young空间的管理：
 		Young空间被分为2个部分，3个板块，即"1个Eden区+2个Survivor区"。
 		Eden区域干什么?使用new 或者 newInstance()等方式创建的对象，默认都是先就爱那个对象的空间放到Eden区域(除非这个对象太大了，Eden区域放不下，或者设定了一个对象阈值-XX:PretenureThreshold,这样的对象在分配的时候就会直接进入Old区)
 		2个Survivor区通常简称为S0,S1,理论上可以认为S0和S1是一样大得。当某些动态参数设置后，这俩个区域的大小可能会发生一些改变。
 		如个这几个区域是如何配合工作的呢?
 		在不断创建对象的过程中，Eden区域会满，满的时候就会开始做Young GC(即成为Minor GC),而Young空间的第1次GC就是找出"Eden区域活着的对象"，将这些活着的对象向S0或S1其中一个区域存放。
 		EXP：
 		如果第一次选择了S0，它会逐步将或者的对象拷贝到S0区域，但是如果S0空间放满了，剩下的活着对象就只能放在Old区域了。接下来的操作就是将Eden区域清空，此时S1区域为空得。
 		第二次Eden区域满的时候，就将"Eden中或者的对象+S0中活着的对象"迁移到S1，迁移方式也是一样的。(如果S1放步下，对象就会放到Old区域)，只是这一次对象来源增加了S0区域的对象，最后被清空的区域是Eden区域和S0区域。
 		S0和S1始终有一个是空的。
 		反反复复多次没有被淘汰的对象也会放入Old区域(也是由一些参数来决定的)
 		活着的对象：从根引用开始找到对象，对象内部的属性可能也是引用，只要能级联到的都会被认为是存活的对象(包括C Heap区域的对象空间)
 		根："本地变量引用"、"操作数栈引用"、"PC寄存器"、"本地方法栈引用"、"静态引用"等这些都是根。
 		一个活着的对象到底进入多少次Minor GC后对象才会进入Old区域呢？
 			一般用-XX:MaxTenuringThreshold=15,这个参数来设置。当一个对象被第一次GC后，计数器会在此对象头哦不相应位置标记1。这个参数没有必要去修改它。
 4.永久代
 	Class加载，其实是被加载到永久代(PerGen)。Class会占用空间，而且并不小，Class本身算是永久代的最大用户，不过永久代是否还会存储其他的内容呢?
 	当我们调用String对象的intern()方法时，它会在永久代中一块所谓的"常量池区域"中查找是否有相同值的常量(使用equals()方法进行匹配)，如果没有找到，就在永久代中生成一个具有相同的String对象并返回这个String对象的引用；如果找到了，则直接返回所找到的String对象的引用地址。
 	当程序中不断使用不同的字符串去操作intern()，并这些字符串都被直接或者间接地引用时，常量池迟早会被撑爆。
 	静态(static)数据放在哪里？
 	静态数据存储的位置其实是在方法区内部，也就是Class所存在的位置内部，在Class初始化时，这块区域就被分配了。通俗理解：只需要知道它在Perm区域中且要占用一定大小的空间就可以了。
 	注意：
 		如果是静态引用，它的生命周期将是从所在类加载到所在类卸载(包括进城退出)；
 		如果是普通类型(例如int long等基本变量类型)，那么占用的是和这些类型等价宽度的空间大小；
 		如果是引用，则存放引用的大小。但是，它所引用的对象，将具有较长的生命周期(如果是final类型，则它引用对象将与其生命周期一致)
	永久代的设计初衷是最初Java认为这块区域应该不会被回收或几乎不会去参与回收，就像我们通常认为Class结构一旦加载就不会被改变一样，而且这样更加适合于JIT动态优化。

 5.常见的虚拟机回收算法
 	EXP:如果设置参数-XX:UseParallelGC,此时就会对Young空间启动Parallel Scavenger方式，对Old空间将采用MSC方式；
 		如果设置参数-XX:UserParallelOldGC,Young空间将保持不变，Old空间将采用Parallel Old方式。
 	1.串行GC
 	  串行GC就是在GC时由单个线程来完成，一般在"Client模式"下这是默认的，或者在运行时加上:-XX:+UseSerialGC,也就会采用串行GC。
 	  （上文提到的Old区域，在串行GC模式下也叫作Tenured），因为串行GC对Old区域回收的算法命名为Tenured
 	  对于服务端程序，尤其是并发量较大或者处理大数据的服务端程序，通常会使用较大的内存来承载用户的访问。面对较大的内存，如果仅仅正对Young空间的回收也许用串行的GC就可以慢去需求(因为Young空间存活的都是少数)，但是如果发生FULL GC，那么串行回收的效率是远远不过的。
 	2.ParallelGC与ParallelOldGC
 	  如果系统启动默认设置了-server参数，那么就默认使用-XX:+UseParallelGC参数，在64位默认下得JVM和OS中，默认也会使用-server参数。-XX:ParallelOldGC参数(JDK1.6出现)，都是并行GC。
 	  区别：最重要的区别在与它是用多线程来处理的。在启动程序的时候，可以使用参数-XX:+PrintGCDetails来看他们对Young空间，Old空间、Perm空间分别采用什么方式来作GC
 		   如果对输出日期格式有要求，可以使用-XX:+PrintGCDateStamps来输出日期格式
 	  可以设置 -XX:+UseParallelGC参数的情况下，可以通过-XX:MaxGCPauseMillis参数来设置最多的暂停时间，也可以通过-XX:GCTimeRatio参数来设置GC时间的比例。
 	  -XX:+UseParallelGC参数仅仅针对Minor GC，Major GC依旧是使用一个线程去完成，他对Old区域的回收采用的MSC(mark Sweep Compact)方式，它会对存活着的对象标记完成后，对没有存货的对象做清楚(包括Old区域，Perm区域，C Heap区域中的内容)，然后进行压缩
 	  当启用-XX:+UseParallelOldGC参数后，对Old去采用的算法是ParOldGen算法，它将对Old区域采用Parallel Compacting(并行压缩)，而且是部分压缩。
 	  
 	总结：在并行GC下最少有俩种情况会导致Full GC。
 		 1.Old区域满的时候(确切地说，是要晋升的对象大于Old区域剩余的空间)
 		 2.Old区域的剩余空间已经小于平均晋升空间的大小的时候。
 		 其他可能会导致Full GC的情况
 		  1.Perm区域满的时候也会导致Full GC
 		  2.系统中使用System.gc()的时候也会默认导致Full GC，这样的Full GC在日志中可以看到关键字"System"。设置-XX:DisableExplicitGC参数后，代码中的System.gc()就不在生效了。
 		  3.dump内存的时候，例如：通过jmap等工具来dump内存的时候，也同样的会发生一次Full GC在dump.
 		  
  6.浅析Java对象的内存结构
  	1.原始类型与对象的自动拆箱和装箱
  	2.对象内存结构
  		Class本身就是一个对象，都以KB为单位，如果new Integer()为了表示一个数字就占用KB级别的内存能接受吗?
  		字节码增强技术，如果对象的结构发生改变，是不是要让每个对象都感知到呢。
  	为了表示对象的属性和方法等信息，不得不需要这个结构描述。Hotspot VM使用对象头部的之歌指针指向Class区域的方式找到对象的Class描述，以及内部的方法、属性入口等。
  	对象头部不止存储这些内容，还有：是否加锁，GC标识位，Monir GC次数、对象默认的hashCode（System.identityHashCode()可以获取这个值）等信息。
  	32位的系统下，存放Class指针的空间大小是4字节，Mark Wording空间大小也是4个字节，因为就是8个字节的头部，如果数组还需要增加4字节表示数组的长度。
  	64位系统以及64位的JVM下，开启指针压缩(参数是-XX:+UseCompressedOOPS,现在很多的JVM在小于32位的情况下是默认打开的)，那么头部存放Class指针的空间大小还是4字节，而Mark Wording区域会变大，变称为8个字节，也就是头部最少为12个字节
  	若未开启压缩，那么保存Class指针的空间大小也会变成8字节，那么对象头部会变成16个字节。另外，在64位模式下，若未开启压缩，引用也会变成8字节。
  	Java对象将以8个字节对齐在内存中，也就是对象占用的空间不是8字节的倍数，将会被补齐为8字节的倍数。
  	估算常用对象占用的空间大小(以32位为例子)：
  	当new Obejct()时（其实对象中里面什么属性都没有），JVM将会分配8个字节的空间，128个对象将占用1KB的空间
  	如果是new Integer()，那么对象里面将包含一个int值，占用4字节，对象此时就是12字节，按照对象8字节对齐的说法，它将对齐到16字节。
  	对象内部属性是怎么排序的？
  	在默认情况下，HotSpot VM会按照一个顺序排序对象的内部属性，
  	这个顺序是：
  	long/double -> int/float -> short/char -> byte/boolean -> Reference(与对象本身的属性顺序无关)。
  	普通类型，Java首先按照宽度由大到小的顺序排序，这样处理的好处就是可以让属性排布的顺序很小。不过，对象的引用不是最小的，放在后面可能是为了Java语言的统一处理。
  	Exp1：
  	class A {
  		byte b1;
  	}
  	在32位的系统下，除了8字节的头部，byte会单独占用1字节，但是padding对齐后，占用16个字节
  	Exp2:
  	class A {
  		byte b1,b2,b3,b4,b5,b6,b7,b8;
  	}
  	添加了8个属性，其实还是16个字节，此时的利用效率最高
  	Exp3：
  	class A {
  		byte b1,b2,b3,b4,b5,b6,b7,b8,b9;
  	}
  	此时变为24个字节的宽度，实际的Body部分只有17个字节，是对齐后到24个字节。
  	Exp4：
  	class A {
  		int i;
  		String str;
  	}
  	此时对象空间占用的也是16个字节，因为int占用了4个字节，而String的引用也占用了4个字节，(这里由于String没有赋值，所以本身的大小没有计算)
  	Exp5：
  	class A {
  		int i ;
  		byte b;
  		String str;
  	}
  	按照8字节码对齐原则，对象大小应当是24字节。
  	在Hotspot VM中，对象排布时，空隙是在4字节基础上的(在32和64位压缩模式下)，例子5中，int后面跟着一个byte，空隙只剩下3字节，对象引用需要4字节来存放，因此byte和对象引用之间就会有3字节对齐，对象引用排布后，最后会有4字节对齐，
  	因此结果上依然是7字节对齐。
  	在Hotspot VM对象属性默认的排布方式，通过设置参数 -XX:FieldsAllocationStyle=0（默认是1）参数，引用会排布到最前面。
  	最后：静态属性所占用的空间通常不计算到对象本身的空间上，因为它的引用是在方法区，所以在计算Body里面到底有多少东西时，要抛出这些静态元素。
  	有继承的对象属性排序：
  		在Java对象中，大部分都有继承关系，所以继承的排布我们不得不考虑。在HotSpot VM中，类的继承并不会使得在创建对象时会创建多个，也就是说，它们只会有一个对象头部，但是在内部结构中，父类的属性依旧要被分配到相应的对象中，这样才能在程序中通过父类访问它的属性。
  	父类的属性不能喝子类混用，它们必须单独排布在一个地方，可以认为它们就是从上到下的一个顺序。以俩重继承为例，对象继承属性排布规则如下所示：
  	|	Mark Word			|
  	|	指向Class的指针		|
  	|	父类的父类属性		|
  	|	属性对齐				|
  	|	父类的属性			|
  	|	属性对齐				|
  	|	当前类的属性			|
  	|	整个对象对齐			|
  	|-----------------------|
  		对象继承属性排布规则
  	这里的对齐有俩种：一个是整个对象的8字节对齐，二是父类到子类的属性对齐。
  	
  	4.代码计算一个对象的大小：
  		
  
 7.常见的OOM：
 	1.HeapSize OOM
 	   Old区剩余的内存，已经无法满足将要晋升到Old区域的对象大小，就会出这种错
 	2.PermGen OOM
 	
 	3.DirectBuffer OOM
 		DirectBuffer这块区域是什么呢？
 		Java的普通I/O采用输入/输出流的方式来实现，输入流都会经历从终端直接内存再到JVM过程；输出流是反过来的，是从JVM直接到终端，这中间多次Kenel与JVM之间的内存拷贝，有些时候为了提高速度，就会想办法直接内存。
 		Java中有一块区域叫做DirectBuffer，这块区域不是Java的Heap，而是C Heap的不一部分。也有一定的大小限制，通常在ULL GC时回收，也可以写代码来回收分配的空间。
 		通过设置启动参数：-XX:MaxDirectMemorySize=256M来设置大小。
 */
public class Note {

	public static void main(String[]args) throws ClassNotFoundException{
	
		//testHeapSizeOOM();
		testPermOOM();
	}

	public static void testClassLoad(){
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		System.out.println(classLoader.getResource(""));
		System.out.println(classLoader.getClass().getName());
		System.out.println(classLoader.getParent().getClass().getName());
		ClassLoader classLoader1 = classLoader.getParent();
		System.out.println(classLoader1.getParent());
		
		
		System.out.println(Note.class.getClassLoader());
		
		System.out.println(NormalObjectSizeOf.sizeOf(new String()));
	}
	
	public static void testHeapSizeOOM(){
		List<String> lists = new ArrayList<String>();
		while(true){
			lists.add("内存溢出了，");
		}
	}
	
	public static void testPermOOM(){
		
	}
}
