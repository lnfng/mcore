package mcore.about_memory;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args: -XX:PermSize=1m -XX:MaxPermsize=1m
 * 运行时常量池溢出
 * @author JackQ
 */
public class RuntimeConstantPoolOOM {

	public static void main(String[] args) {
			
		//使用list 来保持常量池的引用，避免Full GC 
		List<String> list=new ArrayList<>();
		int i=0;
		for(;;){
			//10m Permsize 在Integer 的范围内足够产生OOM
			list.add(String.valueOf(i++).intern());
			System.out.println(i);
		}
	}
	/*结果有点意外，是堆溢出，并不是PermGen Space 溢出
		java.lang.OutOfMemoryError: Java heap space
		Dumping heap to java_pid3300.hprof ...
		Heap dump file created [818437669 bytes in 11.749 secs]
		Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
			at java.util.Arrays.copyOfRange(Arrays.java:2694)
			at java.lang.String.<init>(String.java:234)
			at java.lang.StringBuilder.toString(StringBuilder.java:405)
			at out_of_memory.RuntimeConstantPoolOOM.main(RuntimeConstantPoolOOM.java:25)
			
			
			
		second:
		java.lang.OutOfMemoryError: PermGen space
		Dumping heap to java_pid6588.hprof ...
		Heap dump file created [1010497 bytes in 0.132 secs]
		Error occurred during initialization of VM
		java.lang.OutOfMemoryError: PermGen space
			<<no stack trace available>>
		
		Exception in thread "Reference Handler" 
			
	*/
}
