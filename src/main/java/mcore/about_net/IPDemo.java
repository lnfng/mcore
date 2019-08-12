package mcore.about_net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 常见的协议
 * 应用层：ftp http
 * 传输层：tcp udp
 * 网际层：ip
 * @author JackQ
 *
 */
public class IPDemo {
	
	public static void main(String[] args) throws UnknownHostException {
		//因为有可能一个域名对应多个IP地址
		InetAddress[] inetAddress=InetAddress.getAllByName("www.youku.com");
		for (int i = 0; i < inetAddress.length; i++) {
			/**qian  获取此 IP 地址的完全限定域名。*/
			System.out.println(inetAddress[i].getCanonicalHostName());
			/**127.0.0.1  返回 IP 地址字符串（以文本表现形式）。*/
			System.out.println(inetAddress[i].getHostAddress());
			/**localhost  获取此 IP 地址的主机名。*/
			System.out.println(inetAddress[i].getHostName());
		}
		
		String hostIp="";
		try {
			InetAddress addr = InetAddress.getLocalHost();
			hostIp = addr.getHostAddress();
			
			System.out.println(hostIp);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
