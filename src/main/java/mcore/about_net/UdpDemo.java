package mcore.about_net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
public class UdpDemo {
	
	public static void main(String[] args) {
		udpSend();
		udpReceive();
	}

	private static void udpReceive() {
		
		 /**思路
		 *
		 *1.定义udpsocket服务。
		 *2.定义一个数据包
		 *3.通过socket服务的receive方法将收到的数据存入到已定义好的数据包中
		 *4.通过数据包对象的特有功能，将这些数据取出。
		 *5.关闭资源
		 **/
		new Thread(new Runnable() {
			public void run() {
				try {
					DatagramSocket ds = new DatagramSocket(10001);

					while(true)
					{
						byte[] buf = new byte[1024];

						DatagramPacket dp = new DatagramPacket(buf,buf.length);

						ds.receive(dp);

						String ip = dp.getAddress().getHostAddress();
						String data = new String(dp.getData(),0,dp.getLength());

						System.out.println(ip+"::"+data);
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}

	private static void udpSend() {
		
		/**需求：通过udp将一段数据传输出去

		1. 建立udpsocket服务。
		2. 提供数据，并将数据封装到数据包中
		3. 通过socket服务的发送功能，将数据发送出去。
		4. 关闭资源。
		*/
		new Thread(new Runnable() {
			public void run() {
				try {
					DatagramSocket ds = new DatagramSocket();

					BufferedReader bufr =
							new BufferedReader(new InputStreamReader(System.in));
					String line = null;

					while((line=bufr.readLine())!=null)
					{
						if("886".equals(line))
							break;
						byte[] buf = line.getBytes();

						DatagramPacket dp = new DatagramPacket(buf,buf.length,
							InetAddress.getByName("127.0.0.1"),10001);
						
						ds.send(dp);
					}
					ds.close();
				} catch (Exception e) {
				}
			}
		}).start();
	}
}
