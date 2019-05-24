package mcore.about_thread.executors;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池的一些设定不太符合使用
 * coreSize 必须等到队列满了之后才使用maxsize去创建线程
 * @author Qian
 */
public class PoolTest {
	
	public static void main(String[] args) {
		
		// 线程池讲解参考：https://www.oschina.net/question/565065_86540
		
		threadPoolTest01();
//		threadPoolTest02();
//		threadPoolTest03();
//		threadPoolTest04();
//		threadPoolTest05();
		
		
		
	}
	
	
	static void threadPoolTest01(){
		
		/* 只用8条线程,没有初始线程即coreSize为0
		 * 相当于:  Executors.newCachedThreadPool(),该线程数限制为 Integer.Max_value, 太过大 
		 * 自己手动创建有线程数(核数*cpu数 或再乘以2)限制,更为合理*/
		ThreadPoolExecutor executor ;
		executor = new ThreadPoolExecutor(0, 10, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),new ThreadPoolExecutor.CallerRunsPolicy());
		executor = new ThreadPoolExecutor(0, 16, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		//executor = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(6));
		
		
		
		for(int t=16;t>0;t--){
			executor.execute(new Runnable() {
				public void run() {
					try {Thread.sleep(1000);} catch (Exception e) {}
					System.out.println(">> running about_thread name:"+Thread.currentThread().getName());
				}
			});
		}
		
	}
	
	static void threadPoolTest02(){
		
		
		/* 本来想通过代理的模式去改变corePoolSize的，
		 * 但是增长可以很好的控制，降下就不知道怎么控制了*/
		ExecutorService executor = (ExecutorService) Proxy.newProxyInstance(
			ExecutorService.class.getClassLoader(), 
			new Class[]{ExecutorService.class}, 
			new InvocationHandler(){
				ThreadPoolExecutor executor 
					= new ThreadPoolExecutor(2, 8, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
				
				/* 根据队列长度去动态的设置线程数*/
				public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
					if("submit".equals(method.getName())
						|| "execute".equals(method.getName())){
						executor.getCorePoolSize();
						executor.getMaximumPoolSize();
						//return method.invoke(executor, args);
					}
					else if("".equals(method.getName())){
						
						
					}
					System.out.println(">> method name : "+method.getName());
					
					return method.invoke(executor, args);
				}
			});
		
		executor.execute(new Runnable() {
			public void run() {
				System.out.println(">> running !!!");
			}
		});
		
	}
	
