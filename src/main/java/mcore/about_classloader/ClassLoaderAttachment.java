package mcore.about_classloader;

import java.util.Date;

/**继承date类是有目的*/
public class ClassLoaderAttachment extends Date {

	@Override
	public String toString() {
		return "hello,classLoader!";
	}
}
