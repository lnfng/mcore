package mcore.about_annotation.advance;

import java.lang.reflect.Method;
import mcore.about_annotation.MetaAnnotation;
/**
 	READ ME:
 
	 子类可以继承到父类上的注解吗？
	-----------------------------------------------------------------
	我们知道在编写自定义注解时，可以通过指定@Inherited注解，指明自定义注解是否可以被继承。但实现情况又可细分为多种。
	 
	 
	测试环境如下：
	-----------------------------------------------------------------
	父类的类上和方法上有自定义的注解--MyAnnotation
	子类继承了这个父类，分别：
	子类方法，实现了父类上的抽象方法
	子类方法，继承了父类上的方法
	子类方法，覆盖了父类上的方法

	编写自定义注解时未写@Inherited的运行结果
	-----------------------------------------------------------------
	子类没有继承到父类类上Annotation
	子类实现父类的abstractMethod抽象方法，没有继承到父类抽象方法中的Annotation
	子类继承父类的doExtends方法，继承到父类doExtends方法中的Annotation,其信息如下：父类的doExtends方法
	子类覆盖父类的doHandle方法，没有继承到父类doHandle方法中的Annotation
	 
	 
	编写自定义注解时写了@Inherited的运行结果
	-----------------------------------------------------------------
	子类继承到父类类上Annotation,其信息如下：类名上的注解
	子类实现父类的abstractMethod抽象方法，没有继承到父类抽象方法中的Annotation
	子类继承父类的doExtends方法，继承到父类doExtends方法中的Annotation,其信息如下：父类的doExtends方法
	子类覆盖父类的doHandle方法，没有继承到父类doHandle方法中的Annotation
	 
	 
	结论
	-----------------------------------------------------------------
	 
	父类的类上和方法上有自定义的注解，
	子类继承了这个父类，的情况下。
	 
	 
															未写@Inherited的运行结果：	写了@Inherited的运行结果：
	子类的类上能否继承到父类的类上的注解？								否								能
	子类方法，实现了父类上的抽象方法，这个方法能否继承到注解？			否								否
	子类方法，继承了父类上的方法，这个方法能否继承到注解？				能								能
	子类方法，覆盖了父类上的方法，这个方法能否继承到注解？				否								否
	
	我们知道在编写自定义注解时，可以通过指定@Inherited注解，指明自定义注解是否可以被继承。
	
	通过测试结果来看，
	@Inherited 
		只是可控制 对类名上注解是否可以被继承。
		不能控制方法上的注解是否可以被继承。
	 
	 
	附注
	-----------------------------------------------------------------
	Spring 实现事务的注解@Transactional 是可以被继承的，
	通过查看它的源码可以看到@Inherited。
	
	Q:自定义的注解上仍可以有自己定义的注解，
		不过最多可能只算是标志注解，因为不能进行灵活设置值了
 */

public class MainTest {  
    public static void main(String[] args) throws SecurityException,  
            NoSuchMethodException {  
  
        Class<SubClass> clazz = SubClass.class;  
  
        if (clazz.isAnnotationPresent(MyAnnotation.class)) {  
            MyAnnotation mann = clazz.getAnnotation(MyAnnotation.class);  
            System.out.println("子类继承到父类类上Annotation,其信息如下："+mann.value());  
            
            System.out.println(mann.getClass());//可知是个代理类型
            System.out.println(mann.annotationType());
            Class<MyAnnotation> maclazz = (Class<MyAnnotation>) mann.annotationType();
            System.out.println(maclazz);
            MetaAnnotation annot = maclazz.getAnnotation(MetaAnnotation.class); //获取注解上的注解
            System.out.println(">>>获取注解上的注解>>>: " + annot.value());
            
        } else {  
            System.out.println("子类没有继承到父类类上Annotation");  
        }  
  
        // 实现抽象方法测试  
        Method method = clazz.getMethod("abstractMethod", new Class[] {});  
        if (method.isAnnotationPresent(MyAnnotation.class)) {  
            MyAnnotation ma = method.getAnnotation(MyAnnotation.class);  
            System.out.println("子类实现父类的abstractMethod抽象方法，继承到父类抽象方法中的Annotation,其信息如下："+ma.value());  
        } else {  
            System.out.println("子类实现父类的abstractMethod抽象方法，没有继承到父类抽象方法中的Annotation");  
        }  
  
        //覆盖测试  
        Method methodOverride = clazz.getMethod("doExtends", new Class[] {});  
        if (methodOverride.isAnnotationPresent(MyAnnotation.class)) {  
            MyAnnotation ma = methodOverride.getAnnotation(MyAnnotation.class);  
            System.out.println("子类继承父类的doExtends方法，继承到父类doExtends方法中的Annotation,其信息如下："+ma.value());  
        } else {  
            System.out.println("子类继承父类的doExtends方法，没有继承到父类doExtends方法中的Annotation");  
        }  
  
        //继承测试  
        Method method3 = clazz.getMethod("doHandle", new Class[] {});  
        if (method3.isAnnotationPresent(MyAnnotation.class)) {  
            MyAnnotation ma = method3.getAnnotation(MyAnnotation.class);  
            System.out.println("子类覆盖父类的doHandle方法，继承到父类doHandle方法中的Annotation,其信息如下："+ma.value());  
        } else {  
            System.out.println("子类覆盖父类的doHandle方法，没有继承到父类doHandle方法中的Annotation");  
        }  
        
        
    }  
}  