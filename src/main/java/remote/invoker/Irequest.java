package remote.invoker;

import java.io.Serializable;

import remote.invoker.common.RequestConfig;

/**
 * 请求类
 * @author Qian
 */
public abstract class Irequest implements Serializable {
	private static final long serialVersionUID = 50402L;
	
	/**
	 * 请求配置
	 */
	private RequestConfig config;
	/**
	 * 生成请求报文
	 */
	public abstract String generateReqStr();
	
	public void setConfig(RequestConfig config) {
		this.config = config;
	}
	
	public RequestConfig getConfig() {
		return config;
	}
}
