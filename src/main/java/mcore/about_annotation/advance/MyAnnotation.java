package mcore.about_annotation.advance;

import java.lang.annotation.*;  

import about_annotation.MetaAnnotation;
/** 
 * 自定义注解 
 */ 

@Target({ElementType.METHOD, ElementType.TYPE})//可以作用在类上和方法上  
//@Documented//javadoc生成文件档时，包含本注解信息 

@Inherited  //可以被继承  
/**
 	@Inherited 
		只是可控制 对类名上注解是否可以被继承。
		不能控制方法上的注解是否可以被继承。 
 */

@Retention(RetentionPolicy.RUNTIME)  //可以通过反射读取注解  

@MetaAnnotation(value = "看看能否实现类似继承的功能")
public @interface MyAnnotation {    
    
	String value();  
	
}   