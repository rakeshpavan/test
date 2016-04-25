package com.pragmion.learn.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * * This is for OrderLogicwithCompletableFuture
 * 
 * @author Rakesh
 */
public class OrderLogicwithCompletableFuture {

	private static ExecutorService executor = Executors
			.newFixedThreadPool(3);

	//String resA, resAA, resB;
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		OrderLogicwithCompletableFuture ol = new OrderLogicwithCompletableFuture();
		ol.processOrder();
	}

	public void processOrder() throws InterruptedException, ExecutionException {

		// call Service A{
		// after response from Service A, call service AA
		// out of Service A is the input to Service AA
		// }
		
		 CompletableFuture<String> futureA = CompletableFuture
                .supplyAsync(() -> serviceA())
                .thenApply(a -> serviceAA(a));
		
		// call Service B{
		// }
		 CompletableFuture<String> futureB = CompletableFuture
                .supplyAsync(() -> serviceB());

		// call Service C{
		// output of service AA && output Service B is input for Service C
		// }
		 CompletableFuture<String> futureC = futureA
				.thenCombineAsync(futureB, (resA, resB) -> serviceC(resA, resB));

	}

	public String serviceA() {
		
		try {
			System.out.println("Inside serviceA() -1");
			Thread.sleep(100);
			System.out.println("Inside serviceA() -2");
			Thread.sleep(100);
			System.out.println("Inside serviceA() -3");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return "Response from A";
	}
	
	public String serviceAA(String pStr) {
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
		return "Response from AA";
	}

	public String serviceB() {
		try {
			System.out.println("Inside serviceB() -1");
			Thread.sleep(100);
			System.out.println("Inside serviceB() -2");
			Thread.sleep(100);
			System.out.println("Inside serviceB() -3");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return "Response from B";
	}

	public String serviceC(String a, String b) {
		System.out.println("Inside Service C():"+a);
		System.out.println("Inside Service C:"+b);
		System.out.println("Inside serviceC()");
		return "C";
	}



}

/*
 
Test Run1:
Inside serviceA() -1
Inside serviceB() -1
Inside serviceB() -2
Inside serviceA() -2
Inside serviceB() -3
Inside serviceA() -3
Input String to AA:Response from A
Inside serviceAA() -1
Inside serviceAA() -2
Inside serviceAA() -3
Inside Service C():Response from AA
Inside Service C:Response from B
Inside serviceC()
 */
