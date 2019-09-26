package remote.invoker.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import remote.invoker.Invoker;
import remote.invoker.Irequest;
import remote.invoker.Uresponse;
import remote.invoker.common.RequestConfig;

/**
 * 远程Http调用类
 * 使用commons-httpclient实现
 * @author Qian
 */
public class HttpInvoker implements Invoker {

	// 换行符
	private static final String SEPARATOR = System.getProperty("line.separator");
	// 多线程管理
	private final static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	private final static HttpClient httpClient = new HttpClient(connectionManager);
	private final static HttpInvoker instance = new HttpInvoker();
	
	static {initParams();}
	
	private HttpInvoker() {}
	
	public static HttpInvoker getInstance() { return instance; }
	
	
	@Override
	public <T extends Uresponse> T execute(Irequest req, Class<T> respType) {
		
		RequestConfig config = req.getConfig();
		if (config == null) throw new RuntimeException("requeset config is required.");
		
		String url = config.getUrl();
		if (url == null) throw new RuntimeException("URL is required.");
		
		try {
			HttpHandEnum httpHand = config.getHttpHand();
			Uresponse resp = respType.newInstance();
			String charset = config.getCharset();
			HttpMethodBase httpMethod = null;
			String reqStr = req.generateReqStr();
			reqStr = reqStr != null ? reqStr : "";
			
			if (HttpMethodEnum.GET.equals(config.getHttpMethodName())) {
				GetMethod getMethod = new GetMethod(url);
				getMethod.setQueryString(reqStr);
				httpMethod = getMethod;
						
			} else {
				PostMethod postMethod = new PostMethod(url);
				postMethod.setRequestEntity(new ByteArrayRequestEntity(reqStr.getBytes(charset)));
				httpMethod = postMethod;
			}
			
			HttpMethodParams methodParams = httpMethod.getParams();
			methodParams.setSoTimeout(config.getReadTimeOut()); // 读取超时
			for (Map.Entry<String, String> hand : httpHand.getHandParams(charset).entrySet()) {
				httpMethod.addRequestHeader(hand.getKey(), hand.getValue());
			}

			// 发送请求
	        int respStatus = httpClient.executeMethod(httpMethod);
	        
	        // 获取结果
	        StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(
				new InputStreamReader(httpMethod.getResponseBodyAsStream(),charset));
			for(String line=null;(line=reader.readLine())!=null;){
				builder.append(line).append(SEPARATOR);
			}
	        
			resp.setRawData(builder.toString());
			resp.setStatusCode(respStatus);
	        
	        return (T) resp;
	        
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 初始化参数
	 * 属性可以通过以下方式设置亦可使用默认值
	 * eg:
	 * 	System.setProperty("http.max.total.connections", "120")
	 *  -Dhttp.max.total.connections=120
	 */
	private static void initParams() {
		try {
			// 每个主机允许连接数
			int defaultMaxConnectionsPerHost = Integer.parseInt(System.getProperty("http.max.connections.per.host", "10"));
			// 总连接数
			int maxTotalConnections = Integer.parseInt(System.getProperty("http.max.total.connections", "120"));
			// 连接超时(毫秒)
			int connectionTimeout = Integer.parseInt(System.getProperty("http.connection.timeout", "15000"));
			
			// 设置初始参数
			HttpConnectionManagerParams params = connectionManager.getParams();
			params.setDefaultMaxConnectionsPerHost(defaultMaxConnectionsPerHost);
			params.setMaxTotalConnections(maxTotalConnections);
			params.setConnectionTimeout(connectionTimeout);
			
			// 支持https
			Protocol httpsProtocol = new Protocol("https", new HttpsSecureProtocolSocketFactory(), 443);
            Protocol.registerProtocol("https", httpsProtocol);
           
		} catch (Exception e) {
			System.err.println(">> HttpInvoker fail to load properties :"+e.getMessage());
		}
	}
	
	
}
