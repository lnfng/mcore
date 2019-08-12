package mcore.about_proxy.aop_framework;

import java.io.InputStream;
import java.util.Collection;

public class AopFrameworkTest {

	public static void main(String[] args) throws Exception {
		
		InputStream ips =
			AopFrameworkTest.class.getResourceAsStream("config.properties");
		Object bean = new BeanFactory(ips).getBean("xxx");
		System.out.println(bean.getClass().getName());
		Collection c=(Collection)bean;
		c.add(123);
		c.add("123");
		System.out.println(c);
		System.out.println(c.getClass().getName());
	}

}
