package com.pragmion.learn.threads;

public class Test {

	private int  count = 0;
	private synchronized void increment(){
		count++;
	}
	
	public static void main(String[] args) {
		  int oneMillion_ = 1_000_000; //new
		    int oneMillion = 1000000;
		    if (oneMillion_ == oneMillion){
		        System.out.println(true);
		    } else{
		        System.out.println(false);
		    }
	
	}
	
	private void doWork(){
		
		Thread t1 = new Thread(new Runnable(){
			@Override
			public void run() {
				for(int i=0;i<10000;i++){
					increment();
				}
			}
		});

		Thread t2 = new Thread(new Runnable(){
			@Override
			public void run() {
				for(int i=0;i<10000;i++){
					increment();
				}
			}
			
		});
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Count is: "+count);
	}

}
