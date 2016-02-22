package com.allen.orign;

public class LoadClassErrorDemo {

	public static void main(String[] args) {
		new Thread() {

			public void run() {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				B.getInstance().test2();
			}
		}.start();

		new Thread() {

			public void run() {
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				B.getInstance().test2();
			}
		}.start();
	}
}

abstract class A {
	public A() {
		list();
	}

	public void list() {
		test();
	}

	abstract void test();
}

class B extends A {

	private final static B instance = new B();

	public static B getInstance() {
		return instance;
	}

	@Override
	void test() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		instance.test2();
	}

	public void test2() {
	}

}