package com.pragmion.learn.threads;

import java.util.Scanner;


class Runner extends Thread{
	public boolean running = true;
	public void run(){
		while(running){
			System.out.println("Hello");
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void shutdown(){
		running = false;
	}
}
public class TestThread {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Runner r1 = new Runner();
		r1.start();
		
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		
		r1.shutdown();
	}

}
