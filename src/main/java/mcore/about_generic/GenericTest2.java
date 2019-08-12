package mcore.about_generic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

public class GenericTest2 {
	
	/**使用？通配符可以引用任何参数化的类型，问号通配符定义的变量主要作用引用，
	 * 可以调用与参数化无关的方法，不能调用与参数化有关的方法
	 * 限定通配符总是包括自己
	 * 
	 * 声明时的扩展：<T extends Number>中间还可以加 & 的逻辑符，
	 * 且&后面只可以用“接口类型” <T extends Number & Serializable>
	 * 加逻辑与“&”只能在声明的时候使用，不能在作为引用的时候使用，并且不能使用“?”，只能使用单词字符；
	 * 而“?”是作为引用的时候使用的，不能使用单词字符声明。
	 * 例如：
	 * public class PlainTest< T extends BaseSupport & Serializable>{} ;
	 *  List<? extends PlainTest> tlist = new ArrayList();
	 * 
	 * 	限定通配符的上边界（定义接收的最大类型是Number）
	 * 		正确：Vector<? extends Number> v=new Vector<Integer>();
	 * 		错误：Vector<? extends Number> v=new Vector<String>();
	 * 		说明：接收的类型是Number或者 它的子类
	 * 
	 * 	限定通配符的下边界（接收的最小类型是Integer，即使跟Integer同级也不能接收）
	 * 		正确：Vector<? super Integer> v=new Vector<Number>();
	 * 		错误：Vector<? super Integer> v=new Vector<Byte>();
	 * 		说明：接收的类型是Integer或者 它的父类
	 */
	public static void main(String[] args) {
		Collection<String> c=new ArrayList<String>();
		
		c.add("abc");
		c.add("efg");
		
		printCollection(c);
		
		Vector<? extends Number> v1=new Vector<Integer>();
		Vector<? super Integer> v2=new Vector<Number>();
		/**与参数化有关的方法，编译不通过
		 * 因为实际类型参数不确定*/
		//v1.add(1);
		
		Integer s1=add(1,2);
		/**<? extends Number> ,使用最大公约数*/
		Number s2=add(3.5,12);
		/**<? extends Object> ,使用最大公约数*/
		Object s3=add(12,"abc");
		
		swap(new String[]{"123","456","789"}, 1, 3);
		/**E[] 只能接收对象数组，而int[] 不是对象数组*/
		//swap(new int[]{123,456,789}, 1, 3);
		
	}
	
	
	public static void printCollection(Collection<?> c){
		/**与参数化有关的方法，编译不通过
		 * 因为实际类型参数不确定
		 */
//		c.add("1112");
		System.out.println(c.size());
		for(Object o:c){
			System.out.println(o);
		}
	}
	
	public static <T> T add(T a,T b){
		//因为不是所有类型都能进行加法，这只是演示说明
		return null;
	}
	
	public static <E> void swap(E[] arr,int p1,int p2){
		p1=p1-1;p2=p2-1;
		E temp=arr[p1];
		arr[p1]=arr[p2];
		arr[p2]=temp;
	}

}

