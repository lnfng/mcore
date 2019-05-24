package mcore.about_enum;


public class EnumTest2 {
	
	public static void main(String[] args) {
		WeekDay1 wd=WeekDay1.FRI;
		
		//自动实现了toString方法
		System.out.println(wd);
		System.out.println(wd.name()=="FRI");
		
		//该类的位置
		System.out.println(wd.ordinal());
		
		//通过字符串构造对象,最好要处理异常
		WeekDay1 wd1=WeekDay1.valueOf("WED");
		System.out.println(wd1);
		
		//返回该枚举的所有对象
		WeekDay1[] wds=WeekDay1.values();
		for(WeekDay1 w:wds){
			System.out.println(w);
		}
		WeekDay1 wd11=WeekDay1.MON;
	}



	/**简单的枚举类
	 * 内部类可以有四种修饰符public default protected private
	 * 外部类只有两种public default*/
	public enum WeekDay1{
		SUN(),MON(2),TUE,WED,THR,FRI,SAT;
		
		/**亦可有构造方法，但必须私有化*/
		private WeekDay1(){
			System.out.println("first...");
		}
		private WeekDay1(int id){
			System.out.println("second...");
		}
	}
	
}

