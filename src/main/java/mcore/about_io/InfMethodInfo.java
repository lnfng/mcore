package mcore.about_io;

import java.util.*;

/**
 * 接口方法信息
 * @author Qian
 */
public class InfMethodInfo{
	
	private String comment;        // 方法描述文字,注释文字
	private String methodSign;     // 方法签名
	private String reqClassName;   // 请求类全量类名
	private String respClassName;  // 响应类全量类名
	private String request;        // 请求类名
	private String respond;        // 响应类名
	private String opCode;         // 请求编码
	private String methodCode;     // dubbo 方法编码
	private String url;			   // 请求地址
	private String invoker;		   // 调用类
	private String epID;		   // 配置表ID
	private List<String> tempNames;// 模板名列表,有默认值getDefTempNames
	
	
	/**
	 * 生成Map
	 * @return
	 */
	public Map<String,String> toMap(){
		Map<String,String> map = new HashMap();
		map.put("OP_CODE", opCode);
		map.put("COMMENT", comment);
		map.put("REQUEST", request);
		map.put("RESPOND", respond);
		map.put("METHOD_CODE", methodCode);
		map.put("METHOD_SIGN", methodSign);
		map.put("REQCLASSNAME", reqClassName);
		map.put("RESPCLASSNAME", respClassName);
		map.put("INVOKER", invoker);
		map.put("EP_ID", epID);
		map.put("URL", url);
		return map;
	}
	
	

	/**
	 * 请求类全量类名
	 * @param reqClassName
	 */
	public void setReqClassName(String reqClassName) {
		try {
			Class<?> clazz = Class.forName(reqClassName);
			this.methodCode = (String) clazz
				.getMethod("getApiMethodName").invoke(clazz.newInstance());
			this.request = clazz.getSimpleName();
			this.reqClassName = reqClassName;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 响应全量类名
	 * @param reqClassName
	 */
	public void setRespClassName(String respClassName) {
		try {
			this.respond = Class.forName(respClassName).getSimpleName();
			this.respClassName = respClassName;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 注释及说明
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * 方法签名
	 * @param methodSign
	 */
	public void setMethodSign(String methodSign) {
		this.methodSign = methodSign;
	}
	/**
	 * dubbo 方法编码
	 * @param methodCode
	 */
	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}
	/**
	 * 请求编码
	 * @param opCode
	 */
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	
	/**
	 * 返回默认的模板名称
	 * @return
	 */
	public List<String> getDefTempNames(){
		return new ArrayList(
			Arrays.asList("inf-open-imp","inf-imp","inf-face",
				"inf-endpoint","inf-operation","inf-request","inf-response"));
	}
	public String getInvoker() {
		return invoker;
	}
	public String getEpID() {
		return epID;
	}
	public void setInvoker(String invoker) {
		this.invoker = invoker;
	}
	public void setEpID(String epID) {
		this.epID = epID;
	}
	public List<String> getTempNames() {
		return tempNames;
	}
	public void setTempNames(List<String> tempNames) {
		this.tempNames = tempNames;
	}
	public String getComment() {
		return comment;
	}
	public String getMethodSign() {
		return methodSign;
	}
	public String getReqClassName() {
		return reqClassName;
	}
	public String getRespClassName() {
		return respClassName;
	}
	public String getRequest() {
		return request;
	}
	public String getRespond() {
		return respond;
	}
	public String getOpCode() {
		return opCode;
	}
	public String getMethodCode() {
		return methodCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return this.toMap().toString();
	}
	
}