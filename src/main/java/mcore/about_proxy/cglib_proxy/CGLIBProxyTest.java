package mcore.about_proxy.cglib_proxy;


/**
 * JDK的动态代理机制只能代理实现了接口的类，而不能实现接口的类就不能实现JDK的动态代理，
 * cglib是针对类来实现代理的，他的原理是对指定的目标类生成一个子类，并覆盖其中方法实现增强，
 * 但因为采用的是继承，所以不能对final修饰的类进行代理。
 * 
 * asm.jar ANother Tool for Language Recognition 必要 ASM字节码库 如果使用“cglib” 
 * 则必要 asm-attrs.jar ehcache.jar cglib.jar ASM字节码库 如果使用“cglib”
 *  则必要 EHCache缓存 如果没有其他的缓存，则是必要的 如果使用“cglib” 
 *  则必要 对bean操作的类，可以访问 CGLIB字节码解释器 commons-beanutils.jar 
 *  提供对java反射和自省API的包装
 *	百度上找的
 * 
 */
public class CGLIBProxyTest {

	public static void main(String[] args) {
		CglibProxy proxy = new CglibProxy();  
		  //通过生成子类的方式创建代理类  
		BeProxy proxyImp = (BeProxy)proxy.getProxy(BeProxy.class);  
		proxyImp.proxyMethod();
	}
	
}



