package mcore.about_io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 数据输入流允许应用程序以与机器无关方式从底层输入流中读取基本 Java 数据类型。
 * 应用程序可以使用数据输出流写入稍后由数据输入流读取的数据。 
 * @author JackQ
 *
 */
public class DataStreamDemo {
	
	public static void main(String[] args) throws IOException {
		//writeData();
		readData2();
	}

	private static void readData()  throws IOException{
		DataInputStream dis=
				new DataInputStream(new FileInputStream("test//dataStream.txt"));
		int num=dis.readInt();
		boolean b=dis.readBoolean();
		String str=dis.readUTF();
		
		dis.close();
		System.out.println("int:"+num+" boolean:"+b+" str:"+str);
	}

	private static void writeData() throws IOException {
		/**把Java的基本对象写到文件中，但这比不会显示真确的字符*/
		DataOutputStream dos=
				new DataOutputStream(new FileOutputStream("test//dataStream.txt"));
		dos.writeInt(123);
		dos.writeBoolean(true);
		/**这个方法使用的是修改版的UTF-8（8个字节）的编码，
		 * 与传统的UTF-8（6个字节）的编码不一样，故只能用对应的方法读取*/
		dos.writeUTF("你好");
		
		dos.close();
	}
	
	static void readData2()  throws IOException{
		InputStream stream = DataStreamDemo.class.getClassLoader().getResourceAsStream("aboutIO/FilePath.class");
		InputStream stream2 = DataStreamDemo.class.getClassLoader().getResourceAsStream("aboutIO/FilePath.java");
		System.out.println(">> stream:"+stream);
		DataInputStream dis=
				new DataInputStream(new FileInputStream("test//dataStream.txt"));
		int num=dis.readInt();
		boolean b=dis.readBoolean();
		String str=dis.readUTF();
		
		dis.close();
		System.out.println("int:"+num+" boolean:"+b+" str:"+str);
	}

	
}
