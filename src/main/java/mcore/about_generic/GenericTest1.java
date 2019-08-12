package mcore.about_generic;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

public class GenericTest1 {
	/**
	 * 泛型是提供给javac编译器使用的，可以限定集合中的输入类型，让编译器
	 * 挡住源程序中的非法输入，编译器编译带类型说明的集合时会去掉<类型>信息
	 * 使程序运行效率不受影响，对于参数化的类型，getClass（）的返回值与原始
	 * 类型的返回值是一样的。
	 * 
	 * 由于编译器生成的字节码会去掉泛型的类型信息，只要调过编译器，就可以往某个
	 * 泛型集合里加入其他类型的数据。
	 * 例如：通过反射得到的集合，再调用其add方法即可。
	 */
	public static void main(String[] args) throws NoSuchMethodException, Exception {
		ArrayList<String> con1 = new ArrayList<String>();
		con1.add("abc");
		
		ArrayList<Integer> colletion2 = new ArrayList<Integer>();
//		colletion2.add("abc");
		colletion2.add(123);
		
		/**the result is true*/
		System.out.println(con1.getClass()==colletion2.getClass());
		
		/**use the reflection*/
		Method addMethod=con1.getClass().getMethod("add", Object.class);
		addMethod.invoke(colletion2, "isOkay");
		
		/**the result is [123, isOkay]*/
		System.out.println(colletion2);
		
		/**ArrayList<E> 类定义和ArrayList<Integer> 类中涉及的术语：
		 * 整个ArrayList<E>称为泛型类型
		 * 整个ArrayList<Integer>参数化的类型
		 * ArrayList<Integer>中的Integer称为类型参数的实例或实际类型参数
		 * ArrayList<Integer>中的<> 念typeof
		 * ArrayList称为原始类型
		 * 
		 * 参数化类型于原始类型兼容性：
		 * Collection<String> c1=new ArrayList();
		 * Collection c2=new ArrayList<String>();
		 * c1只能add String 类型的数据，因为限定在引用上
		 * c2可以add Object 类型的数据
		 * 
		 * 参数化类型不考虑类型的继承关系
		 * ArrayList<String> v=new ArrayList<Object>();//错误的
		 * ArrayList<Object> v=new ArrayList<String>();//也错的
		 * 在创建数组实例时，数组元素不能使用参数化的类型
		 * Vector<Integer>[] v=new Vector<Integer>[10];//错的
		 * Vector[] v=new Vector[10];//这是可以的
		 * 
		 * 思考题：下面代码会报错吗
		 * Vector v1=new Vector<String>();
		 * Vector<Object> v=v1;
		 * 答：不会报错，因为参数化类型于原始类型兼容性。
		 */
		Collection<String> c1=new ArrayList();
		Collection c2=new ArrayList<String>();
		
//		c1.add(123);
		c1.add("good");
		
		c2.add(123);
		c2.add("good");
		
		System.out.println(c1);
		System.out.println(c2);
		
		
	//==============================================================
		//参数化类型于原始类型兼容性
		Vector<String>[] v=new Vector[10];
		
		v[0] = new Vector<String>();
		v[0].add(new String("Hello Girl!"));
		
		System.out.println(v[0].get(0));
		
		Vector v1=new Vector<String>();
		Vector<Object> v2=v1;
		v2.add("123456");
		System.out.println(v2.get(0));
		
		//不能创建泛型化的数据对象
		//Vector<Integer>[] v=new Vector<Integer>[10];//错的
	}

}



