package mcore.about_reflect;

import javax.management.MXBean;

@MXBean
public interface ReflectInterface {
	
	String sayHello(String name);
	
	int getIDnum(String uid);

}
