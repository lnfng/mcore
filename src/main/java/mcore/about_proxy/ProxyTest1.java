package mcore.about_proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;

public class ProxyTest1 {
	
	public static void main(String[] args) throws Exception {
		//获得该接口的代理类,类加载器一般是与加载该接口的类加载器一样
		Class clazzProxy1=Proxy.getProxyClass(Collection.class.getClassLoader(),Collection.class);
		System.out.println(clazzProxy1.getName());
		
		System.out.println();
		System.out.println("------begin constructrs list-----");
		Constructor[] constructors=clazzProxy1.getConstructors();
		for(Constructor c:constructors){
			String name=c.getName();
			StringBuilder builder=new StringBuilder(name);
			builder.append('(');
			Class[] clazzParam=c.getParameterTypes();
			for(int i=0;i<clazzParam.length;i++){
				if(i>0)
					builder.append(',');
				builder.append(clazzParam[i].getName());
			}
			builder.append(')');
			System.out.println(builder.toString());
		}
		
		System.out.println();
		System.out.println("------begin methods list-----");
		Method[] methods=clazzProxy1.getMethods();
		for(Method m:methods){
			String methodName=m.getName();
			Class returnType=m.getReturnType();
			StringBuilder builder=new StringBuilder(/*returnType.getSimpleName()+"  "+*/methodName);
			builder.append('(');
			Class[] clazzParam=m.getParameterTypes();
			for(int i=0;i<clazzParam.length;i++){
				if(i>0)
					builder.append(',');
				builder.append(clazzParam[i].getSimpleName());
			}
			builder.append(')');
			System.out.println(builder.toString());
		}
		
		System.out.println("------begin create instance -----");
		//clazzProxy1.newInstance();//这种方法不正确，因为其没有无参构造方法
		Constructor constructor=clazzProxy1.getConstructor(InvocationHandler.class);
		//创建InvocationHandler  空实现
		class MyInvoctionHandler1 implements InvocationHandler{

			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				return null;
			}
		}
		Collection proxy1=(Collection) constructor.newInstance(new MyInvoctionHandler1());
		//是其toString方法返回null
		System.out.println(proxy1);
		
	}

}
