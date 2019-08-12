package mcore.about_reflect.reflectasm_test;

import com.esotericsoftware.reflectasm.MethodAccess;
import java.lang.reflect.Method;

/**
 * 参考地址: https://unmi.cc/java-reflectasm-bytecode-usage
 */
public class ReflectasmClient01 {
 
    public static void main(String[] args) throws Exception {
    	System.out.println(">> start...");
//        testJdkReflect01();
//        testReflectAsm01();
        testJdkReflect02();
        testReflectAsm02();
    }
    
    public static void testJdkReflect01() throws Exception {
        SomeClass someObject = new SomeClass();        
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < 100000000; j++) {
                Method method = SomeClass.class.getMethod("foo", String.class);
                method.invoke(someObject, "Unmi");
            }
            System.out.print(System.currentTimeMillis() - begin +" ");
        }
    }
 
    public static void testReflectAsm01() {
        SomeClass someObject = new SomeClass();
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < 100000000; j++) {
                MethodAccess access = MethodAccess.get(SomeClass.class);
                access.invoke(someObject, "foo", "Unmi");
            }
            System.out.print(System.currentTimeMillis() - begin + " ");
        }
    }
    
    /**
           分别运行 testJdkReflect() 和 testReflectAsm 得出各自的运行时间数据，如下：
	
	运行 testJdkReflect():  31473 31663 31578 31658 31552
	
	运行 testReflectAsm(): 312814 310666 312867 311234 311792
	
	这个数据是非常恐怖的，似乎在带领我们往相反的方向上走，用 ReflectASM 怎么反而耗时多的多，高一个数量级，为什么呢？原因是大部分的时间都耗费在了
	
	MethodAccess access = MethodAccess.get(SomeClass.class);
	
	上，正是生成字节码的环节上，也让你体验到 MethodAccess 是个无比耗时的操作，如果把这行放到循环之外会是什么样的结果呢，同时也把方法 testJdkReflect() 中的
	
	Method method = SomeClass.class.getMethod("foo", String.class);
	
	也提出去，改变后的 testJdkReflect() 和 testReflectAsm() 分别如下：
    
    */
    
    
    public static void testJdkReflect02() throws Exception {
        SomeClass someObject = new SomeClass();        
        Method method = SomeClass.class.getMethod("foo", String.class);
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < 100000000; j++) {
                method.invoke(someObject, "Unmi");
            }
            System.out.print(System.currentTimeMillis() - begin +" ");
        }
    }
 
    public static void testReflectAsm02() {
        SomeClass someObject = new SomeClass();
        MethodAccess access = MethodAccess.get(SomeClass.class);
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < 100000000; j++) {        
                access.invoke(someObject, "foo", "Unmi");
            }
            System.out.print(System.currentTimeMillis() - begin + " ");
        }
    }
    
    /**
     再次分别跑下 testJdkReflect() 和  testReflectAsm()，新的结果如下：

	运行 testJdkReflect():  1682 1696 1858 1774 1780       ------ 平均  1758
	
	运行 testReflectAsm(): 327 549 520 509 514                ------ 平均 483.8
	
	胜负十分明显，上面的实验两相一比较，用 ReflectAsm 进行方法调用节省时间是 72.48％
	
	也因此可以得到使用 ReflectASM 时需特别注意的是，获得类似 MethodAccess 实例只做一次，或它的实例应缓存起来，才是真正用好 ReflectASM。
	
	进一步深入的话，不妨看看分别从 testJdkReflect()/testReflectAsm() 到 SomeClass.foo() 过程中到底发生了什么，断点看调用栈。
	
	testJdkReflect() 到 SomeClass.foo() 的调用栈：
     
     	
     
    ReflectASM 反射调用方法:
		SomeClass someObject = ...
		MethodAccess access = MethodAccess.get(SomeClass.class);
		access.invoke(someObject, "setName", "Awesome McLovin");
		String name = (String)access.invoke(someObject, "getName");
	
	用 ReflectASM 反射来 set/get 字段值:
		SomeClass someObject = ...
		FieldAccess access = FieldAccess.get(SomeClass.class);
		access.set(someObject, "name", "Awesome McLovin");
		String name = (String)access.get(someObject, "name");
	
	用 ReflectASM 反射来调用构造方法:
		ConstructorAccess<SomeClass> access = ConstructorAccess.get(SomeClass.class);
		SomeClass someObject = access.newInstance();

	避免用方法名来查找

		为了在重复性的反射来访问方法或字段时最大化性能，应该用方法和字段的索引来定位而不是名称：

		SomeClass someObject = ...
		MethodAccess access = MethodAccess.get(SomeClass.class);
		int addNameIndex = access.getIndex("addName");
		for (String name : names)
		    access.invoke(someObject, addNameIndex, "Awesome McLovin");
	
	说到这，不妨再次来验证一下，把 testReflectAsm() 方法改为如下：

    public static void testReflectAsm() {
        SomeClass someObject = new SomeClass();
        MethodAccess access = MethodAccess.get(SomeClass.class);
        int fooIndex = access.getIndex("foo", String.class);
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < 100000000; j++) {        
                access.invoke(someObject, fooIndex, "Unmi");
            }
            System.out.print(System.currentTimeMillis() - begin + " ");
        }
    }
	运行的输出结果是，你可能想像不到的：
	
	206 182 171 175 171

	而用名称查找方法时的测试数据为：327 549 520 509 514

	当然你调用的重复性应该带有一点夸张性质的。性能更优化的原因是用名称来查找最科要被转换成索引来查找。

	可见性

	ReflectASM 总是能访问公有成员的. 它会尝试在同一个 package 中去定义访问类的，并且同一个类加载器去加载。所以，如果安全管理器允许 setAccessible 调用成功的话，protected 或包私有(package private) 的成员也可被访问到. 假如 setAccessible 失败，仅当当有公有成员可被访问时，不会有异常抛出. 私有成员总是无法访问到。

	
	有关异常

		当使用 ReflectASM 有异常时，栈跟踪更清淅了。这是 Java 在反射调用方法时抛出了一个 RuntimeException 异常：

			Exception in thread "main" java.lang.reflect.InvocationTargetException
			        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
			        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
			        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
			        at java.lang.reflect.Method.invoke(Method.java:597)
			        at com.example.SomeCallingCode.doit(SomeCallingCode.java:22)
			Caused by: java.lang.RuntimeException
			        at com.example.SomeClass.someMethod(SomeClass.java:48)
			        ... 5 more

	再看用 ReflectASM 时抛出的同样的异常:

		Exception in thread "main" java.lang.RuntimeException
		        at com.example.SomeClass.someMethod(SomeClass.java:48)
		        at com.example.SomeClassMethodAccess.invoke(Unknown Source)
		        at com.example.SomeCallingCode.doit(SomeCallingCode.java:22)
	如果被 ReflectASM 调用的代码抛出了需检测的异常，也需要抛出需检测异常. 
	因为如果你在用 try/catch 捕获块中未声明抛出的具体类型的异常时会报编译错误。
	（Unmi 注：这句话的意思是说，比如方法 foo() 未声明抛出 IOException，
	而你 try 它时却 catch(IOException) 就会出现编译错误）所以当你在用 ReflectASM 反射调用，
	并需要关心其中抛出的异常时，你必须捕获的异常类型是 Exception。

*/
    
    
}