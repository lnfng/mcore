package mcore.about_thread.lock;
/*
作业1：有一个水池，水池的容量是固定 的500L,一边为进水口,一边为放水口.要求,进水与放水不能同时进行.
水池一旦满了不能继续注水,一旦放空了,不可以继续放水. 进水的 速度5L/s ,  放水的速度2L/s 

这里使用了，传统线程的锁和通信机制
ConditionDemo类中使用的是并发库里的新技术
*/


public class WaterPool
{
	public static void main(String[] args) 
	{
		ResourceWater res = new ResourceWater();

		new Thread( new AddWater1(res)).start();
		new Thread( new AddWater1(res)).start();
		new Thread(new OutWater1(res)).start();
		new Thread(new OutWater1(res)).start();


	}
}


class ResourceWater 
{
	public final static int V = 500;
	private int has = 0;
	
	public synchronized void add ()
	{
		/*之前用 if 去判断是否有数据，但 if 只适用于两个线程的操作
		若两个以上就不合适了，因为有多个线程在等待的时候不能确
		保唤醒另一方
		*/
		while((has + 5)>V) 
			try{this.wait();}catch(Exception e){}
		
		try {Thread.sleep(100);	} catch (InterruptedException e) {}
		
		has+=5;
		System.out.println(Thread.currentThread().getName() + "......正在加水，已有 " + has + " L");
		//if(has>=V)
			this.notifyAll();		
		
		/*这里如果加上了 if 语句的话，它会每次都加满再放水，
		因为其它线程正在等待，而唤醒的条件又不满足，而去掉
		条件，会有多个线程同时竞争CPU。
		*/
	}

	public synchronized void out ()
	{
		while(has<2)
			try{this.wait();}catch (Exception e) {}
		
		try {Thread.sleep(100);	} catch (InterruptedException e) {}
		
		has-=2;
		System.out.println(Thread.currentThread().getName() + "....正在放水，还剩" +has + " L");
		
		//if(has<2)
			this.notifyAll();
	}
	
	public void test(){
		synchronized (new Object()) {
			
		}
	}
	
	public static synchronized void test1(){
		
		synchronized (WaterPool.class) {
			
			
		}
		
	}
}


class AddWater1 implements Runnable
{
	private ResourceWater res;

	AddWater1(ResourceWater res)
	{
		this.res = res;
	}
	public void run()
	{
		while(true)
		{
			res.add();
		}
	}

}


class OutWater1 implements Runnable
{
	private ResourceWater res;

	OutWater1(ResourceWater res)
	{
		this.res = res;
	}
	public void run()
	{
		while(true)
		{
			res.out();
		}
	}
}