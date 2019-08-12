package mcore.about_proxy.cglib_proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 我觉得还有更好的写法
 * @author JackQ
 *
 */
public class CglibProxy  implements MethodInterceptor {

	//Enhancer  加强者; 提高者;
	private Enhancer enhancer = new Enhancer();
	
	public Object getProxy(Class<?> clazz){  
	  //设置需要创建子类的类  
	  enhancer.setSuperclass(clazz); 
	  
	  enhancer.setCallback(this);  
	  //通过字节码技术动态创建子类实例  
	  return enhancer.create();  
	} 
	
	
	
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		System.out.println("before the method do...");
		
		//通过代理类调用父类中的方法 
		Object retValue=proxy.invokeSuper(obj, args);
		
		System.out.println("after the method do...");
		return retValue;
	}

}
