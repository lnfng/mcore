package mcore.about_classloader;

import java.io.*;

public class MyClassLoader extends ClassLoader{
	
	private String classDir;

	public MyClassLoader(){		
	}
	public MyClassLoader(String classDir){
		this.classDir=classDir;
	}

	//main方法仅用于加密自定的ClassLoaderAttachment
	public static void main(String[] args) throws Exception{
		/**有包名的类不能调用无包名的类*/
		String srcPath=args[0];
		String destDir=args[1];
		FileInputStream fis=new FileInputStream(srcPath);
		String destFileName=srcPath.substring(srcPath.lastIndexOf('\\')+1);
		String destPath=destDir+"\\"+destFileName;
		FileOutputStream fos=new FileOutputStream(destPath);
		
		cypher(fis,fos);
		fis.close();
		fos.close();
	}
	
	//微加密类
	public static void cypher(InputStream ips,OutputStream ops) throws Exception{
		int b=-1;
		while((b=ips.read())!=-1){
			//通过亦或来加密
			ops.write(b ^ 0xff);
		}
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String classFileName=classDir+"\\"+name.substring(name.lastIndexOf('.')+1)+".class";
		try {
			FileInputStream fis=new FileInputStream(classFileName);
			//写到内存中，目标是内存
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			//解密字节码
			cypher(fis, bos);
			byte[] bytes=bos.toByteArray();
			System.out.println();
			System.out.println("MyClassLoader loader this class!!!");
			System.out.println();
		    return defineClass(name,bytes, 0, bytes.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.findClass(name);
	}
	
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return this.loadClass(name,false);
	}
	
	@Override
	protected Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		
		return super.loadClass(name, resolve);
		/*注释中是ClassLoader中的原码，可以将此覆盖掉后，自定我加载的类，不寻找父类*/
		/*synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();
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
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }*/
	}
	
}
