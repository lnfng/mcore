package mcore.about_thread.executors;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 考虑下应用场景:
 * 在cpu为多核时,并且你的处理结果异步,没有依赖关系
 * 需要处理返回结果的时候适用
 * @author Qian
 */
public class CallableAndFuture {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executor=Executors.newSingleThreadExecutor();
		
		Future<String> future=executor.submit(
				/**该泛型就是返回结果的类型*/
				() -> {
					Thread.sleep(3000);
					return "hello world";
				});
		
		System.out.println("等待结果...");
		System.out.println("得到结果："+future.get());
		
		/**
		 * 上面的方法是阻塞的,下面的为异步执行
		 * 
		 * CompletionService用于提交一组Callable任务，
		 * 其take（）方法返回已完成的一个Callable任务对应的Future对象
		 */
		ExecutorService executor2=Executors.newFixedThreadPool(10);
		CompletionService<String> completionService
			=new ExecutorCompletionService<String>(executor2);
		//提交任务
		for(int i=1;i<=10;i++){
			final int sequence=i;
			completionService.submit(
				new Callable<String>() {
					public String call() throws Exception {
						Thread.sleep(new Random().nextInt(3000));
						return "序列："+sequence;
				}
			});
		}
		//获取任务结果
		for(int i=1;i<=10;i++){
			System.out.println(completionService.take().get());
		}
	}

}
