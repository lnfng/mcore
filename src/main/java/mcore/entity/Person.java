package mcore.entity;

public class Person {//称为javaBean
	
	private boolean flag;	
	private String name;
	private String password;
	private int age;
	
	//javaBean的属性是通过set 或 get 方法却定
	//如其除了当前三个,getClass 是一个属性
	
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
	
	public String from(){
		ClassLoader classLoader = 
				this.getClass().getClassLoader();
		System.out.println("from:"+classLoader);
		return classLoader.toString();
	}
	
}
