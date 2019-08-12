package mcore.about_reflect;

import java.lang.reflect.Constructor;

import org.junit.Test;

public class ReflectConstructor {

	@Test
	public void test1() {
		try {
			
			//1.获得该类的字节码,加载类(常用的是这种)
			Class<?> clazz1 = Class.forName("about_reflect.Person");			
			
			//反射无参的构造函数public Person()
			Constructor<?> constr = clazz1.getConstructor();
			Person person = (Person) constr.newInstance();
			String t = person.test;			
			System.out.println(t);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2() {
		try {
			
			//1.获得该类的字节码,加载类(常用的是这种)
			Class<?> clazz1 = Class.forName("about_reflect.Person");			
			
			//反射构造函数public Person(String name)
			Constructor<?> constr = clazz1.getConstructor(String.class);
			Person person = (Person) constr.newInstance("You are my sunshine");
			String t = person.getName();			
			System.out.println(t);
			
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void test3() {
		try {
			
			//1.获得该类的字节码,加载类(常用的是这种)
			Class<?> clazz1 = Class.forName("about_reflect.Person");			
			
			//反射构造函数private Person(String, int)
			Constructor<?> constr = clazz1.getDeclaredConstructor(String.class,int.class);
			
			//当涉及到的构造函数是private修饰时,需要做如下设置
			constr.setAccessible(true);//暴力反射
			
			Person person = (Person) constr.newInstance("private constructor",12);
			String t = person.getName();			
			System.out.println(t+"  : "+person.getAge());
			
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	//反射主要是用在框架中的应用,反射就是加载类和解剖类的
	//相当于记忆截取(jakc)
	@Test
	public void test4() {
		try {
			
			//1.获得该类的字节码,加载类(常用的是这种)
			Class<?> clazz1 = Class.forName("about_reflect.Person");			
			
			//创建对象的另一种方式
			Person person = (Person) clazz1.newInstance();
			//这方法有个限制就是该类需有个无的构造方法
			//其内部的工作原理是反射出无参的构造方法,再返回给调用者
			System.out.println(person.test);
			
			//该方法等效于test1()
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	
}
