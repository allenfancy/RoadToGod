package com.allen.orign;

import java.nio.ByteBuffer;

public class ByteBufferOOM {

	
	public static void main(String[] args){
		ByteBuffer.allocateDirect(257*1024*1024);
	}
}
