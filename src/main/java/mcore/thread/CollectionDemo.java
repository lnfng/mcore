import java.util.*;


class  CollectionDemo
{
	public static void main(String[] args) 
	{
		//����һ������������ʹ��collection�ӿڵ����࣬ArrayList
		ArrayList al = new ArrayList();

		//1.���Ԫ��
		al.add("java01");
		al.add("java02");
		al.add("java03");
		al.add("java04");

		Iterator itor=al.iterator(); //��ȡ������������ȡ�������е�Ԫ��

		while(itor.hasNext())
		{
			sop(itor.next());
		}

		//������forѭ����ȡ����һЩ,�������ڴ���������ڶ�
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
1. add�����Ĳ���������object���Ա��ڽ����������͵Ķ���

2. �����д洢�Ķ��Ƕ�������ã�����˵�ǵ�ַ��

3. ʲô�ǵ������أ�
��ʵ���Ǽ��ϵ�ȡ��Ԫ�صķ�ʽ

4. ÿ�����������ȡ����ʽ�����ڼ��ϵ��ڲ���
����ȡ����ʽ�Ϳ���ֱ�Ӽ��������ݵ�Ԫ�ء�

��ôȡ����ʽ�Ͷ�������ڲ��ࡣ

��ÿ�����������ݽṹ�ǲ�ͬ�ģ�
����ȡ���Ķ���Ҳ�ǲ�һ���ġ�


*/