package com.allen.orign;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ASMClassModifyAdpter extends ClassAdapter{

	public ASMClassModifyAdpter(ClassVisitor classVisitor) {
		super(classVisitor);
	}
	
	public MethodVisitor visitMethod(final int access,final String methodName,final String desc,final String signature,final String[] exceptions){
		if("display2".equals(methodName)){
			return null;//屏蔽这个方法
		}
		if("display1".equals(methodName)){
			MethodVisitor methodVisitor = cv.visitMethod(access, methodName, desc, signature,exceptions);
			methodVisitor.visitCode();
			//增加这个语句等价于增加代码:name = "我是Name"
			//加载this到栈顶(此时本地变量其实只有一个this)
			methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
			//idc指令，从常量池中取出值加载到栈顶
			//这个代码会隐藏修改常量池
			methodVisitor.visitLdcInsn("我是Name");
			//putfield指令，修改ForASMTestClass的name属性
			methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, "com/allen/orign/ForASMTestClass", "name","Ljava/lang/String;");
			
			//这条语句等价增加代码:value="我是value"
			//记载this到栈顶
			methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
			//idc指令，从常量池中将值加载到栈顶
			methodVisitor.visitLdcInsn("我是Value");
			//putfield指令，修改ForASMTestClass的value属性
			methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, "com/allen/orign/ForASMTestClass", "value", "Ljava/lang/String;");
			
			//再将一个属性获取出来答应出来
			//getstatic指令，获取System类的out属性
			methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System","out", "Ljava/io/PrintStream;");
			//加载this到栈顶
			methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
			//通过getfield指令将ForASMTestClass类的name属性加载到栈顶
			methodVisitor.visitFieldInsn(Opcodes.GETFIELD, "com/allen/orign/ForASMTestClass", "name", "Ljava/lang/String;");
			//调用out的println方法
			methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
			methodVisitor.visitEnd();
			return methodVisitor;//返回visitor	
		}else{
			//其余方法不做任何处理直接返回
			return cv.visitMethod(access, methodName, desc, signature, exceptions);
		}
	}

}
