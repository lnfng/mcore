package remote.invoker;


/**
 * 远程调用类
 * @author Qian
 */
public interface Invoker {

	/**
	 * 执行请求
	 * @param req 请求类
	 * @return 响应类
	 */
	<T extends Uresponse> T execute(Irequest req, Class<T> respType);
	
}
