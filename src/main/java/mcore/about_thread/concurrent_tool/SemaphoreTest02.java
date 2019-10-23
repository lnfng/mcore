package mcore.about_thread.concurrent_tool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore可以维护当前访问自身的线程个数，并提供了同步机制。
 * 
 * 目的：使用Semaphore可以控制同时访问资源的线程个数，
 * 
 * 例如，实现一个文件允许的并发访问数
 * 
 * 单个信号量的Semaphore对象可以实现互斥锁的功能，并且可以是由一个线程获得了“锁”，
 * 再由另一个线程释放“锁”，这可应用于死锁恢复的一些场合。
 * 
 * 	semaphore: 信号系统，发信号
 *	Cyclic：循环的，有周期性的
 *	Barrier:障碍物，屏障 
 *	Latch：门闩，闩锁
 * @author JackQ
 *
 */
public class SemaphoreTest02 {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		//semaphore 信号系统（铁道），发出信号
		final  Semaphore spA = new Semaphore(1);
		final  Semaphore spB = new Semaphore(0);
		final  Semaphore spC = new Semaphore(0);

		Runnable runnableA = () -> {
			for (int i = 0; i < 10; i++) {
				try {
					spA.acquire();
					System.out.println("线程A");
					Thread.sleep((long)(Math.random()*2000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				spB.release();
			}
		};
		Runnable runnableB = () -> {
			for (int i = 0; i < 10; i++) {
				try {
					spB.acquire();
					System.out.println("线程B");
					Thread.sleep((long) (Math.random() * 2000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				spC.release();
			}
		};
		Runnable runnableC = () -> {
			for (int i = 0; i < 10; i++) {
				try {
					spC.acquire();
					System.out.println("线程C");
					Thread.sleep((long) (Math.random() * 2000));
					spA.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		service.execute(runnableA);
		service.execute(runnableC);
		service.execute(runnableB);
	}

}
