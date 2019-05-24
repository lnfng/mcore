package mcore.about_annotation;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface MetaAnnotation{
	/**value 是个特殊的方法名，可以在赋值的时候省略'value='*/
	String value();
}
