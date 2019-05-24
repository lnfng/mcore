package mcore.about_thread.lock;
/*
作业1：有一个水池，水池的容量是固定 的500L,一边为进水口,一边为放水口.要求,进水与放水不能同时进行.
水池一旦满了不能继续注水,一旦放空了,不可以继续放水. 进水的 速度5L/s ,  放水的速度2L/s 

这里使用了，传统线程的锁和通信机制
ConditionDemo类中使用的是并发库里的新技术
*/


public class WaterPool2
{
	public static void main(String[] args) 
	{
		ResourceWater2 res = new ResourceWater2();

		new Thread( new AddWater2(res)).start();
		new Thread( new AddWater2(res)).start();
		new Thread(new OutWater2(res)).start();
		new Thread(new OutWater2(res)).start();


	}
}


class ResourceWater2 
{
	public final static int V = 500;
	private volatile int has = 0;  //不能解决该问题
	
	public void add ()
	{	has = has+1-1;
		while((has + 5)>V) 
			try {Thread.sleep(100);	} catch (InterruptedException e) {}
		
		has+=5;
		System.out.println(Thread.currentThread().getName() + "......正在加水，已有 " + has + " L");
		
	}

	public void out ()
	{	has = has+1-1;
		while(has<2)
			try {Thread.sleep(100);	} catch (InterruptedException e) {}
		
		has-=2;
		System.out.println(Thread.currentThread().getName() + "....正在放水，还剩" +has + " L");
		
	}
}


class AddWater2 implements Runnable
{
	private ResourceWater2 res;

	AddWater2(ResourceWater2 res)
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


class OutWater2 implements Runnable
{
	private ResourceWater2 res;

	OutWater2(ResourceWater2 res)
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