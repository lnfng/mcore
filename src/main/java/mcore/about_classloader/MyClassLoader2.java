package mcore.about_classloader;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;

//delegation model 父类委派机制
public class MyClassLoader2 extends ClassLoader{
	
	private String classDir;

	public MyClassLoader2(){		
	}
	public MyClassLoader2(String classDir){
		//super(null);//置空父类加载器,则为bootstrap 加载了
		this.classDir=classDir;
	}
	
	

	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String separator = System.getProperty("file.separator");
		String classFileName=classDir+separator+name.substring(name.lastIndexOf('.')+1)+".class";
		try {
			@SuppressWarnings("resource")
			FileInputStream fis=new FileInputStream(classFileName);
			//写到内存中，目标是内存
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			
			//解密字节码
			byte[] buf=new byte[1024*100];
			for(int len = 0;(len=fis.read(buf))!=-1;){
				bos.write(buf, 0, len);
			}
			
			byte[] bytes=bos.toByteArray();
			System.out.println("MyClassLoader loader this class:"+name);
		    return defineClass(name,bytes, 0, bytes.length);
		} catch (Exception e) {
			e.printStackTrace();
		}//AutoClosable
		
		return super.findClass(name);
	}
	
	/**is not ok!
	 * 我们发现，如果我们重写loadClass则会破坏双亲委派模型。
	 */
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		/*synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();
                Q: I don't need parent delegation model!
                try {
                    if (parent != null) {
                        c = parent.loadClass(name, false);
                    } else {
                        c = findBootstrapClassOrNull(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }

                if (c == null) {
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();
                    c = findClass(name);

                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            return c;
		}*/
		return super.loadClass(name);
	}
	
	/**
	 * 复写查找资源的
	 */
	@Override
	protected URL findResource(String name) {
		if(name==null) 
			throw new IllegalArgumentException("file name is required!");
		String path = name;
		if(!path.contains(":"))
			path = classDir +System.getProperty("file.separator")+ name;
		try {
			return new File(path).toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	@Override
	protected String findLibrary(String libname) {
		// TODO Auto-generated method stub
		return super.findLibrary(libname);
	}
	@Override
	protected Enumeration<URL> findResources(String name) throws IOException {
		// TODO Auto-generated method stub
		return super.findResources(name);
	}
}
