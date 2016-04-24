package com.pragmion.learn.threads;

public class OrderLogic {

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
		serviceC();

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
	}

	public void serviceC() {
		System.out.println("Inside serviceC()");
	}

}

/*
Test1:
Inside serviceA() -1
Inside serviceB() -1
Inside serviceB() -2
Inside serviceA() -2
Inside serviceB() -3
Inside serviceA() -3
Inside serviceC()

Test2:
Inside serviceA() -1
Inside serviceB() -1
Inside serviceA() -2
Inside serviceB() -2
Inside serviceB() -3
Inside serviceA() -3
Inside serviceC()

Test3:
Inside serviceA() -1
Inside serviceB() -1
Inside serviceA() -2
Inside serviceB() -2
Inside serviceA() -3
Inside serviceB() -3
Inside serviceC()

*
*/