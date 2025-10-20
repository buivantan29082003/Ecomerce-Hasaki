package com.CloneShopee.Bean;

import java.util.Iterator;

public class InterfaceBean {
	public static void main(String[] args) {
		     Counter c=new Counter();
		     
		    	 Thread a=new ThreadCustome(c,"a");
				 Thread b=new ThreadCustome(c,"b");
				 a.start();
				 b.start();
		 
		 
	}
}

class ThreadCustome extends Thread{
	private Counter c;
	String name;
	public ThreadCustome(Counter c,String name) {
		this.c=c;
		this.name=name;
	}
	
	@Override
	public void run() {
		 synchronized (c) {
			 for (Integer a=0;a<10;a++) {
					this.c.increment();
					if(a==5) {
						try {
							c.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						c.notify();
					}
					System.out.println(name+ " - "+this.c.getCount());
					
				}
		 }
		
	}
}

class Counter {
    private int count = 0;

    public    void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
