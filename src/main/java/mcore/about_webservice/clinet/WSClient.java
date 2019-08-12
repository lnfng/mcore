package mcore.about_webservice.clinet;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 用来连接调用webservice
 * @author Qian
 *
 */
public class WSClient {

	private static String soap12path="http://127.0.0.1:8080/services/SimpleService.SimpleServiceHttpSoap12Endpoint/";
	private static String soap11path="http://127.0.0.1:8080/services/SimpleService.SimpleServiceHttpSoap11Endpoint/";
	
	
	public static void main(String[] args) throws Exception {
		// useSOAP11();
		
		//soap12调用不成功,因为jdk不支持12形式的访问
		// useSOAP12();

		testWebService();

	}

	static void testWebService() throws Exception {
		String url = "http://localhost/ws?wsdl";
		String sendMsg=
				"<?xml version='1.0' encoding='UTF-8'?>"
				+ "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
					+"xmlns:ser=\"http://service.about_webservice.mcore\">"
					+"<soapenv:Header/>"
					+"<soapenv:Body>"
						+"<ser:sayHello>"
						+"<arg0>风云世界</arg0>"
						+"</ser:sayHello>"
					+"</soapenv:Body>"
				+"</soapenv:Envelope>";

		//soap 11
		HttpURLConnection conn=(HttpURLConnection)new URL(url).openConnection();
		conn.setRequestProperty("Content-Type","text/xml;charset=UTF-8");
		conn.setRequestProperty("User-Agent","Apache-HttpClient/4.1.1");
		conn.setRequestProperty("SOAPAction", "sayHello");
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5*1000);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		conn.getOutputStream().write(sendMsg.getBytes("UTF-8"));

		InputStream inputStream = conn.getInputStream();
		byte[] buf=new byte[1024];
		for(int len=0;(len=inputStream.read(buf))>0;){
			System.out.println(new String(buf,0,len));
		}

		conn.disconnect();
	}
	
	/**
	 * 使用URLConnect类+ SOAP11 协议进行webservice的调用
	 * @throws Exception
	 */
	public static void useSOAP11() throws Exception{
		
		/**
		    POST http://127.0.0.1:8080/services/SimpleService.SimpleServiceHttpSoap11Endpoint/ HTTP/1.1
			Accept-Encoding: gzip,deflate
			Content-Type: text/xml;charset=UTF-8
			SOAPAction: "urn:helloService"
			Content-Length: 309
			Host: 127.0.0.1:8080
			Connection: Keep-Alive
			User-Agent: Apache-HttpClient/4.1.1 (java 1.5)
		 */
		
		String sendMsg=
				"<?xml version='1.0' encoding='UTF-8'?>"
				+ "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://user.qian.org\">"
					+"<soapenv:Header/>"
						+"<soapenv:Body>"
						+"<web:helloService>"
				        +"<!--Optional:-->"
				        +"<web:msg>风云世界</web:msg>"
				        +"</web:helloService>"
			        +"</soapenv:Body>"
			    +"</soapenv:Envelope>";
		
		//soap 11
		HttpURLConnection conn=(HttpURLConnection)new URL(soap11path).openConnection();
		//SOAPAction: "urn:helloService"
		conn.setRequestProperty("SOAPAction","\"urn:helloService\"");
		conn.setRequestProperty("Content-Type","text/xml;charset=UTF-8;");
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5*1000);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		
		conn.getOutputStream().write(sendMsg.getBytes());
		
		InputStream inputStream = conn.getInputStream();
		byte[] buf=new byte[1024];
		for(int len=0;(len=inputStream.read(buf))>0;){
			System.out.println(new String(buf,0,len));
		}
	
		conn.disconnect();
	}
	
	/**
	 * 使用URLConnect类 + SOAP12 协议进行webservice的调用
	 * @throws Exception
	 */
	public static void useSOAP12() throws Exception{
		
		/**
		    POST http://127.0.0.1:8080/services/SimpleService.SimpleServiceHttpSoap12Endpoint/ HTTP/1.1
			Accept-Encoding: gzip,deflate
			Content-Type: application/soap+xml;charset=UTF-8;action="urn:helloService"
			Content-Length: 289
			Host: 127.0.0.1:8080
			Connection: Keep-Alive
			User-Agent: Apache-HttpClient/4.1.1 (java 1.5)

		 */
		/*
		 	soap1.1 xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
			soap1.2 xmlns:soap="http://www.w3.org/2003/05/soap-envelope"
		*/
		String sendMsg=
				"<?xml version='1.0' encoding='UTF-8'?>"
				+ "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:web=\"http://user.qian.org\">"
					+"<soapenv:Header/>"
						+"<soapenv:Body>"
						+"<web:helloService>"
				        +"<!--Optional:-->"
				        +"<web:msg>风云世界</web:msg>"
				        +"</web:helloService>"
			        +"</soapenv:Body>"
			    +"</soapenv:Envelope>";
		
		//soap 11
		HttpURLConnection conn=(HttpURLConnection)new URL(soap12path).openConnection();
		conn.setRequestProperty("Content-Type","application/soap+xml;charset=UTF-8;action=\"urn:helloService\"");
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5*1000);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		
		OutputStream os = conn.getOutputStream();
		os.write(sendMsg.getBytes());
		os.close();
		
		InputStream inputStream = conn.getInputStream();
		byte[] buf=new byte[1024];
		for(int len=0;(len=inputStream.read(buf))>0;){
			System.out.println(new String(buf,0,len));
		}
	
		conn.disconnect();
	}
	
}
