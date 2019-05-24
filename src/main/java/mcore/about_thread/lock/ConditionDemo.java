package mcore.about_thread.lock;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1.在等待 Condition 时，允许发生“虚假唤醒”，这通常作为对基础平台语义的让步。
 * 对于大多数应用程序，这带来的实际影响很小，因为 Condition 应该总是在一个循环中被等待，
 * 并测试正被等待的状态声明。某个实现可以随意移除可能的虚假唤醒，
 * 但建议应用程序程序员总是假定这些虚假唤醒可能发生，因此总是在一个循环中等待。
 * 
 * 2.一个锁内部可以有多个Condition，即有多路等待和通知，
 * 可以参看jdk1.5提供的Lock与Condition实现的可阻塞队列的应用案例，
 * 从中除了要体味算法，还要体味面向对象的封装。
 * 在传统的线程机制中一个监视器对象上只能有一路等待和通知，
 * 要想实现多路等待和通知，必须嵌套使用多个同步监视器对象。
 * （如果只用一个Condition，两个放的都在等，一旦一个放的进去了，
 * 那么它通知可能会导致另一个放接着往下走。）
 * 
 * 或者在执行通知时不需要保持一个锁。
 * 
 * 
 * @author JackQ
 *
 */
public class ConditionDemo {
	/*
	作业1：有一个水池，水池的容量是固定 的500L,
	一边为进水口,一边为放水口.要求,进水与放水不能同时进行.
	水池一旦满了不能继续注水,一旦放空了,不可以继续放水. 进水的 速度5L/s ,  放水的速度2L/s 
	*/

	public static void main(String[] args) 
	{
		Resource res = new Resource();

		new Thread( new AddWater(res)).start();
		new Thread( new AddWater(res)).start();
		new Thread(new OutWater(res)).start();
		new Thread(new OutWater(res)).start();

		
		
	}
}


class Resource {
	private final static int V = 500;
	private int has = 0;
	
	private Lock lock=new ReentrantLock();
	private Condition notFull=lock.newCondition();
	private Condition notEmpty=lock.newCondition();
	/*you can define more Condition object */
	
	public /*synchronized*/ void add ()	{
		/*之前用 if 去判断是否有数据，但 if 只适用于两个线程的操作
		若两个以上就不合适了，因为有多个线程在等待的时候不能确
		保唤醒另一方
		用while 更健壮，防止虚假唤醒
		*/
		Set<String> set = new HashSet<String>();
		set.add("123");
		
		lock.lock();
		
		try {
			while((has + 5)>V) {
				/**
				 * 使用传统的 wait 和 notify... 组合，当几个add线程都在此等待
				 * 这时候有一个被唤醒，执行完add操作后，进行对等待的线程进行
				 * 唤醒，但会出现池子满了，而唤醒的又是add的线程，这样就会浪费资源
				 * 
				 * 使用：两个condition 可以解决此问题
				 * Condition 将 Object 监视器方法
				 * （wait、notify 和 notifyAll）分解成截然不同的对象
				 */
				try{
					//this.wait();
					notFull.await();
				}catch(Exception e){}
			}
			
			try {Thread.sleep(100);	} catch (InterruptedException e) {}
			has+=5;
			System.out.println(Thread.currentThread().getName() + "......正在加水，已有 " + has + " L");
			
			//notEmpty.signalAll();
			/**signal() 更合适*/
			notEmpty.signal();
		} finally{
			lock.unlock();
		}		
		
	}

	public /*synchronized*/  void out (){
		lock.lock();
		try {
			while(has<2)
				try{
					//this.wait();
					notEmpty.await();
				}catch(Exception e){}
			
			try {Thread.sleep(100);	} catch (InterruptedException e) {}
			
			has-=2;
			System.out.println(Thread.currentThread().getName() + "....正在放水，还剩" +has + " L");
			
//			notFull.signalAll();
			/**signal() 更合适*/
			notFull.signal();
		} finally {
			lock.unlock();
		}
	}
}

class AddWater implements Runnable{
	private Resource res;

	AddWater(Resource res)	{
		this.res = res;
	}
	public void run(){
		while(true){
			res.add();
		}
	}

}


