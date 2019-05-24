package mcore.about_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**retention 保留 
 * 表示该注解保留到什么时候，有运行时，编译时， 编写时（相当于一个标志）
RUNTIME 
          编译器将把注释记录在类文件中，在运行时 VM 将保留注释，因此可以反射性地读取。 
CLASS 
          编译器将把注释记录在类文件中，但在运行时 VM 不需要保留注释。 
SOURCE 
          编译器要丢弃的注释。 
*/
@Retention(RetentionPolicy.RUNTIME)
/** ANNOTATION_TYPE 
	          注解类型声明 
	CONSTRUCTOR 
	          构造方法声明 
	FIELD 
	          字段声明（包括枚举常量） 
	LOCAL_VARIABLE 
	          局部变量声明 
	METHOD 
	          方法声明 
	PACKAGE 
	          包声明 
	PARAMETER 
	          参数声明 
	TYPE 
	          类、接口（包括注释类型）或枚举声明 
	
	Target注解就只有以上的类型
	当前表示可用在方法上以及类、接口（包括注释类型）或枚举上
*/
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface ItcastAnnotation {
	/**只有设置了默认值，在赋值的时候才不会检查*/
	String color() default "blue";
	/**value 是个特殊的方法名，可以在赋值的时候省略'value='*/
	String value();
	/**注解类方法的返回值便是跟赋值类型一样*/
	int[] arrayAttr() default {3,4,4};
	EnumTest.TrafficLamp lamp() default EnumTest.TrafficLamp.RED;
	MetaAnnotation annotationAttr() default @MetaAnnotation("lhm");
}
