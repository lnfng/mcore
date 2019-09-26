package remote.invoker;

import java.io.Serializable;

/**
 * 响应类
 * @author Qian
 */
public abstract class Uresponse implements Serializable {
	private static final long serialVersionUID = 50402L;
	
	/**
	 * 状态编码
	 */
	private int statusCode;
	
	/**
	 * 原生数据(未经处理)
	 */
	private Object rawData;
	
	/**
	 * 解析后的数据
	 */
	private Object parsedData;

	/**
	 * 处理原生数据
	 */
	protected abstract Object dealRawData();
	
	/**
	 * 是否处理成功
	 */
	public abstract boolean isSucc();

	/**
	 * 获取解析后的数据
	 * 即实现dealRawData返回的数据
	 */
	public Object getParsedData() {
		if (parsedData == null) parsedData = dealRawData();
		return parsedData;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public Object getRawData() {
		return rawData;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public void setRawData(Object rawData) {
		this.rawData = rawData;
	}
	
}
