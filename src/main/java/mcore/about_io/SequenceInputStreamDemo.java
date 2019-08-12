package mcore.about_io;
import java.io.*;
import java.util.*;
/**
 * 将多个读取流合并成一个流再进行写操作
 * 还有对应的文件切割
 * @author JackQ
 *
 */
public class SequenceInputStreamDemo {

	public static void main(String[] args) throws IOException {
		Vector<FileInputStream> v=new Vector<FileInputStream>();
		v.add(new FileInputStream("sequenceInputStream//1.txt"));
		v.add(new FileInputStream("sequenceInputStream//2.txt"));
		v.add(new FileInputStream("sequenceInputStream//3.txt"));
		
		//Enumeration 是jdk1.0的
		Enumeration<FileInputStream> e=v.elements();
		SequenceInputStream sis=new SequenceInputStream(e);
		
		//剩下的就是读写操作啦
		FileOutputStream fos=new FileOutputStream("sequenceInputStream//4.txt");
		byte[] buf=new byte[1024];
		for(int len=0;(len=sis.read(buf))>0;){
			fos.write(buf, 0, len);
		}
		fos.close();
		sis.close();
	}

}