class OutWater implements Runnable{
	private Resource res;

	OutWater(Resource res)	{
		this.res = res;
	}
	public void run()	{
		while(true){
			res.out();
		}
	}
}

/**
	作为一个示例，假定有一个绑定的缓冲区，它支持 put 和 take 方法。
	如果试图在空的缓冲区上执行 take 操作，则在某一个项变得可用之前，
	线程将一直阻塞；如果试图在满的缓冲区上执行 put 操作，则在有空间变得可用之前，
	线程将一直阻塞。我们喜欢在单独的等待 set 中保存 put 线程和 take 线程，
	这样就可以在缓冲区中的项或空间变得可用时利用最佳规划，
	------------------------------------
	一次只通知一个线程。
	-------------------------------------
	可以使用两个 Condition 实例来做到这一点。 
	
	class BoundedBuffer {
		final Lock lock = new ReentrantLock();
		final Condition notFull  = lock.newCondition(); 
		final Condition notEmpty = lock.newCondition(); 
		
		final Object[] items = new Object[100];
		int putptr, takeptr, count;
		
		public void put(Object x) throws InterruptedException {
		 lock.lock();
		 try {
		   while (count == items.length) 
		     notFull.await();
		   items[putptr] = x; 
		   if (++putptr == items.length) putptr = 0;
		   ++count;
		   notEmpty.signal();
		 } finally {
		   lock.unlock();
		 }
		}
		
		public Object take() throws InterruptedException {
		 lock.lock();
		 try {
		   while (count == 0) 
		     notEmpty.await();
		   Object x = items[takeptr]; 
		   if (++takeptr == items.length) takeptr = 0;
		   --count;
		   notFull.signal();
		   return x;
		 } finally {
		   lock.unlock();
		 }
		} 
	}
	
	（ArrayBlockingQueue 类提供了这项功能，因此没有理由去实现这个示例类。） 
	Condition 实现可以提供不同于 Object 监视器方法的行为和语义，
	比如受保证的通知排序，
	-------------------------------------
	或者在执行通知时不需要保持一个锁。
	---------------------------------------
	如果某个实现提供了这样特殊的语义，则该实现必须记录这些语义。 
	
	注意，Condition 实例只是一些普通的对象，它们自身可以用作 synchronized 语句中的目标，
	并且可以调用自己的 wait 和 notification 监视器方法。
	获取 Condition 实例的监视器锁或者使用其监视器方法，
	与获取和该 Condition 相关的 Lock 或使用其 waiting 和 signalling 方法
	没有什么特定的关系。为了避免混淆，建议除了在其自身的实现中之外，
	切勿以这种方式使用 Condition 实例。 
	
	除非另行说明，否则为任何参数传递 null 值将导致抛出 NullPointerException。 
	
	实现注意事项
	在等待 Condition 时，允许发生“虚假唤醒”，这通常作为对基础平台语义的让步。
	对于大多数应用程序，这带来的实际影响很小，因为 Condition 应该总是在一个循环中被等待，
	并测试正被等待的状态声明。某个实现可以随意移除可能的虚假唤醒，
	但建议应用程序程序员总是假定这些虚假唤醒可能发生，因此总是在一个循环中等待。 
	
	三种形式的条件等待（可中断、不可中断和超时）在一些平台上的实现以及它们的
	性能特征可能会有所不同。尤其是它可能很难提供这些特性和维护特定语义，
	比如排序保证。更进一步地说，中断线程实际挂起的能力在所有平台上并不是总是可行的。 
	
	因此，并不要求某个实现为所有三种形式的等待定义完全相同的保证或语义，
	也不要求其支持中断线程的实际挂起。 
	
	要求实现清楚地记录每个等待方法提供的语义和保证，在某个实现不支持中断线程的挂起时，
	它必须遵从此接口中定义的中断语义。 
	
	由于中断通常意味着取消，而又通常很少进行中断检查，因此实现可以先于普通方法的返回来
	对中断进行响应。即使出现在另一个操作后的中断可能会释放线程锁时也是如此。
	实现应记录此行为。 
*/