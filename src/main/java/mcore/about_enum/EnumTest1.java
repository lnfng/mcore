package mcore.about_enum;

public class EnumTest1 {
	
	public static void main(String[] args) {
		WeekDay weekDay=WeekDay.MON;
		System.out.println(weekDay.nextDay());
	}

}
/**
 * 没有封装enum 类的实现方法
 * 这就是普通Java模拟枚举类的功能
 * @author JackQ
 *
 */
abstract class WeekDay{
	
	private WeekDay(){}
	/**这里就简单举了两个*/
	public static final WeekDay SUN=new WeekDay(){
		@Override
		public WeekDay nextDay() {
			return MON;
		}
		
	};
	public static final WeekDay MON=new WeekDay(){
		@Override
		public WeekDay nextDay() {
			return SUN;
		}
		
	};
	
	public abstract WeekDay nextDay();/*{
		//还有另外的实现方式，让子类自己实现该方法
		//目的是把大量的if else语句转到独立的类中 
		if(this==SUN){
			return MON;
		}else{
			return SUN;
		}
	}*/
	
	@Override
	public String toString() {
		return this==MON?"MON":"SUN";
	}
}
