package mcore.about_reflect;

import java.lang.reflect.Method;

import org.junit.Test;

public class ReflectMethod {

	//反射主要是用在框架中的应用,反射就是加载类和解剖类的
	@Test
	//public void test1()
	public void test1() {
		try {
			Person p = new Person();//该对象最好也是通过反射的来的
			
			Class clazz = Class.forName("about_reflect.Person");			
			Method method = clazz.getMethod("test1");
			//p 代表是调用该方法的对象,即该方法是属于哪个对象的
			//method.invoke(p, null);
			method.invoke((Person) clazz.newInstance());
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	@Test
	//public void test2(int age)
	public void test2() {
		try {
			Person p = new Person();//该对象最好也是通过反射的来的			
			Class clazz = Class.forName("about_reflect.Person");			
			Method method = clazz.getMethod("test2", int.class);
			
			//p 代表是调用该方法的对象,即该方法是属于哪个对象的
			Object obj=method.invoke(p, 12);
			//System.out.println(obj);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	//public void test3(int[] nums)
	public void test3() {
		try {
			Person p = new Person();//该对象最好也是通过反射的来的			
			Class clazz = Class.forName("about_reflect.Person");			
			Method method = clazz.getMethod("test3", int[].class);
			
			//p 代表是调用该方法的对象,即该方法是属于哪个对象的
			method.invoke(p, new int[]{1,2,3});
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	//public String test4()
	public void test4() {
		try {
			Person p = new Person();//该对象最好也是通过反射的来的			
			Class clazz = Class.forName("about_reflect.Person");			
			Method method = clazz.getMethod("test4");
			
			//p 代表是调用该方法的对象,即该方法是属于哪个对象的,返回值是该方法的返回对象
			String info = (String) method.invoke(p);
			System.out.println(info);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	//private void test5()
	public void test5() {
		try {
			Person p = new Person();//该对象最好也是通过反射的来的			
			Class clazz = Class.forName("about_reflect.Person");	
			Method method = clazz.getDeclaredMethod("test5");
			method.setAccessible(true);
			
			 Method[] methods = clazz.getDeclaredMethods();
			 System.out.println(methods.length);
			
			//p 代表是调用该方法的对象,即该方法是属于哪个对象的
			method.invoke(p);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	//public static void test6(int info) 反射静态
	public void test6() {
		try {
			Class clazz = Class.forName("about_reflect.Person");	
			Method method = clazz.getDeclaredMethod("test6", int.class);
			System.out.println(method.getName());
			System.out.println(method.getParameterTypes()[0].getName());
			
			//因为静态无须对象来调用
			method.invoke(null, 123);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	//public static void main(String[] args) 反射主方法
	public void test7() {
		try {
			Class clazz = Class.forName("about_reflect.Person");	
			Method method = clazz.getDeclaredMethod("main", String[].class);			
			
			//method.invoke(null, new String[]{"aa","bb"});
			/*这种做法是错误的,因为它为了兼容1.4之前的会把String 数组拆成aa bb
			 *再把aa bb当成参数传给main方法,故而报错 ;
			 *
			 *下面两种做法
			 */
			method.invoke(null, (Object)(new String[]{"aa","bb"}));
			method.invoke(null, new Object[]{new String[]{"aa","bb"}});
			
			
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
}
