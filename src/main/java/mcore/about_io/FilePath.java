package mcore.about_io;

import java.io.File;

public class FilePath {
	
	/*
	 * 讲的非常好啊，学习了，这里好像有点错。撇斜杠（/）不是“当前类的绝对路径”，
	 * 是classpath的绝对路径。你当前这个类假如包结构是com.foo.bar.Test，
	 * 获得的绝对路径还是相同的，不管你这个类放到什么包结构，获得的都是classpath
	 */

	public static void main(String[] args) {
          //当前类的绝对路径
          System.out.println(FilePath.class.getResource("/").getFile());
          //指定CLASSPATH文件的绝对路径
          System.out.println(FilePath.class.getResource("/").getFile());
          //指定CLASSPATH文件的绝对路径
          File f = new File(FilePath.class.getResource("/").getFile());
          System.out.println(f.getPath());
    } 

}
