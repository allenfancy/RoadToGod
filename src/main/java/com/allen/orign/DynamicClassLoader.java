package com.allen.orign;

import java.net.URL;
import java.net.URLClassLoader;

public class DynamicClassLoader extends URLClassLoader{

	public DynamicClassLoader(URLClassLoader parentClassLoader){
		super(parentClassLoader.getURLs(),parentClassLoader);
	}
	public DynamicClassLoader(URL[] urls) {
		super(urls);
		// TODO Auto-generated constructor stub
	}
	
	public Class<?> defineClassByByteArray(String className,byte[] bytes){
		return this.defineClass(className, bytes, 0,bytes.length);
	}

}
