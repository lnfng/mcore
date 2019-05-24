package mcore.about_thread.lock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：分为读锁和写锁，
 * 多个读锁不互斥
 * 读锁和写锁互斥
 * 写锁与写锁互斥
 * 但这不能简单的理解成文件的读写，而是对所有的数据的更改和读取都是适用的
 * @author JackQ
 *
 */
public class ReadWriteLockDemo {

	public static void main(String[] args) {
		final Queue3 queue3=new Queue3();
		for (int i = 0; i <3; i++) {
			/**3个线程读，3个线程写*/
			new Thread(new Runnable() {
				
				public void run() {
					for(;;)
					queue3.get();
				}
			}).start();
			
			new Thread(new Runnable() {
				
				public void run() {
					for(;;)
					queue3.setData(new Random().nextInt(10000));
				}
			}).start();
		}

	}

}

class Queue3{
	/**共享数据，只有一个线程能进行写*/
	private Object data=null;
	private ReadWriteLock rwl=new ReentrantReadWriteLock();
	private Lock read=rwl.readLock();
	private Lock write=rwl.writeLock();
	
	public void get(){
		String threadName=Thread.currentThread().getName();
		read.lock();
		try {
			System.out.println(threadName+" be ready to read data!");
			Thread.sleep((long)(Math.random()*1000));
			System.out.println(threadName+" have read date :"+data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			read.unlock();
		}
	}
	
	public void setData(Object data){
		String threadName=Thread.currentThread().getName();
		write.lock();
		try {
			System.out.println(threadName+" be read to write data...");
			Thread.sleep((long)(Math.random()*1000));
			this.data=data;
			System.out.println(threadName+" have write data :"+data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			write.unlock();
		}
		
	}
	/*帮助文档中的示例
	示例用法。下面的代码展示了如何利用重入来执行升级缓存后的锁降级（为简单起见，省略了异常处理）： 

	 class CachedData {
	   Object data;
	   volatile boolean cacheValid;
	   ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	
	   void processCachedData() {
	     rwl.readLock().lock();
	     if (!cacheValid) {
	        // Must release read lock before acquiring write lock
	        rwl.readLock().unlock();
	        rwl.writeLock().lock();
	        // Recheck state because another about_thread might have acquired
	        //   write lock and changed state before we did.
	        if (!cacheValid) {
	          data = ...
	          cacheValid = true;
	        }
	        // Downgrade by acquiring read lock before releasing write lock
	        rwl.readLock().lock();
	        rwl.writeLock().unlock(); // Unlock write, still hold read
	     }
	
	     use(data);
	     rwl.readLock().unlock();
	   }
	 }
	 
	在使用某些种类的 Collection 时，可以使用 ReentrantReadWriteLock 来提高并发性。
	通常，在预期 collection 很大，读取者线程访问它的次数多于写入者线程，
	并且 entail 操作的开销高于同步开销时，这很值得一试。
	例如，以下是一个使用 TreeMap 的类，预期它很大，并且能被同时访问。 
	class RWDictionary {
	    private final Map<String, Data> m = new TreeMap<String, Data>();
	    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	    private final Lock r = rwl.readLock();
	    private final Lock w = rwl.writeLock();
	
	    public Data get(String key) {
	        r.lock();
	        try { return m.get(key); }
	        finally { r.unlock(); }
	    }
	    public String[] allKeys() {
	        r.lock();
	        try { return m.keySet().toArray(); }
	        finally { r.unlock(); }
	    }
	    public Data put(String key, Data value) {
	        w.lock();
	        try { return m.put(key, value); }
	        finally { w.unlock(); }
	    }
	    public void clear() {
	        w.lock();
	        try { m.clear(); }
	        finally { w.unlock(); }
	    }
	 }
	 实现注意事项：
	此锁最多支持 65535 个递归写入锁和 65535 个读取锁。
	试图超出这些限制将导致锁方法抛出 Error。

	*/
}
