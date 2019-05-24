package mcore.about_classloader;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassLoaderTest2 {
	
	
	public static void main(String[] agrs) throws Exception{
		test2();
	}
	
	public static void test1(){
		InputStream stream = ClassLoaderTest2.class.getResourceAsStream("../datasources.properties");
		InputStream stream2 = ClassLoaderTest2.class.getClassLoader().getResourceAsStream("datasources.properties");
		/*
		 * 以上两者均是通过class loader 去获取资源的.
		 * 不同的是:直接通过class.getResourceAsStream,它的相对路径是当前类所在的路径开始.
		 * 而classLoader.getResourceAsStream,它的相对路径是类路径.
		 */
		
		System.out.println(stream);
		System.out.println(stream2);
		
	}

	public static void test2() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, InstantiationException {
		//delegation model 父类委派机制
		MyClassLoader2 cl = new MyClassLoader2("lib");
		Class<?> cls = cl.loadClass("about_class_loader.Person");
		Method method = cls.getMethod("from");
		method.invoke(cls.newInstance());
		
		System.out.println();
		//getResourceAsStream 一样遵循父类委派机制
		InputStream stream = cl.getResourceAsStream("datasources.properties");
		InputStream stream2 = cls.getClassLoader().getResourceAsStream("datasources.properties");
		InputStream stream3 = cl.getResourceAsStream("WebappLoader.class");
		System.out.println("stream="+stream);
		System.out.println("stream2="+stream2);
		System.out.println("stream3="+stream3);
		
		//load java.lang.String
		Class<?> cls2 = cl.loadClass("java.lang.String");
		Method method2 = cls2.getMethod("hashCode");
		Object invoke = method2.invoke(cls2.getConstructor(String.class).newInstance("123"));
		System.out.println(invoke);
	}
	
	
	

}
