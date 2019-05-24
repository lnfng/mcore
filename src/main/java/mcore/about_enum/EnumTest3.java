package mcore.about_enum;

public class EnumTest3 {

	public static void main(String[] args) {
		TrafficeLamp lamp=TrafficeLamp.GREEN;
		
		System.out.println(lamp);
		System.out.println(lamp.nextLamp());
	}
	
	/**交通灯的枚举类
	 * 枚举只有一个成员时，可以作为单例*/
	public enum TrafficeLamp{
		RED(40){
			@Override
			public TrafficeLamp nextLamp() {
				return GREEN;
			}
		},
		GREEN(45){
			@Override
			public TrafficeLamp nextLamp() {
				return YELLOW;
			}
		},
		YELLOW(5){
			@Override
			public TrafficeLamp nextLamp() {
				return RED;
			}
		};
		/**枚举子类必须在其他Field的前面*/
		private int time;
		
		private TrafficeLamp(){}
		
		private TrafficeLamp(int time){
			this.time=time;
		}
		
		public int getTime() {
			return time;
		}
		//有个抽象方法返回下一个灯
		public abstract TrafficeLamp nextLamp();
	}
}
