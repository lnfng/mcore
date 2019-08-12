package mcore.about_reflect.introspector;

public class Person {
	
	private boolean flag;	
	private String name;
	private String password;
	private int age;
	
	private String abCcDd;
	
	public String getAbCcDd() {
		return abCcDd;
	}
	
	public void setAbCcDd(String abCcDd) {
		this.abCcDd = abCcDd;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
