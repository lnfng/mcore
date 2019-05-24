package mcore.about_thread.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TraditionTimer {

	private static int count=0;
	public static void main(String[] args) throws InterruptedException {

		/*new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("bombing....");

			}
		}, 5000);*/
		/**
		 * 静态方法内不能直接实例化内部类对象（new TraditionTimer() 不可行）
		 * 要必须存在外部类才能创建内部类。
		 * 因为静态方法不需要实例就可调用，而内部类是隶属于外部类，
		 * 可共享外部类的资源，若不存在外部类，则该类就无法创建了。
		 */
		MyTimerTask task=new TraditionTimer().new MyTimerTask();
		
		new Timer().schedule(task,2000);
		while (true) {
			// 仅用于输出秒数
			System.out.println(new Date().getSeconds());
			Thread.sleep(1000);
		}
		
	}
	
	/**
	 * 2,4,2,4...这种循环有两种解决方案
	 * 一种是定义两个定时器，一个为2 ，一个为4,在分别调用
	 * 另一种就是利用奇数偶数来判断
	 * @author JackQ
	 *
	 */

	class MyTimerTask extends TimerTask{
		
		@Override
		public void run() {
			count=(count+1)%2;
			System.out.println("bombing...");
			//在进行调度
			new Timer().schedule(new MyTimerTask(), 2000+2000*count);
		}
		
	}
	/**
	 * 定时器内嵌 可以
	 * 1.
	 * public void schedule(TimerTask task, long delay)
	 * 安排在指定延迟后执行指定的任务。
	 * 
	 * 参数： task - 所要安排的任务。 
	 * delay - 执行任务前的延迟时间，单位是毫秒。 抛出：
	 * IllegalArgumentException - 如果 delay 是负数，或者 delay +
	 * System.currentTimeMillis() 是负数。 IllegalStateException -
	 * 如果已经安排或取消了任务，或者已经取消计时器。
	 * 
	 * 2.
	 * public void schedule(TimerTask task,Date time)
	 * 安排在指定的时间执行指定的任务。如果此时间已过去，则安排立即执行该任务。 
	 * 
	 * 参数：
		task - 所要安排的任务。
		time - 执行任务的时间。
	 * 
	 * 更多方法轻详查
	 */

}












