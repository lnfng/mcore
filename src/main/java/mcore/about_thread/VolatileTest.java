package mcore.about_thread;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class VolatileTest {
	
	private static int numNext = 0;
	private static volatile int num = 0;
	
	private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(3, 9, 5L, TimeUnit.SECONDS, 
			new SynchronousQueue<Runnable>(),new ThreadPoolExecutor.CallerRunsPolicy());
	
	public static void main(String[] args) throws Exception {
		
		for (int i = 0; i < 10; i++) {
			final int id = i;
			EXECUTOR.execute(new Runnable() {
				public void run() {
					System.out.println(">> index:"+id);
					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		TimeUnit.SECONDS.sleep(5);
		System.out.println("pool size 01 :"+EXECUTOR.getPoolSize());
		
		
		TimeUnit.SECONDS.sleep(40);
		System.out.println("pool size 02 :"+EXECUTOR.getPoolSize());
		
		
//		for(int i=0;i<10;i++){
//			new Thread(){
//				public void run() {
//					changeNum();
//				}
//			}.start();
//		}
	}
	
	
	static void changeNum(){
		if(num==0){
			num = 123;
			System.out.println("num is changed!!!");
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				// ignore
			}
		}
	}
	

}
