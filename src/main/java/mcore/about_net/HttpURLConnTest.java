package mcore.about_net;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Permission;
import java.util.List;
import java.util.Map;

public class HttpURLConnTest {
	
	public static void main(String[] args) throws Exception {
		test1();
	}
	
	/**
	 * 对HttpURLConnection的传参设置content-type的不同
	 * 传的参数格式也是不一样的;
	 * @throws Exception
	 */
	public static void test2() throws Exception{
		String strUrl="http://127.0.0.1:8181/user-web/utest/tread.do";
		HttpURLConnection conn = 
				(HttpURLConnection)(new URL(strUrl).openConnection());
		conn.setDoOutput(true);
		conn.setDoInput(true);
		/**application/octet-stream ： 二进制流数据（如常见的文件下载）
		 * 
		 * multipart/form-data ： 需要在表单中进行文件上传时，就需要使用该格式
		 * 
		 * application/x-www-form-urlencoded ： 
		 * 	<form encType=””>中默认的encType，form表单数据被编码为key/value
		 * 	格式发送到服务器(表单默认的提交数据的格式)另外一种常见的媒体格式是上传文件之时使用的：
		 * 
		 * 详情见ContentType.java*/
		conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		conn.setConnectTimeout(1000*10);
		conn.setRequestMethod("POST");
		
		PrintWriter writer = new PrintWriter(conn.getOutputStream());
		writer.print("age=456&gender=m");//效果如<input name="age" value="456">
		writer.println("&name=qian");//如果要传送多个参数必须使用"&"符号连接
		writer.close();
		conn.getInputStream();//必须要getInputStream才能发送请求出去
		
		String rmsg = conn.getResponseMessage();
		log(">>ResponseMessage is >>:"+rmsg);//like "OK"
		
		String contentEncoding = conn.getContentEncoding();
		log(">>encoding is >>:"+contentEncoding);
		
		String contentType = conn.getContentType();
		log(">>contentType is >>:"+contentType);
		
		Map<String, List<String>> fields = conn.getHeaderFields();
		log(">>files is >>:"+fields);
		
		Permission permission = conn.getPermission();
		String name = permission.getName();
		log(">>permission name >>:"+name);
		String actions = permission.getActions();
		log(">>permission actions>>:"+actions);
	}
	
	/**
	 * test URLEncoder and URLDecoder
	 */
	public static void test1(){try {
		
			/* 进行两次编码是因为在get请求时，浏览器地址会第一次
			 * 编码进行解码，这样就会显示具体内容，二次编码就可以解决这个问题
			 */
			String en1 = URLEncoder.encode("你好", "utf-8");
			log("en1="+en1);//6个字节 en1=%E4%BD%A0%E5%A5%BD
			String enGBK=URLEncoder.encode("你好", "gbk");
			log("enGBK="+enGBK);//4个字节enGBK=%C4%E3%BA%C3
			
			String en2 = URLEncoder.encode(en1, "utf-8");
			log("en2="+en2);
			String d1 = URLDecoder.decode(en1, "utf-8");
			log("d1="+d1);
			String d2 = URLDecoder.decode(en2,"utf-8");
			log("d2="+d2);
			
			String en3="%E4%BE%AF%E4%B9%89%E5%8D%8E";
			String d3 = URLDecoder.decode(en3,"ISO-8859-1");
			log("d3="+d3);
			
			
			
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}}
	

	
	public static void log(String msg){
		System.out.println("-"+msg);
	}
}
