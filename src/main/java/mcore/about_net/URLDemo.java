package mcore.about_net;

import java.net.URL;

public class URLDemo {
	
	public static void main(String[] args) throws Exception {
		/*
		 * 如果不指定端口：getPort:-1
		 * if(getPort==-1)
		 * 		port=80;
		 */	
		URL url=new URL("http://127.0.0.1:8080/docs/deployer-howto.html?name=qian&age=23");
		String getHost=url.getHost();
		int getPort=url.getPort();
		String getProtocol=url.getProtocol();
		String getQuery=url.getQuery();
		String getPath=url.getPath();
		String getFile=url.getFile();
		/*
		 * the result:
		    getHost:127.0.0.1
			getPort:8080
			getProtocol:http
			getQuery:name=qian&age=23
			getPath:/docs/deployer-howto.html
			getFile:/docs/deployer-howto.html?name=qian&age=23
		 */
		System.out.println("getHost:"+getHost);
		System.out.println("getPort:"+getPort);
		System.out.println("getProtocol:"+getProtocol);
		System.out.println("getQuery:"+getQuery);
		System.out.println("getPath:"+getPath);
		System.out.println("getFile:"+getFile);
		
	}
	/**
 String getHost() 
          获取此 URL 的主机名（如果适用）。 
 int getPort() 
          获取此 URL 的端口号。 
 String getProtocol() 
          获取此 URL 的协议名称。 
 String getQuery() 
          获取此 URL 的查询部分。 
 String getPath() 
          获取此 URL 的路径部分。 
 String getFile() 
          获取此 URL 的文件名。 

	 */
}
