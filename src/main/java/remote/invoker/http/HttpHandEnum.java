package remote.invoker.http;

import java.util.HashMap;
import java.util.Map;

/**
 * 常用的请求头参数
 * @author Qian
 */
public enum HttpHandEnum {

	/**
	 * text/*
	 */
	TEXT_HAND,
	/**
	 * application/x-www-form-urlencoded
	 */
	URL_ENCODED_HAND,
	/**
	 * application/json
	 */
	JSON_HAND, 
	
	/**
	 * text/xml
	 */
	XML_HAND;
	
	/**
	 * 获取头部参数
	 * @return
	 */
	public Map<String, String> getHandParams(String charset) {
		Map<String, String> params = new HashMap();
		
		if (this.equals(URL_ENCODED_HAND)) {
			params.put("Content-type", "application/x-www-form-urlencoded;charset=" + charset);
			
		} else if (this.equals(JSON_HAND)) {
			params.put("Content-type", "application/json;charset=" + charset);
			
		} else if (this.equals(TEXT_HAND)) {
			params.put("Content-type", "text/*;charset=" + charset);
			
		} else if (this.equals(XML_HAND)) {
			// 包含Webservice的
			params.put("SOAPAction", "");
			params.put("Content-type", "text/xml;charset=" + charset);
		}
		
		return params;
	}
	
}
