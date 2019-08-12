package mcore.about_reflect;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.Test;

/* 高效的反射工具
 * 参考:https://unmi.cc/java-reflectasm-bytecode-usage/
 * <dependency>
    <groupId>com.esotericsoftware.reflectasm</groupId>
    <artifactId>reflectasm</artifactId>
    <version>1.05</version>
	</dependency>
 */


//反射字段
public class ReflectField {
	
	@Test
	//反射字段 public String str = "字段String";
	public void test1() throws Exception{
		Person p = new Person();
		
		Class clazz = Class.forName("about_reflect.Person");
		//获得公开的字段
		Field field = clazz.getField("str");
		String str = (String) field.get(p);
		
		System.out.println(str);
		
		//获得该字段的类型
		Class type = field.getType();
		System.out.println(type);//class java.lang.String
		
		field.set(p, "xxxxx");
		System.out.println(p.str);
		
	}
	
	@Test
	//反射字段 private int _int = 123456;
	public void test2() throws Exception{
		Person p = new Person();
		
		Class clazz = Class.forName("about_reflect.Person");
		//获得声明的域，包括私有，静态等
		Field field = clazz.getDeclaredField("_int");
		//私有字段,需要暴力反射
		field.setAccessible(true);
		int _int = field.getInt(p);//or field.get(p) 前一个可以强转为int
		System.out.println(_int);
		
	}
	
	@Test
	//反射字段 public static String[] str_arr = {"aa","bb"};
	public void test3() throws Exception{
		Person p = new Person();
		
		Class clazz = Class.forName("about_reflect.Person");	
		//获得声明的域，包括私有，静态等
		Field field = clazz.getDeclaredField("str_arr");
		
		//因为静态是不属于对象身上的，故get的参数可以是clazz，null，p
		String[] str_arr = (String[]) field.get(null);
		
		for(String str:str_arr){
			System.out.println(str);
		}
		
	}
	
	@Test
	//反射字段 public static String[] str_arr = {"aa","bb"};
	//并改变它的值
	public void test4() throws Exception{
		Person p = new Person();
		
		Class clazz = Class.forName("about_reflect.Person");	
		//获得声明的域，包括私有，静态等
		Field field = clazz.getDeclaredField("str_arr");
		//改变该对象的值
		field.set(null,new String[]{"123","456"});
		
		//因为静态是不属于对象身上的，故get的参数可以是clazz，null，p
		String[] str_arr = (String[]) field.get(null);
		
		for(String str:str_arr){
			System.out.println(str);
		}
	}
	
	@Test
	public void test5() throws Exception{
		
		Class clazz = Class.forName("about_reflect.Person");	
		//获得声明的域，包括私有，静态等
		Field[] fields = clazz.getDeclaredFields();
		
		for(Field f:fields){
			System.out.println("-----"+f.getName()+"-----");
			

			PropertyDescriptor descriptor = new PropertyDescriptor(f.getName(), clazz);
			/**
			 * 通过Field 去获得对应的setter 与 getter 方法是错误的
			 * 
			 */
			Method readMethod = descriptor.getReadMethod();
			System.out.println(readMethod.getName());
		}
		
	}
	
	@Test
	//反射字段 public final String fianlStr = "final修饰的字符串";
	/**
	 * 参考地址: https://unmi.cc/java-reflection-modify-final-field/#more-8231
	 */
	public void test6() throws Exception{
		Person p = new Person();
		
		Class clazz = Class.forName("about_reflect.Person");
		//获得公开的字段
		Field field = clazz.getField("fianlStr");
		
		//修改final属性
		Field modifiersField = Field.class.getDeclaredField("modifiers");
	    modifiersField.setAccessible(true); //Field 的 modifiers 是私有的
	    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		
		String str = (String) field.get(p);
		
		System.out.println(str);
		
		//获得该字段的类型
		Class type = field.getType();
		System.out.println(type);//class java.lang.String
		
		field.set(p, "xxxxx");
		System.out.println(p.fianlStr);  //输出仍然是final修饰的字符串
		/*
		 为什么代码执行下来没问题，但输出又还是原来的值呢？但总是可以通过反射方式获取到修改后的新值。
		 这就是 Java  编译器对 final 属型的内联优化，即编译时把该 final 的值直接放到了引用它的地方。
		 即使是反射修改了该属性，但这种事后处理于事无补。
		 
		 那么 Java 会对什么类型的 final 值进行内联编译呢？
		 它们基本类型 byte, char, short, int, long, float, double, boolean; 
		 再加上 Literal String 类型(直接双引号字符串)。只要是不被编译器内联优化的 final 属性
		 都可以通过反射有效的进行修改(修改后能使用到新的值)
         String 类型比较特殊， 如果把 Person 类 name 属性改成用 new String("Mike")
		 */
		
		Field newField = clazz.getField("newFianlStr");
		
		//修改final属性
		Field modifiersNewField = Field.class.getDeclaredField("modifiers");
	    modifiersField.setAccessible(true); //Field 的 modifiers 是私有的
	    modifiersField.setInt(newField, field.getModifiers() & ~Modifier.FINAL);
		
	    String newFianlStr = (String) newField.get(p);
	    System.out.println(">> 原来的属性值newFianlStr="+newFianlStr);
	    
	    newField.set(p, "xxxxx");
	    System.out.println(">> 修改后的属性值newFianlStr="+p.newFianlStr);
	    
	}
	
}
