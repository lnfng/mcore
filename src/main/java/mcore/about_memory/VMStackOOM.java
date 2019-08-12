package mcore.about_memory;

/**
 * -VM Args: -Xss2M 
 * 把栈的容量提到2m，使建立的栈减少
 * 
 * 不要轻易的尝试，会电脑死机
 * 没能得到想要的结果，
 * 
 * @author JackQ
 */
public class VMStackOOM {

	private void dontStop(){
		int i=0;
		for(;;){
			System.out.println("times--->"+i++);
		}
	}
	
	public void stackLeakByThread(){
		/*for(;;){
			Thread thread=new Thread(){
				@Override
				public void run() {
					dontStop();
				}
			};
			thread.start();
		}*/
	}
	
	public static void main(String[] args) {
		VMStackOOM oom=new VMStackOOM();
		oom.stackLeakByThread();
	}
	
}
