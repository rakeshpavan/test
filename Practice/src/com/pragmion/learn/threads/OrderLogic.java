package com.pragmion.learn.threads;

public class OrderLogic {

	String resA, resAA, resB;
	public static void main(String[] args) {
		OrderLogic ol = new OrderLogic();
		ol.processOrder();
	}

	public void processOrder() {

		// call Service A{
		// after response from Service A, call service AA
		// out of Service A is the input to Service AA
		// }

		Thread tA = new Thread() {
			public void run() {
				serviceA();
				serviceAA(resA);
			}
		};
		
		// call Service B{
		// }
		Thread tB = new Thread() {
			public void run() {
				serviceB();
			}
		};
		
		tA.start();
		tB.start();

		try {
			tA.join();
			tB.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// call Service C{
		// output of service AA && output Service B is input for Service C
		// }
		serviceC(resAA, resB);

	}

	public void serviceA() {
		
		try {
			System.out.println("Inside serviceA() -1");
			Thread.sleep(100);
			System.out.println("Inside serviceA() -2");
			Thread.sleep(100);
			System.out.println("Inside serviceA() -3");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		resA = "A";
	}
	
	public void serviceAA(String pStr) {
		System.out.println("Input String to AA:"+pStr);
		
		try {
			System.out.println("Inside serviceAA() -1");
			Thread.sleep(100);
			System.out.println("Inside serviceAA() -2");
			Thread.sleep(100);
			System.out.println("Inside serviceAA() -3");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		resAA = "AA";
	}

	public void serviceB() {
		try {
			System.out.println("Inside serviceB() -1");
			Thread.sleep(100);
			System.out.println("Inside serviceB() -2");
			Thread.sleep(100);
			System.out.println("Inside serviceB() -3");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		resB = "B";
	}

	public void serviceC(String a, String b) {
		System.out.println("Inside Service C:"+a);
		System.out.println("Inside Service C:"+b);
		System.out.println("Inside serviceC()");
	}

}

/*
Test1:
Inside serviceB() -1
Inside serviceA() -1
Inside serviceA() -2
Inside serviceB() -2
Inside serviceB() -3
Inside serviceA() -3
Input String to AA:A
Inside serviceAA() -1
Inside serviceAA() -2
Inside serviceAA() -3
Inside Service C:AA
Inside Service C:B
Inside serviceC()


Test2:
Inside serviceB() -1
Inside serviceA() -1
Inside serviceB() -2
Inside serviceA() -2
Inside serviceA() -3
Inside serviceB() -3
Input String to AA:A
Inside serviceAA() -1
Inside serviceAA() -2
Inside serviceAA() -3
Inside Service C:AA
Inside Service C:B
Inside serviceC()

Test3:
Inside serviceA() -1
Inside serviceB() -1
Inside serviceA() -2
Inside serviceB() -2
Inside serviceB() -3
Inside serviceA() -3
Input String to AA:A
Inside serviceAA() -1
Inside serviceAA() -2
Inside serviceAA() -3
Inside Service C:AA
Inside Service C:B
Inside serviceC()

*
*/