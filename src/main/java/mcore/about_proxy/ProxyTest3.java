package mcore.about_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;

public class ProxyTest3 {
	
	public static void main(String[] args) throws Exception {
		/**把目标和是系统处理抽取出来*/
		final Collection target=new ArrayList();
		final Advice advice=new Advice(){
			long begintime=0;
			public void beforeMethod(Method method) {
				begintime=System./*currentTimeMillis()*/nanoTime();//纳秒
			}
			public void afterMethod(Method method) {
				long endtime=System./*currentTimeMillis()*/nanoTime();
				System.out.println("代理类调用 "+method.getName()+" 耗时"+(endtime-begintime));
			}
			
		};
		
		Collection proxy2 = (Collection) getProxy(target,advice);
		
		proxy2.add("Qian");
		proxy2.add("Zhao");
		proxy2.add("Liu");
		System.out.println(proxy2.size());
	}

	//还可以把handler抽取出去
	private static Object getProxy(final Object target,final Advice advice) {
		/**通过代理类的静态方法获得代理对象*/
		Object proxy=Proxy.newProxyInstance(
				target.getClass().getClassLoader(),
				target.getClass().getInterfaces(),
				new InvocationHandler(){
					/**一个InvocationHandler可以给多个代理类调用
					 * invoke方法的参数说明：
					 * proxy：当前的调用的代理类
					 * method:调用代理类的哪个方法
					 * args：所传递的参数*/
					public Object invoke(Object proxy, Method method,Object[] args) 
							throws Throwable {
						advice.beforeMethod(method);
						
						Object retVal=method.invoke(target, args);
						advice.afterMethod(method);
						return retVal;
					}
					
				});
		return proxy;
	}

}
