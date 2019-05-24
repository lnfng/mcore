package mcore.about_thread.executors;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 演示Executors
 * 17-08-12:
 * 	新获消息, 不推荐使用Executors来创建线程池,而是推荐使用ThreadPoolExcutor
 * 	(还没认真考虑):ThreadPoolExecutor 需要自己控制实现,避免消耗过大或者信息堆积
 * @author JackQ
 * 
 */
public class ThreadPool {

	public static void main(String[] args) {
		ExecutorService executor = null;
		/**线程会重用(线程池的作用)，没任务的时候线程会保持存活一分钟，再没任务就会消毁
		 * 不推荐使用该方式去建立线程池,若是任务多的时候线程数是不可控的*/
		executor = Executors.newCachedThreadPool();
		
		/**线程会重用(线程池的作用)，它的线程数是一定的，线程会线程池中存在,直到显式地关闭
		 * 大小不好控制 */
		executor = Executors.newFixedThreadPool(500);
		
		/**线程会重用(线程池的作用)，它的线程数只有一个，线程会线程池中存在,直到显式地关闭 */
		executor = Executors.newSingleThreadExecutor();
		
		/**线程会重用(线程池的作用)，它的线程数只有一个，线程会线程池中存在,直到显式地关闭
		 * 但是要注意,如果这单线程终止执行期间由于失败关闭之前,如果需要执行后续任务,一个新的将取而代之 */
//		ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
//		ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(5);
		
		executor = testThreadPoolExecutor();
		
		executorTask(executor);
		
//		executorScheduledTask(scheduledExecutor);
	}
	
	private static void executorScheduledTask(ScheduledExecutorService executor) {
		/**
		 1、scheduleAtFixedRate 方法，顾名思义，它的方法名称的意思是：
		        已固定的频率来执行某项计划(任务)。
		 2、scheduleWithFixedDealy,相对固定的延迟后，执行某项计划。
		        单从它们的字面来看，还是让人困惑，跟TC的管猿似的，文字游戏。
			还是比较简单明了的描述比较好：第一个方法是固定的频率来执行某项计划，
			它不受计划执行时间的影响。到时间，它就执行。
  			而第二个方法，相对固定，据鄙人理解，是相对任务的。即无论某个任务执行多长时间，
  			等执行完了，我再延迟指定的时间。也就是第二个方法，它受计划执行时间的影响。
		 */
		Runnable runnable=new Runnable() {
			public void run() {
				System.out.println("'"+Thread.currentThread().getName()+"' bombing...");
			}
		};
		for (int i = 1; i <= 10; i++) {
			executor.scheduleWithFixedDelay(
					runnable,
					0,
					1, /**这参数是表示，执行上个任务执行完后隔一秒继续执行，跟scheduleAtFixedRate不一样*/
					TimeUnit.SECONDS);
		}
		//不能在这里进行shutdown 动作，否则无法继续执行
		//executor.shutdown();
	}

	private static void executorTask(ExecutorService executor) {
		try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e1) {}
		for (int i = 1; i <= 3000; i++) {
			
			final int taskId=i;
			executor.execute(new Runnable() {
				public void run() {
					//try {Thread.sleep(new Random().nextInt(500));} catch (Exception e) {}
					System.out.println("'"+Thread.currentThread().getName()+"' run the task :"+taskId);
				}
			});
		}
		//表示不再接收任务，任务完成自动关闭
		executor.shutdown();
		
