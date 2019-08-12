package mcore.about_reflect;

public class RFimpl implements ReflectInterface {

	public String sayHello(String name) {
		return "Hello "+name;
	}

	public int getIDnum(String uid) {
		return 0;
	}

}
