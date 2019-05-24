package mcore.about_thread.concurrent_tool;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *用于实现两个人之间的数据交换，每个人在完成一定的事务后想与对方交换数据，
 *第一个先拿出数据的人将一直等待第二个人拿着数据到来时，才能彼此交换数据。
 *
 *无论谁先到达，都必须等待对方 */
public class ExchangerTest {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		final Exchanger<String> exchanger = new Exchanger<String>();
		service.execute(new Runnable(){
			public void run() {
				try {				
					Thread.sleep((long)(Math.random()*10000));
					String data1 = "zxx";
					System.out.println("线程" + Thread.currentThread().getName() + 
					"正在把数据" + data1 +"换出去");
					String data2 = exchanger.exchange(data1);
					System.out.println("线程" + Thread.currentThread().getName() + 
					"换回的数据为" + data2);
				}catch(Exception e){
					
				}
			}	
		});
		service.execute(new Runnable(){
			public void run() {
				try {				
					Thread.sleep((long)(Math.random()*10000));
					String data1 = "lhm";
					System.out.println("线程" + Thread.currentThread().getName() + 
					"正在把数据" + data1 +"换出去");
					String data2 = exchanger.exchange(data1);
					System.out.println("线程" + Thread.currentThread().getName() + 
					"换回的数据为" + data2);
				}catch(Exception e){
					
				}				
			}	
		});		
		
		service.execute(new Runnable(){
			public void run() {
				try {				
					Thread.sleep((long)(Math.random()*10000));
					String data1 = "qxx";
					System.out.println("线程" + Thread.currentThread().getName() + 
					"正在把数据" + data1 +"换出去");
					String data2 = exchanger.exchange(data1);
					System.out.println("线程" + Thread.currentThread().getName() + 
					"换回的数据为" + data2);
				}catch(Exception e){
					
				}				
			}	
		});
		// 交换的线程必须是2的倍数。
		
		System.out.println(">> main stop!!!");
	}
}
