package mcore.about_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;

public class ProxyTest2 {
	
	public static void main(String[] args) throws Exception {
		
		/**通过代理类的静态方法获得代理对象*/
		Collection proxy2=(Collection) Proxy.newProxyInstance(
				Collection.class.getClassLoader(),
				new Class[]{Collection.class},
				new InvocationHandler(){
					//为代理类提供实际操作
					Collection target=new ArrayList();
					
					/**一个InvocationHandler可以给多个代理类调用
					 * invoke方法的参数说明：
					 * proxy：当前的调用的代理类
					 * method:调用代理类的哪个方法
					 * args：所传递的参数*/
					public Object invoke(Object proxy, Method method,Object[] args) 
							throws Throwable {
						long begintime=System./*currentTimeMillis()*/nanoTime();//纳秒
						Object retVal=method.invoke(target, args);
						long endtime=System./*currentTimeMillis()*/nanoTime();
						System.out.println("代理类调用 "+method.getName()+" 耗时"+(endtime-begintime));
						return retVal;
					}
					
				});
		
		proxy2.add("Qian");
		proxy2.add("Zhao");
		proxy2.add("Liu");
		System.out.println(proxy2.size());
		
		Proxy.newProxyInstance(
				Collection.class.getClassLoader(),
				null, /**it must need the interface*/
				new InvocationHandler(){

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return null;
				
			}
			
		});
	}

}
