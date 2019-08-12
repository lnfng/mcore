package mcore.about_reflect;

import javax.management.MXBean;

import org.junit.Test;

public class ReflectClassLoad {

	/**
	 * 反射主要是用在框架中的应用,反射就是加载类和解剖类的
	 * 综述：反射就是将类的各个成份反射成对应的Java类
	 */
	public static void main(String[] args) {
		
		try {//方式
			
			//1.获得该类的字节码,加载类(常用的是这种)
			Class<Person> clazz1 = (Class<Person>) Class.forName("mcore.about_reflect.Person");
			Person instance = clazz1.newInstance();
			System.out.println(instance);
			
			
			
			//2.
			Class<? extends Person> clazz2 = new Person().getClass();			
			//3.
			Class<Person> clazz3 = Person.class;			
			//获得的字节码肯定是一样的
			System.out.println(clazz1.equals(clazz3));
			
			//获得构造函数
			clazz1.getConstructor();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	public void reflectInterface() throws Exception{
		
		/*Class<ReflectInterface> icls = (Class<ReflectInterface>) Class.forName("about_reflect.ReflectInterface");
		System.out.println(icls.getName());
		MXBean interface_ann = icls.getAnnotation(MXBean.class);
		System.out.println(">>>通过接口获取注解>>>: "+interface_ann);
		
		Class<RFimpl> rfcls = (Class<RFimpl>) Class.forName("about_reflect.RFimpl");
		System.out.println(rfcls.getName());
		
		MXBean annotation = rfcls.getAnnotation(MXBean.class);
		System.out.println(">>>通过实现类获取注解>>>: "+annotation);*/
		
	}
	
}
