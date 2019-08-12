package mcore.about_net;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class URLConnectionDemo {
	
	public static void main(String[] args) throws Exception {
		/**
		 * 如果不指定端口：getPort:-1
		 * if(getPort==-1)
		 * 		port=80;
		 */	
		URL url=new URL("http://127.0.0.1:8080/docs/deployer-howto.html?name=qian&age=23");
		System.out.println("getHost:"+url.getHost()+" getPort:"+url.getPort());
		/**
		 * 该类内部封装了socket，即把传输层编程转到了应用层编程
		 */
		URLConnection conn=url.openConnection();
		System.out.println(conn);
		
		InputStream is=conn.getInputStream();
		byte[] buf=new byte[1024];
		/**获取到的是主体内容，conn对象已经把传输层的协议拆包了*/
		for(int len=0;(len=is.read(buf))!=-1;){
			//System.out.println(new String(buf,0,len));
		}
		
		Map<String,List<String>>  props=conn.getHeaderFields() ;
		Set<String> keys=props.keySet();
		for(String k:keys){
			System.out.println(k+":"+props.get(k));
		}
		
	}

}
