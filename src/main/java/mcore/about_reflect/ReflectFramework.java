package mcore.about_reflect;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Properties;


public class ReflectFramework {
	
	public static void main(String[] args) throws Exception{
		//把要使用的集合，延迟到配置上
//		List collection=new ArrayList<>();
		
		//这种方式不够灵活
//		InputStream ips=new FileInputStream("src//about_reflect//config.properties");
		
		//通过类加载器获得源文件这种方式是比较常用的，相对于字节码的位置
//		InputStream ips=ReflectFramework.class.getClassLoader().getResourceAsStream("about_reflect//config.properties");
		//该方法内部仍调用上面的方法，只不过是简化了一些操作，是相对于当前类包而言的
		InputStream ips=ReflectFramework.class.getResourceAsStream("config.properties");
		
		//内部使用了Hashtable<Object,Object>
		Properties prop=new Properties();
		prop.load(ips);
		String className=prop.getProperty("className");
		
		Collection collection=(Collection) Class.forName(className).newInstance();
		
		collection.add(101);
		collection.add(102);
		collection.add(103);
		collection.add(101);
		
		System.out.println(collection);
		
		URL url=ReflectFramework.class.getResource("config.properties");
		String path=url.getPath();
		String file=url.getFile();
		String prot=url.getProtocol();
		System.out.println(path);
		System.out.println(file);
		System.out.println(prot);
	}
}