	static void threadPoolTest03(){
		
		final int queueCapacity = 1;
		
		/* 基本实现期望的目的，就是当超过指定队列的时候，增加线程，
		 * 但线程数达到最大,队列也满了，还能继续添加 */
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 3, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(queueCapacity), 
			new RejectedExecutionHandler() {
				private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(); //作为缓存队列
				public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
					queue.offer(r);
					BlockingQueue<Runnable> actQueue = executor.getQueue(); // 当前执行的队列
					// 这个处理有待改进, 这里已经堵塞了
					if(queue.size()==1){
						while(true){
							if(queue.size()==0){break;}
							if(actQueue.size()<queueCapacity){
								actQueue.offer(queue.poll());
							}
						}
					}
				}
				public BlockingQueue<Runnable> getQueue(){return this.queue;}
		});
		
		/* 测试数据： 
		 * corePoolSize = 1
		 * maximumPoolSize = 5
		 * TimeUnit.SECONDS 10
		 * queueCapacity = 1
		 * 以上面的数据创建线程池，然后看测试结果：
		 * 首先运行6个任务并使其占满线程池及队列，然后等6个任务皆运行完毕，
		 * 再提交两个任务，结果请看： POOL_RESULT
		 * corePoolSize这个线程在所有线程任务都结束后存活一段时间，但不会直存活，这可能是个bug
		 * 
		 * 导致corePoolSize线程也停止的是Runnable getTask() 与 void workerDone(Worker w)方法 对的
		 * 操作并没有互斥。poolSize 在getTask 是自由读取的，而在workerDone 是加锁操作的。
		 * JDK 7 之前的版本会有这个问题，JDK 7 之后已经解决了这个问题
		 * 
		 * 超过时间就会消除，再有任务就会新建线程 */
		//executor = new ThreadPoolExecutor(1, 5, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(queueCapacity));
		
		for(int t=6;t>0;t--){
			System.out.println(">> core size = "+executor.getCorePoolSize());
			System.out.println(">> pool size = "+executor.getPoolSize());
			executor.submit(new Runnable() {
				public void run() {
					try {Thread.sleep(5*1000);} catch (Exception e) {}
					System.out.println(">> about_thread name:"+Thread.currentThread().getName());
				}
			});
		}
		
		try {Thread.sleep(20*1000);} catch (Exception e) {}
		for(int t=2;t>0;t--){
			System.out.println(">> core size = "+executor.getCorePoolSize());
			System.out.println(">> pool size = "+executor.getPoolSize());
			executor.submit(new Runnable() {
				public void run() {
					System.out.println(">> about_thread name:"+Thread.currentThread().getName());
				}
			});
		}
		
		
	}
	
	
	static void threadPoolTest04(){
		
		final int queueCapacity = 3;
		final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(); //作为缓存队列
		
		/*采用继承的方法实现当线程数达到最大,队列也满了，还能继续添加*/
		RejectedExecutionHandler rejectedHandler = new RejectedExecutionHandler() {
			public void rejectedExecution(Runnable r,ThreadPoolExecutor executor) {
				if(executor.isShutdown()){ // 非运行状态不添加
					throw new RejectedExecutionException();
				}
				queue.offer(r);
			}
		};
		
		ThreadPoolExecutor executor 
		= new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(queueCapacity),rejectedHandler){
			@Override
			protected void afterExecute(Runnable r, Throwable t) {
				if(this.getQueue().size() < queueCapacity && queue.size() > 0){
					this.getQueue().offer(queue.poll());
				}
			}
		};
		
		for(int t=1; t<=5; t++){
			final Integer ID = t;
			executor.submit(new Runnable() {
				public void run() {
					System.out.println(">> task id "+ID+" about_thread name:"+Thread.currentThread().getName());
				}
			});
		}
		
		/*try {Thread.sleep(60*1000);} catch (Exception e) {}*/
		// 如果不需要获取线程执行结果，还是推荐使用execute方法执行任务，这样会少创建一些不必要的对象
		for(int t=6; t<=10; t++){
			final Integer ID = t;
			executor.execute(new Runnable() {
				public void run() {
					System.out.println(">> task id "+ID+" about_thread name:"+Thread.currentThread().getName());
				}
			});
		}
	}
	
	/**
	 * 当队列增加多少个，增加一个线程，直到达到线程最大值
	 */
	static void threadPoolTest05(){
		ThreadPoolExecutor executor 
		= new ThreadPoolExecutor(1, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>()){
			private int divisor = 10; // divisor > 0
			private int initCorePoolSize =this.getCorePoolSize();
			private volatile boolean reset = false;
			
			@Override
			protected void beforeExecute(Thread t, Runnable r) {
				//System.out.println(">> beforeExecute about_thread name:"+t.getName());
				int qsize = getQueue().size();
				if(initCorePoolSize>0&&getCorePoolSize()<getMaximumPoolSize()
					&& qsize > 0 && (qsize%divisor) == 0){
					setCorePoolSize(getCorePoolSize()+1); // 进行动态增加
				}
			}
			
			@Override
			protected void afterExecute(Runnable r, Throwable t) {
				if(!reset && getQueue().size()<=divisor && getCorePoolSize()>initCorePoolSize ){
					reset = true;
					setCorePoolSize(initCorePoolSize); //重置为最初始值
				}
			}
		};
		
		for(int t=0; t<50; t++){
			final Integer ID = t;
			executor.execute(new Runnable() {
				public void run() {
					//try {Thread.sleep(100);} catch (Exception e) {}
					System.out.println(">> task id "+ID+" about_thread name:"+Thread.currentThread().getName());
				}
			});
		}
		
		try {Thread.sleep(30*1000);} catch (Exception e) {}
		System.out.println(">> executor info : "+executor.toString());
	}
	
	
	/* jdk 6
	 * POOL_RESULT:
	 	>> core size = 1
		>> pool size = 0
		>> core size = 1
		>> pool size = 1
		....
		>> core size = 1
		>> pool size = 4
		>> about_thread name:pool-2-about_thread-1
		>> about_thread name:pool-2-about_thread-2
		>> about_thread name:pool-2-about_thread-3
		>> about_thread name:pool-2-about_thread-4
		>> about_thread name:pool-2-about_thread-5
		>> about_thread name:pool-2-about_thread-1
		>> core size = 1
		>> pool size = 0
		>> core size = 1
		>> pool size = 1
		>> about_thread name:pool-2-about_thread-6
		>> about_thread name:pool-2-about_thread-6
 
 
 		调整 TimeUnit.SECONDS 100
 		...
 		>> core size = 1
		>> pool size = 4
		>> about_thread name:pool-2-about_thread-1
		>> about_thread name:pool-2-about_thread-4
		>> about_thread name:pool-2-about_thread-2
		>> about_thread name:pool-2-about_thread-3
		>> about_thread name:pool-2-about_thread-5
		>> about_thread name:pool-2-about_thread-1
		>> core size = 1
		>> pool size = 5
		>> core size = 1
		>> pool size = 5
		>> about_thread name:pool-2-about_thread-2
		>> about_thread name:pool-2-about_thread-3
	 */
	

}