		/*
		 while(!EXECUTOR.isTerminated()){
			if(EXECUTOR.awaitTermination(200, TimeUnit.MILLISECONDS)){
				Long startTime = TIME.get("startTime");
				Long endTime = TIME.get("endTime");
				System.out.println(">> 打包完成! 花费时间:"+(endTime-startTime));
			}
		}
		 
		 EXECUTOR.awaitTermination(200, TimeUnit.MILLISECONDS) 该方法：在执行shutdown关闭动作
		 	后，按指定时间频率去检查任务是否执行完毕。返回true/false。会阻塞。
		 EXECUTOR.isTerminated() 单次检查线程池是否关闭。
		 */
		
		
		System.out.println("finish submited ...");
	}
	
	/**
	 * Executors 的实现都是通过ThreadPoolExecutor来创建的
	 */
	static ExecutorService testThreadPoolExecutor(){
		/*
		   	线程池按以下行为执行任务
				当线程数小于核心线程数时，创建线程。
				当线程数大于等于核心线程数，且任务队列未满时，将任务放入任务队列。
				当线程数大于等于核心线程数，且任务队列已满
				若线程数小于最大线程数，创建线程
				若线程数等于最大线程数，抛出异常，拒绝任务
				
			系统负载
				参数的设置跟系统的负载有直接的关系，下面为系统负载的相关参数：
				tasks，每秒需要处理的最大任务数量
				tasktime，处理第个任务所需要的时间
				responsetime，系统允许任务最大的响应时间，比如每个任务的响应时间不得超过2秒。
				
				参数设置
				
				corePoolSize:
				每个任务需要tasktime秒处理，则每个线程每钞可处理1/tasktime个任务。系统每秒有tasks个任务需要处理，
				则需要的线程数为：tasks/(1/tasktime)，即tasks*tasktime个线程数。假设系统每秒任务数为100~1000，每个任务耗时0.1秒，
				则需要100*0.1至1000*0.1，即10~100个线程。那么corePoolSize应该设置为大于10，
				具体数字最好根据80 20原则，即80%情况下系统每秒任务数，若系统80%的情况下第秒任务数小于200，
				最多时为1000，则corePoolSize可设置为20。
				
				queueCapacity:
				任务队列的长度要根据核心线程数，以及系统对任务响应时间的要求有关。队列长度可以设置为(corePoolSize/tasktime)*responsetime： (20/0.1)*2=400，即队列长度可设置为400。
				队列长度设置过大，会导致任务响应时间过长，切忌以下写法：
				LinkedBlockingQueue queue = new LinkedBlockingQueue();
				这实际上是将队列长度设置为Integer.MAX_VALUE，将会导致线程数量永远为corePoolSize，再也不会增加，当任务数量陡增时，任务响应时间也将随之陡增。
				
				maxPoolSize:
				当系统负载达到最大值时，核心线程数已无法按时处理完所有任务，这时就需要增加线程。每秒200个任务需要20个线程，那么当每秒达到1000个任务时，则需要(1000-queueCapacity)*(20/200)，即60个线程，可将maxPoolSize设置为60。
				
				keepAliveTime:
				线程数量只增加不减少也不行。当负载降低时，可减少线程数量，如果一个线程空闲时间达到keepAliveTiime，该线程就退出。默认情况下线程池最少会保持corePoolSize个线程。
				
				allowCoreThreadTimeout:
				默认情况下核心线程不会退出，可通过将该参数设置为true，让核心线程也退出。
				
				以上关于线程数量的计算并没有考虑CPU的情况。若结合CPU的情况，比如，当线程数量达到50时，CPU达到100%，则将maxPoolSize设置为60也不合适，此时若系统负载长时间维持在每秒1000个任务，则超出线程池处理能力，应设法降低每个任务的处理时间(tasktime)。
		 */
		ThreadPoolExecutor executor 
			= new ThreadPoolExecutor(10, 500, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1000));
		
		
		/* 对于上面这种创建方式，我更推荐使用类似于Executors.newCachedThreadPool()的创建方式，
		 * 不设置corePoolSize,对maximumPoolSize 进行设置，避免像Executors.newCachedThreadPool()在任务
		 * 过多的时候创建过多线程 ；设置corePoolSize在JDK7以下的版本是有问题的，当设置的队列满了，然后进行
		 * 创建小于maximumPoolSize的线程，但当任务下去时，所有的线程都会死掉，这是jdk的bug，JDK7以上的版本已经修复*/
		
		/**CPU 物理2核逻辑4核,LinkedBlockingQueue 只要一天的请求能消耗完，长度是可以不用限制*/
		ThreadPoolExecutor EXECUTOR 
			= new ThreadPoolExecutor(0, 50, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		
		
		return executor;
	}

}
