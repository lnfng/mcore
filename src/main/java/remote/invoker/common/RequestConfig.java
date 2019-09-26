package remote.invoker.common;

import remote.invoker.http.HttpHandEnum;
import remote.invoker.http.HttpMethodEnum;

/**
 * 请求配置
 * @author Qian
 */
public class RequestConfig {
	/**
	 * 请求地址
	 */
	private String url;
	/**
	 * 主机名称
	 */
	private String host;
	/**
	 * 服务端口
	 */
	private Integer port;
	/**
	 * 读取超时(毫秒)
	 */
	private Integer readTimeOut;
	/**
	 * 读取超时默认:13秒
	 */
	private Integer defReadTimeOut = 13 * 1000;
	/**
	 * 字符集
	 */
	private String charset;
	/**
	 * 默认字符集UTF-8
	 */
	private String defCharset = "UTF-8";
	/**
	 * Http请求方法名称
	 */
	private HttpMethodEnum httpMethodName;
	/**
	 * 默认Http请求方法名称:POST
	 */
	private HttpMethodEnum defHttpMethod = HttpMethodEnum.POST;
	/**
	 * 常用请求头
	 */
	private HttpHandEnum httpHand;
	/**
	 * 常用请求头:TEXT_HAND
	 */
	private HttpHandEnum defHttpHand = HttpHandEnum.TEXT_HAND;
	/**
	 * 请求协议:http/https/tcp
	 */
	private String protocol;
	
	
	public RequestConfig() {}
	
	public RequestConfig(String url) {
		this.url = url;
	}
	
	
	public void setHttpMethodName(HttpMethodEnum httpMethod) {
		this.httpMethodName = httpMethod;
	}
	
	public HttpMethodEnum getHttpMethodName() {
		return httpMethodName == null ? defHttpMethod : httpMethodName;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 解析URL
	 */
	private void parseUrl() {
		String[] slots = url != null ? url.split("/") : new String[0];
		if (slots.length >= 2) {
			protocol = slots[0].replace(":", "");
			String[] infos = slots[2].split(":");
			host = infos[0];
			port = infos.length == 2 ? Integer.valueOf(infos[1]) : 80;
		}
	}

	public String getHost() {
		if (host == null) parseUrl();
		return host;
	}

	public Integer getPort() {
		if (port == null) parseUrl();
		return port;
	}

	public String getProtocol() {
		if (protocol == null) parseUrl();
		return protocol;
	}
	
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public HttpMethodEnum getDefHttpMethod() {
		return defHttpMethod;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public void setDefHttpMethod(HttpMethodEnum defHttpMethod) {
		this.defHttpMethod = defHttpMethod;
	}
	
	public String getCharset() {
		return charset == null ? defCharset : charset;
	}
	
	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getDefCharset() {
		return defCharset;
	}

	public HttpHandEnum getHttpHand() {
		return httpHand == null ? defHttpHand : httpHand;
	}

	public HttpHandEnum getDefHttpHand() {
		return defHttpHand;
	}
	public void setHttpHand(HttpHandEnum httpHand) {
		this.httpHand = httpHand;
	}

	public Integer getReadTimeOut() {
		return readTimeOut == null ? defReadTimeOut : readTimeOut;
	}

	public Integer getDefReadTimeOut() {
		return defReadTimeOut;
	}

	public void setReadTimeOut(Integer readTimeOut) {
		this.readTimeOut = readTimeOut;
	}
	
}
