package mcore.about_classloader;

import java.util.Date;

public class ClassLoaderTest1 {

	/**java 虚拟机中可以安装多个类加载器，系统默认三个主要类加载器，每个类
	 * 负责加载特定位置的类：
	 * Bootstrap(由C++所写，是顶层类加载器)，ExtClassLoader,AppClassLoader
	 * 
	 * BootStrap----------->JRE/lib/rt.jar jre所指的是当前运行环境下的，java的核心包，如String，Integer
	 * ExtClassLoader------>JRE/lib/ext/*.jar 扩展包
	 * AppClassLoader------>ClASSPATH指定的所有jar或目录（System ClassLoader）
	 * 自上而下父--->子
	 * 
	 * 类加载器也是Java类，因为其他是Java类的类加载器本身也要被类加载器加载，
	 * 显然必须有第一个类加载器不是Java类，这正是Bootstrap。
	 * 
	 * Java虚拟机中的所有类装载器采用具有父子关系的树形结构进行组织，
	 * 在实例化每个类装载器对象时，需要为其指定一个父级类装载器对象或者
	 * 默认采用系统类装载器（AppClassLoader）为其父级类加载器。
	 */
	public static void main(String[] args) throws Exception {
		/**在没进行导出为jar文件时类加载器为sun.misc.Launcher$AppClassLoader@1bd7848
		 * 导出动作后类加载器为sun.misc.Launcher$ExtClassLoader@c4fe76*/
		System.out.println(
				ClassLoaderTest1.class.getClassLoader().getClass().getSimpleName());
		
		ClassLoader loader=ClassLoaderTest1.class.getClassLoader();
		while(loader!=null){
			System.out.println(loader.getClass().getName());
			//当加载器为Bootstrap返回值为null
			loader=loader.getParent();
		}
		
		/**拿到加密后的字节码（lib/ClassLoaderAttachment.class）
		 * 去覆盖调好的字节码（AboutClassLoader\bin\about_class_loader\ClassLoaderAttachment.class）
		 * 没覆盖之前能运行*/
		//System.out.println(new ClassLoaderAttachment().toString());
		
		Class clazz=
				new MyClassLoader("lib")
				//先从父加载器找，再回到发起者加载器里找
				.loadClass("about_class_loader.ClassLoaderAttachment");
		/*
		 * 为什么不用ClassLoaderAttachment接收呢？
		 * 因为ClassLoaderAttachment用AppClassLoader类加载器加载到的是加密后的
		 * 无解析，所以不能使用该引用。这也就是继承一个父类的原因。
		 * 
		 * 一个类的标志，真正来说是 包名+类名+类加载器
		 */
		
		
		//----------------------------------------------------------
		Date d=(Date) clazz.newInstance();
		Date d2=new Date();
		System.out.println();
		
		ClassLoader classLoader = d.getClass().getClassLoader();
		ClassLoader classLoader2 = d2.getClass().getClassLoader();
		
		System.out.println("the date classLoader is "+classLoader);
		System.out.println("the date2 classLoader is "+classLoader2);
		
		//----------------------------------------------------------
		/* above expression run the result is 
		 * the date classLoader is sun.misc.Launcher$AppClassLoader@7041825e
		 * the date2 classLoader is null
		 * 从上面的结果可以知道，通过强转得到的date类型是AppClassLoader 加载的
		 * 而date 的本应该是有BootStrap来加载的，说明类加载特定位置的字节码，并不是
		 * 很严格的说法，AppClassLoader 一样可以加载 Bootstrap管理目录下的字节码文件。
		 * 
		 */
		
		System.out.println();
		Object obj=clazz.newInstance();
		System.out.println(d.toString());
		System.out.println(obj);
		
		
		//---------------------------------------------------------------------------
		String str1=new String();
		//String str2=(String) new Object();
		Object object=new Object();
		System.out.println(str1.getClass().getClassLoader());
		//System.out.println(str2.getClass().getClassLoader());
		System.out.println(object.getClass().getClassLoader());
		
		//---------------------------------------------------------------------------
		/*强转的前提是两者之间必须是父类与子类的关系
		 * 一个类的标志，真正来说是 包名+类名+类加载器
		 * String str2=(String) new Object();
		 * 运行上面那句是会抛出java.lang.ClassCastException
		 * 上面的情况，我还有点解析不清
		 * 
		 * 还有种情况就是 前面引用部分加载的是一份字节码，
		 * 后面实际创建对象时又是另一份字节码，在Tomcat就
		 * 会经常出现这种情况，因为它打破了行父类委托机制
		 * 
		 * Fruit f;
		 *	Apple a = (Apple)f;
		 *
		 *	当出现下列情况时，就会引发ClassCastException异常：
		 *	
		 *	1. Fruit和Apple类不兼容。当应用程序代码尝试将某一对象转换为某一子类时，如果该对象并非该子类的实例，
		 *     JVM就会抛出ClassCastException异常。
		 *	2. Fruit和Apple类兼容，但加载时使用了不同的ClassLoader。这是这种异常发生最常见的原因。
		 * 
		 * 由不同ClassLoader加载的同一类文件也会被视为不同的类，即便每个字节都完全相同。
		 * 这是ClassCastException的一个典型原因。
		 * 
		 * 强转时虚拟机都进行了什么动作呢？
		 */
		
		
		
		
		
		
		
		
		/**类加载器的委托机制
		 * 
		 * 当Java虚拟机要加载一个类时，到底派出哪个类加载器去加载呢？
		 * 1.如果类A中引用了类B，，Java虚拟机降使用加载类A的类加载器去装载类B
		 * 2.还可以直接调用ClassLoader.loadClass()方法进行加载某个类
		 * 每个类加载器加载类时，又先委托给它的上级类加载器。
		 * 1.当所有祖宗类加载器都没有加载到类，回到发起者的类加载器，还加载
		 * 	不了，则抛出ClassNotFoundException，不是再去找发起者类加载器的儿子
		 * 	例如：tomcat中有自定义的类加载器去加载HttpServlet类，当我们把自己定义的
		 * 	的MyServlet类导出到jre/lib/ext/下的话，加载MyServlet的类加载器是ExtClassLoader
		 * 	但MyServlet还继承了HttpServlet，而虚拟机仍派ExtClassLoader去加载HttpServlet，
		 * 	此时HttpServlet并不存在于jre/lib/ext/下，故而抛出ClassNotFoundException，
		 * 	若把tomcat下的HttpServlet.jar复制到jre/lib/ext/，则可以运行通过了。
		 * 
		 * 2.对着类加载器的层次结构图和委托加载原理，解释先前将ClassLoaderTest1输出
		 * 	到jre/lib/ext目录下的classloader_test包中后，运行结果为ExtClassLoader的原因
		 * 
		帮助文档描述：
		
		public abstract class ClassLoaderextends Object类加载器是负责加载类的对象。
		ClassLoader 类是一个抽象类。如果给定类的二进制名称，那么类加载器会试图查找或生成构成类定义的数据。
		一般策略是将名称转换为某个文件名，然后从文件系统读取该名称的“类文件”。 
		
		每个 Class 对象都包含一个对定义它的 ClassLoader 的引用。 
		数组类的 Class 对象不是由类加载器创建的，而是由 Java 运行时根据需要自动创建。
		数组类的类加载器由 Class.getClassLoader() 返回，该加载器与其元素类型的类加载器是相同的；
		如果该元素类型是基本类型，则该数组类没有类加载器。 
		
		应用程序需要实现 ClassLoader 的子类，以扩展 Java 虚拟机动态加载类的方式。
		类加载器通常由安全管理器使用，用于指示安全域。 
		
		ClassLoader 类使用委托模型来搜索类和资源。每个 ClassLoader 实例都有一个相关的父类加载器。
		需要查找类或资源时，ClassLoader 实例会在试图亲自查找类或资源之前，
		将搜索类或资源的任务委托给其父类加载器。
		虚拟机的内置类加载器（称为 "bootstrap class loader"）本身没有父类加载器，
		但是可以将它用作 ClassLoader 实例的父类加载器。 
		
		通常情况下，Java 虚拟机以与平台有关的方式，从本地文件系统中加载类。
		例如，在 UNIX 系统中，虚拟机从 CLASSPATH 环境变量定义的目录中加载类。 
		
		然而，有些类可能并非源自一个文件；它们可能源自其他来源（如网络），
		也可能是由应用程序构造的。defineClass 方法将一个 byte 数组转换为 Class 类的实例。
		这种新定义的类的实例可以使用 Class.newInstance 来创建。 
		
		类加载器所创建对象的方法和构造方法可以引用其他类。为了确定引用的类，
		Java 虚拟机将调用最初创建该类的类加载器的 loadClass 方法。 
		
		例如，应用程序可以创建一个网络类加载器，从服务器中下载类文件。示例代码如下所示： 
		
		   ClassLoader loader = new NetworkClassLoader(host, port);
		   Object main = loader.loadClass("Main", true).newInstance();
		          . . .
		 网络类加载器子类必须定义方法 findClass 和 loadClassData，以实现从网络加载类。
		 下载组成该类的字节后，它应该使用方法 defineClass 来创建类实例。示例实现如下： 
		
		     class NetworkClassLoader extends ClassLoader {
		         String host;
		         int port;
		
		         public Class findClass(String name) {
		             byte[] b = loadClassData(name);
		             return defineClass(name, b, 0, b.length);
		         }
		
		         private byte[] loadClassData(String name) {
		             // load the class data from the connection
		              . . .
		         }
		     }
		 二进制名称 
		按照《Java Language Specification》的定义，任何作为 String 
		类型参数传递给 ClassLoader 中方法的类名称都必须是一个二进制名称。 
		
		有效类名称的示例包括： 
		
		   "java.lang.String"
		   "javax.swing.JSpinner$DefaultEditor"
		   "java.security.KeyStore$Builder$FileBuilder$1"
		   "java.net.URLClassLoader$3$1"
		 
		
		从以下版本开始： 
		1.0 
		*/
		
		
	}
}
