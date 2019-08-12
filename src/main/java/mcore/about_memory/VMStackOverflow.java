package mcore.about_memory;

/***
 * VM Args: -Xss128k
 * java 虚拟机栈 StackOverflowError
 * 请求的栈深度大于虚拟机允许的深度
 * @author JackQ
 */
public class VMStackOverflow {
	
	private int stackLength=1;
	
	public void stackLeak(){
		stackLength++;
		stackLeak();
	}

	public static void main(String[] args) {
		VMStackOverflow VMSOF=new VMStackOverflow();
		try{
			VMSOF.stackLeak();
		}catch(Throwable thr){
			System.out.println("栈的深度为--->"+VMSOF.stackLength);
			throw thr;
		}
	}
	/*the info
		栈的深度为--->991
		Exception in thread "main" java.lang.StackOverflowError
			at out_of_memory.VMStackOverflow.stackLeak(VMStackOverflow.java:14)
			at out_of_memory.VMStackOverflow.stackLeak(VMStackOverflow.java:15)
			at out_of_memory.VMStackOverflow.stackLeak(VMStackOverflow.java:15)
			at out_of_memory.VMStackOverflow.stackLeak(VMStackOverflow.java:15)
			...
	*/
}
