package mcore.about_net;

import java.util.HashMap;
import java.util.Map;

public class HttpClient {
	
	private String url;
	private Map<String,String> params = new HashMap<String, String>();
	
	/**
	 * 构造该链接的实例
	 * @param url 目标链接
	 */
	public HttpClient(String url){
		this.url=url;
	}
	
	/**
	 * doPost 方法
	 * @author Qian
	 * @return
	 */
	public String doPost(){
		String result = "";
		
		return result ;
	}
	
	/**
	 * doGet 方法
	 * @author Qian
	 * @return
	 */
	public String doGet(){
		String result = "";
		
		return result;
	}
	
	/**
	 * 添加键值的参数对应
	 * @author Qian
	 * @param name
	 * @param val
	 */
	public void addParam(String name,String val){
		
	}
	
	/**
	 * 以map方式封装传递参数
	 * @author Qian
	 * @param params
	 */
	public void addParam(Map<String,String> params){
		
	}
	
	/**
	 * 通过封装的实体添加参数
	 * @author Qian
	 * @param paramObj 封装的实体
	 */
	public void addParam(Object paramObj){
		
	}

}
