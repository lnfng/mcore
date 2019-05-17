import java.util.*;


class  CollectionDemo
{
	public static void main(String[] args) 
	{
		//创建一个集合容器。使用collection接口的子类，ArrayList
		ArrayList al = new ArrayList();

		//1.添加元素
		al.add("java01");
		al.add("java02");
		al.add("java03");
		al.add("java04");

		Iterator itor=al.iterator(); //获取迭代器，用于取出集合中的元素

		while(itor.hasNext())
		{
			sop(itor.next());
		}

		//可以用for循环类取，优一些,对象在内存的生命周期短
		/*for(Iterator itor=al.iterator(); itor.hasNext(); )
		{
			sop(itor.next());
		}*/

	}

	public static void sop(Object obj)
	{
		System.out.println(obj);
	}
}
/*
1. add方法的参数类型是object，以便于接收任意类型的对象

2. 集合中存储的都是对象的引用（或者说是地址）

3. 什么是迭代器呢？
其实就是集合的取出元素的方式

4. 每个集合子类把取出方式定义在集合的内部，
这样取出方式就可以直接集合内内容的元素。

那么取出方式就定义成了内部类。

而每个容器的数据结构是不同的，
所以取出的动作也是不一样的。


*/