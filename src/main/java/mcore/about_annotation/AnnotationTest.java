package mcore.about_annotation;

import java.lang.reflect.Method;

/**
 * 当前是测试类，也是一个注解类
 * @author JackQ
 *
 */
@ItcastAnnotation
	(annotationAttr=@MetaAnnotation("flx"),color="red",value="abc",arrayAttr=1)
public class AnnotationTest {

	@ItcastAnnotation("xyz")
	public static void main(String[] args) throws Exception{
		/**反射技术：查找该类是否有注解*/
		if(AnnotationTest.class.isAnnotationPresent(ItcastAnnotation.class)){
			ItcastAnnotation annotation = 
				(ItcastAnnotation)AnnotationTest.class.getAnnotation(ItcastAnnotation.class);
			System.out.println(annotation.color());
			System.out.println(annotation.value());
			System.out.println(annotation.arrayAttr().length);
			System.out.println(annotation.lamp().nextLamp().name());
			System.out.println(annotation.annotationAttr().value());
		}
		/**
		  * 在方法上的注解则需要获得该方法对象再进行操作，其它的成份一样
		  *	 <T extends Annotation> T  getAnnotation(Class<T> annotationClass) 
	      *	   如果存在该元素的指定类型的注释，则返回这些注释，否则返回 null。 
	 	  *	 Annotation[] getDeclaredAnnotations() 
	      * 返回直接存在于此元素上的所有注释。
          */
		
		Method mainMethod = AnnotationTest.class.getMethod("main", String[].class);
		ItcastAnnotation annotation2 = 
			(ItcastAnnotation)mainMethod.getAnnotation(ItcastAnnotation.class);
		System.out.println(annotation2.value());
	}

	/**这是标志方法过时的注解，是在class阶段*/
	@Deprecated
	public static void sayHello(){
		System.out.println("hi,广告费呢");
	}
}
