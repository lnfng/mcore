package remote.invoker.http;


import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 支持https请求
 * Socket 创建工厂
 * @author Qian
 */
public class HttpsSecureProtocolSocketFactory implements ProtocolSocketFactory {
	
	private SSLSocketFactory socketFactory;
	
	public HttpsSecureProtocolSocketFactory() {
		initSSLContext();
	}

	private void initSSLContext() {
		try {
			SSLContext sslcontext = SSLContext.getInstance("SSL");
			sslcontext.init(null, new TrustManager[] { new TrustAnyTrustManager()}, new SecureRandom());
			SSLSocketFactory socketFactory = sslcontext.getSocketFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Socket createSocket(Socket socket, String host, int port,boolean autoClose) 
			throws IOException, UnknownHostException {
		return socketFactory.createSocket(socket, host, port, autoClose);
	}

	public Socket createSocket(String host, int port) throws IOException,UnknownHostException {
		return socketFactory.createSocket(host, port);
	}

	public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) 
			throws IOException, UnknownHostException {
		return socketFactory.createSocket(host, port,clientHost, clientPort);
	}

	public Socket createSocket(String host, int port, InetAddress localAddress, int localPort, HttpConnectionParams params)
			throws IOException,UnknownHostException, ConnectTimeoutException {
		if (params == null) {
			throw new IllegalArgumentException("Parameters may not be null");
		}
		int timeout = params.getConnectionTimeout();
		if (timeout == 0) {
			return socketFactory.createSocket(host, port, localAddress,localPort);
		} else {
			Socket socket = socketFactory.createSocket();
			SocketAddress localAddr = new InetSocketAddress(localAddress,localPort);
			SocketAddress remoteAddr = new InetSocketAddress(host, port);
			socket.bind(localAddr);
			socket.connect(remoteAddr, timeout);
			return socket;
		}
	}

	// 信任机制
	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType) 
			throws CertificateException {}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

}