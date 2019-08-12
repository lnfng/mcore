package mcore.about_io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * PrintStream 与  PrintWriter 相似
 * PrintWriter 接收参数
 * 1，file 对象
 * 2，字符串路径
 * 3，字节输出流
 * 4，字符输出流  （PrintStream 没有）
 * @author JackQ
 *
 */
public class PrintWriterDemo {
	

	public static void main(String[] args) throws IOException {
		//录入键盘
		BufferedReader bufr=
				new BufferedReader(new InputStreamReader(System.in));
		
		//true 为自动刷新,可以写到文件，控制台，web也是用这个的
//		PrintWriter out=new PrintWriter(System.out,true);
		PrintWriter out=new PrintWriter("splitFiles//test.txt");
		//打印对象的toString的方法
//		out.print(new Student("Qian", 23));
//		out.close();
		String line=null;
		while((line=bufr.readLine())!=null){
			if("over".equals(line))
				break;
			out.println(line.toUpperCase());
		}
		out.close();
		bufr.close();
	}

}
class Student{
	private String name;
	private int age;
	
	public Student(String name,int age) {
		this.name=name;
		this.age=age;
	}
}
