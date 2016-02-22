package com.allen.orign;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class InstForTransformer {

	private static Instrumentation inst;
	public static void premain(String agentArgs,Instrumentation instP){
		inst = instP;
		//设置true后，可以在运行时进行restransformClasses方法，否则调用retransformClasses无效
		inst.addTransformer(new TestTransformer(),true);
		
		//instP.addTransformer(new TestTransformer());
	}
	
	public static void reTransClass(Class<?> clazz) throws UnmodifiableClassException {
		inst.retransformClasses(clazz);
	}
}
