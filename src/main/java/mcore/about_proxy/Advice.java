package mcore.about_proxy;

import java.lang.reflect.Method;
/**
 * 这是一个契约
 * @author JackQ
 *
 */
public interface Advice {
	/**一般有四个方法，还有处理异常等，可参考Spring*/
	void beforeMethod(Method method);
	void afterMethod(Method method);

}
