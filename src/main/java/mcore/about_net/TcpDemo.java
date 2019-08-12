package mcore.about_net;

import java.io.*;
import java.net.*;

/**
 * Udp（相当于邮寄）协议：
 * 将数据及源和目的封装到数据包中，每个数据包的大小限制64k；
 * 缺点：面向无连接，是不可靠协议
 * 优点：不需要建立连接，数度快
 * 用途：聊天，视频广播等都是用udp协议
 * 
 * Tcp（相当于通话）
 * 建立连接，形成数据传输通道
 * 在连接中进行大数据量传送
 * 通过三次握手连接，是可靠连接
 * 必须建立连接，效率稍低
 * 用途: 下载视频，文件等，不容许数据丢失的。
 * @author JackQ
 *
 */
public class TcpDemo {

	public static void main(String[] args) {
		new Thread(new UploadServer()).start();
		new Thread(new UploadClient()).start();
	}
}

class UploadServer implements Runnable{

	public void run() {
		try {
			//后面参数是指定连接队列的长度即同时可以允许多少人在线
			ServerSocket server=new ServerSocket(10008,20);
			Socket s=server.accept();
			BufferedReader bufIn=
					new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter writer=new PrintWriter(s.getOutputStream(),true);
			
			for(String line=null;(line=bufIn.readLine())!=null;){
				System.out.println(line);
			}
			
			//writer.println可以和readLine 相匹配
			writer.println("上传成功");
			System.out.println("isokay");
			s.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}

class UploadClient implements Runnable{

	public void run() {
		try {
			Socket client=new Socket("127.0.0.1",10008);
			
			BufferedReader bufr=
					new BufferedReader(new FileReader("file//IPDemo.java"));
			BufferedWriter bufOut=
					new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			BufferedReader bufIn=
					new BufferedReader(new InputStreamReader(client.getInputStream()));
			/**
			 * 文件有默认的结束标记读取文件能自动结束，
			 * 但写到服务端就不一样了，现在的方式没有把文件结束标志写出去
			 * 所以需要显式写给服务端client.shutdownOutput();
			 */
			for(String line=null;(line=bufr.readLine())!=null;){
				bufOut.write(line);
				bufOut.newLine();
				bufOut.flush();
			}
			client.shutdownOutput();
			
			String info=bufIn.readLine();
			System.out.println(info);
			
			bufr.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
