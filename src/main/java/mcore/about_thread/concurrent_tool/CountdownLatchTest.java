package mcore.about_thread.concurrent_tool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 该功能就如有几个'门闩'，必须把全部门闩都打门才能开，才能放行
 * 
 * 犹如倒计时计数器，调用CountDownLatch对象的countDown方法就将计数器减1，
 * 当计数到达0时，则所有等待者或单个等待者开始执行。
 * 这直接通过代码来说明CountDownLatch的作用。
	可以实现一个人（也可以是多个人）等待其他所有人都来通知他，
	这犹如一个计划需要多个领导都签字后才能继续向下实施。
	还可以实现一个人通知多个人的效果，类似裁判一声口令，
	运动员同时开始奔跑。用这个功能做百米赛跑的游戏程序不错哦
	
	semaphore: 信号系统，发信号
	Cyclic：循环的，有周期性的
	Barrier:障碍物，屏障 
	Latch：门闩，闩锁
	
 * @author JackQ
 *
 */
public class CountdownLatchTest {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		final CountDownLatch cdOrder = new CountDownLatch(1);
		/**接收的参数是给countdown 使用的，每countdown一次减1*/
		final CountDownLatch cdAnswer = new CountDownLatch(3);		
		for(int i=0;i<3;i++){
			Runnable runnable = new Runnable(){
					public void run(){
					try {
						System.out.println("线程" + Thread.currentThread().getName() + 
								"正准备接受命令");	
						/**必须等到有线程count down 到0 才能往下进行*/
						cdOrder.await();
						System.out.println("线程" + Thread.currentThread().getName() + 
						"已接受命令");								
						Thread.sleep((long)(Math.random()*10000));	
						System.out.println("线程" + Thread.currentThread().getName() + 
								"回应命令处理结果");						
						cdAnswer.countDown();						
					} catch (Exception e) {
						e.printStackTrace();
					}				
				}
			};
			service.execute(runnable);
		}		
		try {
			Thread.sleep((long)(Math.random()*10000));
		
			/*在这里进行代码处理*/
			System.out.println("线程" + Thread.currentThread().getName() + 
					"即将发布命令");						
			cdOrder.countDown();
			
			System.out.println("线程" + Thread.currentThread().getName() + 
			"已发送命令，正在等待结果");	
			
			cdAnswer.await();
			System.out.println("线程" + Thread.currentThread().getName() + 
			"已收到所有响应结果");	
		} catch (Exception e) {
			e.printStackTrace();
		}				
		service.shutdown();

	}
}
