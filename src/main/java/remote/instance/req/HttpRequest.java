package remote.instance.req;

import remote.invoker.Irequest;

/**
 * 请求类
 * @author Qian
 */
public class HttpRequest extends Irequest {
	private static final long serialVersionUID = 1L;
	// 请求参数
	private String reqStr;

	@Override
	public String generateReqStr() {
		return reqStr;
	}
	
	public void setReqStr(String reqStr) {
		this.reqStr = reqStr;
	}
	public String getReqStr() {
		return reqStr;
	}
}