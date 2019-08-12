package mcore.about_reflect.introspector;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.junit.Test;

public class IntrospectorDemo1 {
	
	
	@Test
	public void test1() throws Exception{
//	    BeanInfo info = Introspector.getBeanInfo(Person.class);//获取包括父类的bean信息
	    BeanInfo info = Introspector.getBeanInfo(Person.class,Object.class);//去除父类的bean属性信息
	    //获得属性描述
	    PropertyDescriptor[] pds = info.getPropertyDescriptors();
	    
	    for(PropertyDescriptor pd : pds){
	    	System.out.println(pd.getName());
	    }
	    
	}
	
	
	@Test
	public void test2() throws Exception{

		Person p = new Person();
		
		
		PropertyDescriptor pd = new PropertyDescriptor("age",p.getClass());	
		System.out.println(pd.getName());
		Method method = pd.getWriteMethod();//public void setAge(int age)
		
		
		method.invoke(p, 18);
		
		Method method2 = pd.getReadMethod();//public int getAge()
		
		
		System.out.println(p.getAge());
		System.out.println(method2.invoke(p));
	}
	
	
	@Test
	public void test3() throws Exception{
		
		Person p = new Person();
		
		PropertyDescriptor pd = new PropertyDescriptor("abCcDd",p.getClass());
		Class clazz = pd.getPropertyType();

		System.out.println(clazz.getName());
	}

	
	
}
