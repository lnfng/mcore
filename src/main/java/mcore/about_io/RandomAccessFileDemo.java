package mcore.about_io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * RandomAccessFile
 * 该类不算是IO体系中的子类
 * 
 * 但它是IO包中的成员。因为它具备读写的功能，其内部同时封装了
 * 输入流和输出流。
 * 内部封装了一个数组，而且通过指针对数组元素进行操作。
 * 可以通过getFilePointer获取指针位置
 * 同时通过seek改变指针的位置。
 * 
 * 功能：可以对文件进行指定写入和读取，可以将一个文件分成几个段进行操作
 * 
 * 局限：该类只能操作文件
 * Qian把文件加载到内存中，会不会很影响性能？
 * FileChannel类的：将文件中的某个区域直接映射到内存中；
 * 对于较大的文件，这通常比调用普通的 read 或 write 方法更为高效。 
 * @author JackQ
 *
 */
public class RandomAccessFileDemo {
	
	public static void main(String[] args) {
		//writeFile1();
		readFile();
//		writeFile2();
	}

	private static void readFile() {
		try {
			/**文件中      李四    a王五   b
			 * 只读模式不能进行写操作*/
			RandomAccessFile raf=new RandomAccessFile("test//RAF2.txt","r");
			//4个字节刚好装“李四”，一个中文两个字节，指针自动向后
			byte[] buf=new byte[4];
			raf.read(buf);
			System.out.println(new String(buf));
			raf.read(buf);
			for(byte b:buf){
				System.out.println(b);
			}
			System.out.println(new String(buf));
			//当前指针
			System.out.println(raf.getFilePointer());
			
			//指定指针的位置进行读取
			raf.seek(12);
			raf.read(buf);
			System.out.println(new String(buf));
			/*要进行指针的挪动,才能完全读取第一行
			 * raf.skipBytes(0) 是不能往回跳的，如象棋中的兵卒*/
//			raf.skipBytes(0);
			raf.seek(0);
			
			/*有乱码问题*/
			String data=
				new String(raf.readLine().getBytes("iso8859-1"),"GBK");
			System.out.println(data);
			
			raf.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeFile2() {
		try {
			RandomAccessFile raf=new RandomAccessFile("test//RAF2.txt","rw");
			
			raf.write("李四".getBytes());
			raf.writeInt(97);
			raf.write("王五".getBytes());
			raf.writeInt(98);
			/**以上共有16个字节*/
			
			/**指定位置进行加入,中间有十六个字节空出*/
			raf.seek(32);
			raf.write("赵龙".getBytes());
			raf.writeInt(100);
			
			/**指定位置进行加入，互不影响，位置有侵占，后者覆盖前者*/
			raf.seek(16);//第十六个字节之后
			raf.write("钱喜校".getBytes());
			raf.writeInt(111);
			
			raf.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void writeFile1() {
		try {
			RandomAccessFile raf=new RandomAccessFile("test//RAF.txt","rw");
			/**这样只能写出最低8位（一个字节），而int有四个字节*/
			//raf.write(123);
			
			//raf.writeChars("Qian");//Q i a n 这是结果显示（一个char 两个字节）
			//raf.write("Qian".getBytes());//Qian 这是结果显示
			raf.write("李四".getBytes());
			raf.writeInt(97);
			raf.write("王五".getBytes());
			raf.writeInt(98);
			
			raf.close();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
