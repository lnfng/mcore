package mcore.about_pattern;

/**
 * 单例模式（Singleton）
 * 	步骤:
 * 		1. 构造方法私有化
 * 		2. 编写获取实例对象的方法
 * 		3. 初始化实例
 *
 *	单例对象是一种常用的设计模式。在Java应用中，单例对象能保证在一个JVM中，
 *	该对象只有一个实例存在。这样的模式有几个好处：
 *		1、某些类创建比较频繁，对于一些大型的对象，这是一笔很大的系统开销。
 *		2、省去了new操作符，降低了系统内存的使用频率，减轻GC压力。
 *		3、有些类如交易所的核心交易引擎，控制着交易流程，如果该类可以创建多个的话，系统完全乱了。
 *			（比如一个军队出现了多个司令员同时指挥，肯定会乱成一团），所以只有使用单例模式，
 *			才能保证核心交易服务器独立控制整个流程。
 * @author Qian
 */
public class Singleton {
	
	/* 私有构造方法，防止被实例化 */  
    private Singleton() {
    	System.out.println("be created!!!");
    }  
    
	
	/* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */  
    private static Singleton instance = null;  
	
	
    /**此处加锁保证了并发不会多次初始化,
     * 但每次都要判断锁,影响性能*/
    public static synchronized Singleton getInstance1() {  
        if (instance == null) {  
            instance = new Singleton();  
        }  
        return instance;  
    }  
    
    /**
     * 加锁双重判断:
     * 缺点:
     * 1. 偏复杂
     * 2. 在Java指令中创建对象和赋值操作是分开进行的，也就是说instance = new Singleton();语句是分两步执行的。
     * 但是JVM并不保证这两个操作的先后顺序，也就是说有可能JVM会为新的Singleton实例分配空间，
     * 然后直接赋值给instance成员，然后再去初始化这个Singleton实例。这样就可能出错了，我们以A、B两个线程为例：
     *		a>A、B线程同时进入了第一个if判断
     *		b>A首先进入synchronized块，由于instance为null，所以它执行instance = new Singleton();
     *		c>由于JVM内部的优化机制，JVM先画出了一些分配给Singleton实例的空白内存，并赋值给instance成员（注意此时JVM没有开始初始化这个实例），
     *		然后A离开了synchronized块。
     *		d>B进入synchronized块，由于instance此时不是null，因此它马上离开了synchronized块并将结果返回给调用该方法的程序。
     *		e>此时B线程打算使用Singleton实例，却发现它没有被初始化，于是错误发生了。*/
    public static Singleton getInstance2() {  
        if (instance == null) {  
            synchronized (Singleton.class) {  
                if (instance == null) {  
                    instance = new Singleton();  
                }  
            }  
        }  
        return instance;  
    }  
    
    
    private static synchronized void syncInit() {  
        if (instance == null) {  
            instance = new Singleton();  
        }  
    }  
  
    /**跟加锁双重判断是一个意思*/
    public static Singleton getInstance3() {  
        if (instance == null) {  
            syncInit();  
        }  
        return instance;  
    }  
    
    
    private static class SingletonFactory{           
        static Singleton instance = new Singleton();           
    } 
    /**
     *实际情况是，单例模式使用内部类来维护单例的实现，JVM内部的机制能够保证当一个类被加载的时候，这个类的加载过程是线程互斥的。
     *这样当我们第一次调用getInstance的时候，JVM能够帮我们保证instance只被创建一次，
     *并且会保证把赋值给instance的内存初始化完毕，这样我们就不用担心上面的问题。
     *同时该方法也只会在第一次调用的时候使用互斥机制，这样就解决了低性能问题。
     *这样我们暂时总结一个完美的单例模式：
     * */
    public static Singleton getInstance4(){           
        return SingletonFactory.instance;           
    }   
    
    
    public static void main(String[] args) {
    	Singleton.getInstance1();
    	Singleton.getInstance2();
    	Singleton.getInstance4();
	}
    
    

}
