package mcore.about_thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

	public static void main(String[] args) {
		final Outputer Outputer=new Outputer();
		new Thread(new Runnable() {
			public void run() {
				for(;;)
				Outputer.print("zhaozilong");
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				for(;;)
				Outputer.print("qianxixiao");
			}
		}).start();
	}

}

/**
 * 没加锁的话，会打断输出
 * 这个例子并没有体现数据共享的安全问题
 * @author JackQ
 *
 */
class Outputer{
	Lock lock=new ReentrantLock();
	
	public void print(String name){
		int len=name.length();
		lock.lock();
		try {
			for(int i=0;i<len;i++){
				System.out.print(name.charAt(i));
			}
			System.out.println();
		} finally {
			//用try-finally解锁更健壮
			lock.unlock();
		}
	}
}
