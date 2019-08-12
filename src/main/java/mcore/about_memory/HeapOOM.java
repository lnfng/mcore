package mcore.about_memory;

import java.util.*;

/**
 *堆内存溢出
 * VM Args: -Xmx20m -Xms20m -XX:+HeapDumpOnOutOfMemoryError
 * @author JackQ
 */
public class HeapOOM {
	
	static class ObjectOOM{
	}
	
	public static void main(String[] args) {
		List<ObjectOOM> list=new ArrayList<ObjectOOM>();
		
		int objCount=0;
		try {
			for(;objCount>=0;objCount++){
				list.add(new ObjectOOM());
			}
		} catch (Throwable e) {
			System.out.println(objCount);
			e.printStackTrace();
		}
		
	}
	/*the info 
	 
		java.lang.OutOfMemoryError: Java heap space
		Dumping heap to java_pid43296.hprof ...
		Heap dump file created [27844512 bytes in 0.124 secs]
		810325
		java.lang.OutOfMemoryError: Java heap space
			at java.util.Arrays.copyOf(Arrays.java:2245)
			at java.util.Arrays.copyOf(Arrays.java:2219)
			at java.util.ArrayList.grow(ArrayList.java:213)
			at java.util.ArrayList.ensureCapacityInternal(ArrayList.java:187)
			at java.util.ArrayList.add(ArrayList.java:411)
			at out_of_memory.HeapOOM.main(HeapOOM.java:20)
	*/
	
}