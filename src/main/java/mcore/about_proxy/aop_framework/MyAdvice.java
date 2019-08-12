package mcore.about_proxy.aop_framework;

import java.lang.reflect.Method;

import mcore.about_proxy.Advice;

public class MyAdvice implements Advice {

	long begintime=0;
	public void beforeMethod(Method method) {
		begintime=System./*currentTimeMillis()*/nanoTime();//纳秒
	}
	public void afterMethod(Method method) {
		long endtime=System./*currentTimeMillis()*/nanoTime();
		System.out.println("代理类调用 "+method.getName()+" 耗时"+(endtime-begintime));
	}

}
