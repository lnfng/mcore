package mcore.about_thread.threadScope;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/**
 * 目的是想让同一个线程内的共享一个数据
 * 不同的线程内的数据是可以不一样的
 * 就如 A B 两个类在一个线程内共享一个数据
 * @author JackQ
 *
 */
public class ThreadScopeShareDate {
	
	private static Map<Thread,Integer> threadData=new HashMap<Thread,Integer>();
	
	public static void main(String[] args) throws Exception {
		
		for(int i=0;i<100;i++){
			new Thread(new Runnable(){

				public void run() {
					int data = new Random().nextInt();
					threadData.put(Thread.currentThread(), data);
					new A().getData();
					new B().getData();
				}
				
			}).start();
		}
		/**需解决当线程结束了，如何清空map，还有建议使用WeakRefren*/
		Thread.sleep(10000);
		System.out.println(threadData.size());
		
		Runtime.getRuntime().addShutdownHook(null);
	}
	
	static class A{
		public void getData(){
			System.out.println(Thread.currentThread().getName()
					+" in A class data:"
					+threadData.get(Thread.currentThread()));
		}
	}
	
	static class B{
		public void getData(){
			System.out.println(Thread.currentThread().getName()
					+" in B class data:"
					+threadData.get(Thread.currentThread()));
		}
	}
}
