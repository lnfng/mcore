package mcore.about_reflect;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ReflectArray {
	
	public static void main(String[] args) {
		int num=123;
		/**下面的语句是编译不过的，因为int类不是对象
		 * 不能获取到字节码
		 * System.out.println(num.getClass());
		 */
		
		int[] a1=new int[]{1,2,3};
		int[] a2=new int[4];
		int[][] a3=new int[2][3];
		String[] a4=new String[]{"a","b","c"};
		
		System.out.println(a1.getClass()==a2.getClass());
		/*编译不能通过了，因为类型不一样，不能进行等号比较
		 * System.out.println(a1.getClass()==a3.getClass());
		System.out.println(a1.getClass()==a4.getClass());*/
		
		System.out.println(a1.getClass().getSuperclass().getName());
		System.out.println(a4.getClass().getSuperclass().getName());
		
		//面向父类编程
		Object aObj1=a1;
		Object aObj2=a4;
		/**cannot convert from int[] to Object[]
		 * Object[] aObj3=a1;
		 */
		Object aObj4=a3;
		Object[] aObj5=a3;
		Object[] aObj6=a4;
		/**
		 * 通过Arrays 工具类转换成List再打印
		 * Arrays.asList(Object[] a) 1.4版
		 * Arrays.asList(T... a) 1.5版
		 * 下面打印结果是：
		 * [[I@9945ce]
		 * [a, b, c]
		 * 
		 * 首先它要兼容1.4版本，所以String[] 能被成功转换
		 * 但int[]不等于Object[]，而int[]属于Object，所以
		 * 它用1.5的处理方式，把int[] 当作一个对象转换成List 
		 */
		System.out.println(Arrays.asList(a1));
		System.out.println(Arrays.asList(a4));
		
		printObj(a1);
		printObj(a4);
		printObj(num);
		
	}

	private static void printObj(Object obj) {
		Class clazz=obj.getClass();
		if(clazz.isArray()){
			int len=Array.getLength(obj);
			for(int i=0;i<len;i++){
				Object ob=Array.get(obj, i);
				System.out.print(ob+" "+ob.getClass().getSimpleName()+"  ");
			}
			System.out.println();
		}else{
			System.out.println(obj+" "+clazz.getSimpleName());
		}
		
	}

}
