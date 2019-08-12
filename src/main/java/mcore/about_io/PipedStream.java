package mcore.about_io;

import java.io.*;
/**
 * 管道输入流应该连接到管道输出流；管道输入流提供要写入管道输出流的所有数据字节。
 * 通常，数据由某个线程从 PipedInputStream 对象读取，
 * 并由其他线程将其写入到相应的 PipedOutputStream。
 * 不建议对这两个对象尝试使用单个线程，因为这样可能死锁线程。
 * 管道输入流包含一个缓冲区，可在缓冲区限定的范围内将读操作和写操作分离开。
 * 如果向连接管道输出流提供数据字节的线程不再存在，则认为该管道已损坏。
 * 
 * 比较少用，一般要用两个线程
 * @author JackQ
 *
 */
public class PipedStream {
	
	public static void main(String[] args) throws Exception {
		PipedInputStream pis=new PipedInputStream();
		PipedOutputStream pos=new PipedOutputStream();
		pis.connect(pos);
		
		new Thread(new Read(pis)).start();
		new Thread(new Write(pos)).start();
	}

}


class Read implements Runnable{

	private PipedInputStream pis;
	
	Read(PipedInputStream pis){
		this.pis=pis;
	}
	
	public void run() {
	try {
		Thread.sleep(2000);
		System.out.println(pis.available());
		byte[] buf=new byte[1024];
		for(int len=0;(len=pis.read(buf))!=-1;){
			System.out.println(new String(buf,0,len));
		}
		pis.close();
	} catch (Exception e) {}
	}
	
}


class Write implements Runnable{
	private PipedOutputStream pos;

	Write(PipedOutputStream pos){
		this.pos=pos;
	}
	public void run() {
		try {
			pos.write("这是管道流的示范".getBytes());	
			pos.close();
		} catch (Exception e) {	}
		
	}
	
}









