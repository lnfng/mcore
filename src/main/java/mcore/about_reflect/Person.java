package mcore.about_reflect;

import java.util.Date;

public class Person {
	
	public String test="this string is used to test the reflect";
	public String str = "字段String";
	public final String fianlStr = "final修饰的字符串";
	public final String newFianlStr = new String("newFianlStr字符串");
	private int _int = 123456;
	public static String[] str_arr = {"aa","bb"};
	
	
	private String name;
	private String password;
	private int age;
	private Date birthday;
	
	public Person(){
		System.out.println("the constructor Person() is invoken!");
	}
	
	public Person(String name){
		this.name=name;
		System.out.println("the constructor Person(String name) is invoken!");
	}
	
	private Person(String name,int age){
		this.name=name;
		this.age=age;
		System.out.println("the private constructor Person(String name,int age) is invoken!");
	}
	
	
	public void test1(){
		System.out.println("public void test1() is invoken");
	}
	
	
	public void test2(int age){
		System.out.println("public void test2(int) is invoken "+age);
	}
	
	public void test3(int[] nums){
		System.out.println("public void test3(int[]) is invoken "+nums.length);
	}
	
	
	public String test4(){
		System.out.println("public String test4() is invoken ");
		return "test4()";
	}
	
	private void test5(){
		System.out.println("private void test5() is invoken ");
	}
	
	
	public static void test6(int info){
		System.out.println("public static void test6(int info) "+info);
	}
	
	
	public static void main(String[] args) {
		System.out.println("public static void main(String[] args) is invoken");
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getTest(){
		return test;
	}
	
	public void setTest(String test){
		this.test=test;
	}
	
}
