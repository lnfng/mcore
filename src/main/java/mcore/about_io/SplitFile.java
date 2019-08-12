package mcore.about_io;
import java.io.*;
import java.util.*;
/**
 * 这先进行切割，后合并
 * 可以对比较大的文件进行切割后在发送
 * @author JackQ
 *
 */
public class SplitFile {
	
	public static void main(String[] args) throws IOException {
		splitFile();
//		mergeFile();
	}

	private static void mergeFile() throws IOException {
		//因为Vector比较低效，故用arraylist
		List<FileInputStream> list=new ArrayList<FileInputStream>();
		for(int i=1;i<=6;i++){
			list.add(new FileInputStream("splitFiles//"+i+".part"));
		}
		final Iterator<FileInputStream> iter=list.iterator();
		//因为是需要Enunciation的对象，故手动创建
		Enumeration<FileInputStream> en=new Enumeration<FileInputStream>() {
			public FileInputStream nextElement() {
				return iter.next();
			}
			public boolean hasMoreElements() {
				return iter.hasNext();
			}
		};
		SequenceInputStream sis=new SequenceInputStream(en);
		
		FileOutputStream fos=new FileOutputStream("splitFiles//merge.png");
		byte[] buf=new byte[1024];
		for(int len=0;(len=sis.read(buf))!=-1;){
			fos.write(buf, 0, len);
		}
		fos.close();
		sis.close();
	}

	private static void splitFile() throws IOException {
		FileInputStream fis=new FileInputStream("splitFiles//original.png");
		
		int available = fis.available();
		int sp = available/150;
		
		FileOutputStream fos=null;
		/**一兆为单位切割*/
		byte[] buf=new byte[1024*1024];
		int count=1;
		for(int len=0;(len=fis.read(buf))!=-1;count++){
			/**可以在此累计多少换流对象*/
			fos=new FileOutputStream("splitFiles//"+count+".part");
			fos.write(buf, 0, len);
			fos.close();
		}
		fis.close();
	}

}
