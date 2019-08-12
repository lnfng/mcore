package mcore.about_generic;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Vector;

public class GenericReflect {
	
	public static void main(String[] args) throws Exception {
		
		/**反射技术：通过下面的表达式是无法获得Vector的参数类型的*/
		Vector<String> v=new Vector<String>();
		
		
		
		/**只能通过方法的参数类型获得集合的实际类型*/
		Method method=GenericReflect.class.getMethod("genericReflect", Vector.class);
		Type[] tps=method.getGenericParameterTypes();
		ParameterizedType pt= (ParameterizedType) tps[0];
		
		/**class java.util.Vector*/
		System.out.println(pt.getRawType());
		/**class java.lang.String*/
		System.out.println(pt.getActualTypeArguments()[0]);
		
	}
	
	/**巴巴运动网上的片段*/
	public <T> String getEntityName(Class<T> entityClass){
		String entityName=entityClass.getSimpleName();
		
		/*通过获得注解，再判断Entity的name是否为空
		 * 因为Entity是Spring框架中的注解，在这无法编译通过
		Entity entity=entityClass.getAnnotation(Entity.class);
		if(entity.name()!=null&&"".equals(entity.name())){
			entityName=entity.name();
		}
		*/
		return entityName;
	}

	public void genericReflect(Vector<String> v){
		
	}
	
	
}
