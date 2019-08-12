package mcore.about_io;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;


public class FileObject {

	/**创建带文件夹的文件*/
	@Test
	public void test1() {
		String decr="e:\\AAA\\bbb\\ccc1\\ycy.txt";
		//1. 新建file对象
		File file = new File(decr);
		//2. 获得file对象的路径
		File dir = file.getParentFile();
		
		System.out.println(dir);
		
		try {
			if (!dir.exists()) {
				//3. 创建文件夹
				System.out.println("创建文件夹 "+dir+" isOkay-->"+dir.mkdirs());
			}
			//4. 创建文件
			System.out.println("创建文件 "+file.getName()+"isOkay-->"+file.createNewFile());
		} catch (IOException e) {
			/**获取异常栈里的东西*/
			StackTraceElement[] stackTrace = e.getStackTrace();
			for(StackTraceElement ste:stackTrace){
				System.out.println(
						 "   CalssName: "+ste.getClassName()
						+"   FileName: "+ste.getFileName()
						+"   MethodName: "+ste.getMethodName()
						+"   LineNum: "+ste.getLineNumber());
			}
			e.printStackTrace();
		}
	}
	
	@Test
	public void testClassLoaderPath(){
		//当前类的绝对路径
        System.out.println(FileObject.class.getResource("/").getFile());
        //指定CLASSPATH文件的绝对路径
        System.out.println(String.class.getResource("/").getFile());
        //指定CLASSPATH文件的绝对路径
        File f = new File(String.class.getResource("/").getFile());
        System.out.println(f.getPath());
        
        /*  /D:/WorkSpaces/CommentSpace/AboutTest/BaseTest/bin/
			/D:/WorkSpaces/CommentSpace/AboutTest/BaseTest/bin/
			D:\WorkSpaces\CommentSpace\AboutTest\BaseTest\bin
			sun.misc.Launcher$AppClassLoader@7041825e
			null*/
		
        URL r1 = FileObject.class.getResource("/");
        URL r2 = String.class.getResource("/");
        
        
        System.out.println(FileObject.class.getClassLoader());
        System.out.println(String.class.getClassLoader());
       
	}
}
