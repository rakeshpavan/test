package com.pragmion.learn.threads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * * This is for OrderLogic
 * 
 * @author Rakesh
 */
public class OrderLogicwithExecutorService {
	private static final ExecutorService executorSrvc = Executors
			.newFixedThreadPool(3);

	public static void main(String args[]) throws InterruptedException,
			ExecutionException {
		ServiceAnAAClient task = new ServiceAnAAClient("requestA");
		System.out.println("Before Submitting call to A and AA");
		Future futureA = executorSrvc.submit(task);
		System.out.println("Task is submitted");
		
		ServiceBClient taskB = new ServiceBClient("requestB");
		System.out.println("Before Submitting call to B");
		Future futureB = executorSrvc.submit(taskB);
		System.out.println("TaskB is submitted");
		
		
		while (!(futureA.isDone()&&futureB.isDone())) {
			System.out.println("Tasks are not completed yet....");
			Thread.sleep(1);
			// sleep for 1 millisecond before checking again
		}
		
		
		String resA = futureA.get().toString();
		System.out.println("Response from Service AnAA is : " + resA);
		String resB = futureB.get().toString();
		System.out.println("Response from Service B is:"+resB);
		executorSrvc.shutdown();
		
		callServiceC(resA+" and "+resB);
		
	}

	private static class ServiceAnAAClient implements Callable {
		private final String req;

		public ServiceAnAAClient(String pReq) {
			this.req = pReq;
		}

		@Override
		public String call() {
			String resA = callA(req);
			System.out.println("Before Calling AA");
			return callAA(resA);
		}
		
		public String callA(String reqA){
			return "Response from A";
		}
		
		public String callAA(String req){
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("request to AA is:"+req);
			return "Response from AA";
		}

	}
	
/*	private static class ServiceAAClient implements Callable {
		private final String req;

		public ServiceAAClient(String pReqAA) {
			this.req = pReqAA;
		}

		@Override
		public String call() {
			return "Response from AA";
		}

	}*/
	
	private static class ServiceBClient implements Callable {
		private final Object reqVO;

		public ServiceBClient(Object pReqB) {
			this.reqVO = pReqB;
		}

		@Override
		public String call() {
			return "Response from B";
		}

	}
	
	private static void callServiceC(String req)
	{
		System.out.println("Input Request to ServiceC is :"+req);
	}
}


/*
Test Output:
###########
Before Submitting call to A and AA
Task is submitted
Before Calling AA
Before Submitting call to B
TaskB is submitted
Tasks are not completed yet....
Tasks are not completed yet....
Tasks are not completed yet....
Tasks are not completed yet....
Tasks are not completed yet....
Tasks are not completed yet....
Tasks are not completed yet....
Tasks are not completed yet....
Tasks are not completed yet....
request to AA is:Response from A
Response from Service AnAA is : Response from AA
Response from Service B is:Response from B
Input Request to ServiceC is :Response from AA and Response from B

 */
