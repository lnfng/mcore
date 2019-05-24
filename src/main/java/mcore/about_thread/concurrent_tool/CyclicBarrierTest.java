package mcore.about_thread.concurrent_tool;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 表示大家彼此等待，大家集合好后才开始出发，分散活动后又在指定地点集合碰面，
 * 这就好比整个公司的人员利用周末时间集体郊游一样，先各自从家出发到公司集合后，
 * 再同时出发到公园游玩，在指定地点集合后再同时开始就餐
 * 
 *  semaphore: 信号系统，发信号
	Cyclic：循环的，有周期性的
	Barrier:障碍物，屏障 
	Latch：门闩，闩锁
 * @author JackQ
 *
 */
public class CyclicBarrierTest {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		
		/*barrier 障碍  		
		 *创建3个障碍，等到所有人都到齐了，才可以推开
		 *cb.await() 等待完后自动执行 */
		final  CyclicBarrier cb = new CyclicBarrier(3);
		for(int i=0;i<3;i++){
			Runnable runnable = new Runnable(){
					public void run(){
					try {
						Thread.sleep((long)(Math.random()*10000));	
						System.out.println("线程" + Thread.currentThread().getName() + 
								"即将到达集合地点1，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");						
						
						/**先到的线程，在此恭候其他的线程,等其他的线程都到齐了再往下进行*/
						cb.await();
						
						Thread.sleep((long)(Math.random()*10000));	
						System.out.println("线程" + Thread.currentThread().getName() + 
								"即将到达集合地点2，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");						
						cb.await();	
						Thread.sleep((long)(Math.random()*10000));	
						System.out.println("线程" + Thread.currentThread().getName() + 
								"即将到达集合地点3，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");						
						cb.await();						
					} catch (Exception e) {
						e.printStackTrace();
					}				
				}
			};
			service.execute(runnable);
		}
		service.shutdown();
	}
}
