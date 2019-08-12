package mcore.about_io;

import java.io.File;
/**
 *删除原理：
 *在window中，删除带内容的目录是从里到外删除，故而需要用到递归 
 * @author JackQ
 *
 */
public class RemoveDIR {
	
	public static void main(String[] args) {
		File dir=new File("E://testdir");
		removeDir(dir);
	}
	
	public static void removeDir(File dir){
		if(dir.isDirectory()){
			File[] files=dir.listFiles();
			for(File f:files){
				removeDir(f);
			}
		}
		System.out.println("delete the directory :"+dir.toString()+"--->"+dir.delete());
		
	}
}
