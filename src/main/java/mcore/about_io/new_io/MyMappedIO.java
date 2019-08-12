package mcore.about_io.new_io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


/**
 * 该类是 Java编程思想(第4版) 的源码
 * 
 * @author JackQ
 * 
 */
public class MyMappedIO {
	private final static String RESOURCES = "F:/Project-log/testResource.java";
	private final static String FROM_RESOURCES = "F:/Project-log/fromTestRes.java";
	private static final int BSIZE = 1024*16;
	

	public static void main(String[] args) throws Exception {
		
		Tester nioByteTest = nioByteBufferTest();
		
		Tester nioByteLoopMapTest = nioByteBufferLoopMap();
		
		Tester channelCopyTest = channelCopyTest();
		
		Tester tdTest = tdReadWrite();
		
		for(int j=0;j<10;j++){
//			nioByteTest.runTest();
//			nioByteLoopMapTest.runTest();
//			channelCopyTest.runTest();
			tdTest.runTest();
		}
		
		
	}


	/**
	 * 传统的读写方法
	 * @return
	 */
	static Tester tdReadWrite() {
		return new Tester("Tradictional Read and Write") {
			
			@Override
			public void test() throws IOException {
				FileInputStream fis = new FileInputStream(new File(RESOURCES));
				FileOutputStream fos = new FileOutputStream(FROM_RESOURCES);

				byte[] buf=new byte[BSIZE];//100k左右
				for(int len=0;(len=fis.read(buf))!=-1;){
					fos.write(buf, 0, len);
				}
				
				fos.close();
				fis.close();
			}
		};
	}


	
	/**
	 * 通过映射来复制文件,非常高效,
	 * 缺点,消耗大,字节数有限制
	 * @return
	 */
	static Tester nioByteBufferTest() {
		return new Tester("Mapped Read and Write") {
			@SuppressWarnings("resource")
			public void test() throws IOException {
				
				FileChannel fc = 
						new FileInputStream(new File(RESOURCES)).getChannel();
				MappedByteBuffer inbuf = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
				//The size of the region to be mapped; must be non-negative and no greater than Integer.MAX_VALUE
				//对于大文件必须分批次映射,因为太大本身对机器的消耗也很厉害
				//size是对文件一次性的映射大小要求
				
				FileChannel fc1 = new RandomAccessFile(FROM_RESOURCES, "rw").getChannel();
				MappedByteBuffer outbuf = fc1.map(FileChannel.MapMode.READ_WRITE, 0, fc.size());
				
				/*int len=1024*1024;
				int position = 0;
				int capacity = inbuf.capacity();
				byte[] dst=new byte[len];
				while (inbuf.hasRemaining()){
					position=inbuf.position();
					len=(capacity-position)>len?len:capacity-position;
					inbuf.get(dst,0,len);
					outbuf.put(dst,0,len);
				}*/
				
				outbuf.put(inbuf);//可以循环,也可以直接,但此方法更高效
				
				fc.close();
				fc1.close();
				
				/*
				    Mapped Read and Write: 82.40//首次映射到内存是要时间的
					Mapped Read and Write: 1.20
					Mapped Read and Write: 1.15
					Mapped Read and Write: 1.15
					Mapped Read and Write: 1.38
					Mapped Read and Write: 1.22
					Mapped Read and Write: 1.18
					Mapped Read and Write: 1.19
					Mapped Read and Write: 1.46
					Mapped Read and Write: 1.33
				 * 
				 */
			}
		};
	}

	/**
	 * 通过循环映射来进行复制
	 * 此种方法理论上可以复制大于2G的文件,
	 * 但映射之后太占内存,buffer对象不能及时清除,
	 * 这样会拖咔系统,得不偿失.
	 * 所以建议大于1G的文件都不要使用此种方法.
	 * @return
	 */
	static Tester nioByteBufferLoopMap() {
		return new Tester("Mapped Read and Write") {
			private long m_size = 104857600L;//100M
			
			@SuppressWarnings("resource")
			public void test() throws IOException {
				
				FileChannel inch = new FileInputStream(new File(RESOURCES)).getChannel();
				FileChannel outch = new RandomAccessFile(FROM_RESOURCES, "rw").getChannel();
				
				long fileSize = inch.size();
				long remaining = fileSize;
				long position = 0;
				
				while(remaining>0&&position!=fileSize){
					
					remaining = fileSize - position;//剩余量
					if(remaining < m_size){
						m_size = remaining; //当剩余量少于 设定的m_size时需要调整
					}
//					System.out.println("position : "+ position+", remaining :"+remaining + ", m_size :"+m_size);
					MappedByteBuffer inbuf = inch.map(FileChannel.MapMode.READ_ONLY, position, m_size);
					MappedByteBuffer outbuf = outch.map(FileChannel.MapMode.READ_WRITE, position, m_size);
					outbuf.put(inbuf);
					
					outbuf.force();
					outbuf.clear();
					inbuf.clear();
					
					position += m_size;//变更位置
				}
				
				inch.close();
				outch.close();
				
			}
		};
	}
	
	
	static Tester channelCopyTest() {
		return new Tester("channelCopyTest ") {
			
			@SuppressWarnings("resource")
			public void test() throws IOException {
				
				FileChannel inch = new FileInputStream(new File(RESOURCES)).getChannel();
				FileChannel outch = new RandomAccessFile(FROM_RESOURCES, "rw").getChannel();
				//也可以通过FileOutputStream#getchannel
				
				ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
//				ByteBuffer buffer = ByteBuffer.allocateDirect(BSIZE);
				//直接内存与非直接内存性能上有点差距,但直接内存的回收有一定问题,还有点不稳定
				
			    while(inch.read(buffer) != -1) {
			      buffer.flip(); // Prepare for writing
			      outch.write(buffer);
			      buffer.clear();  // Prepare for reading
			    }
				//使用循环读取方式, 85M的文件10次结果
			    //100k 缓冲 平均使用0.08
			    //64k 缓冲 平均使用0.09
			    //32k 缓冲 平均使用0.09
			    //16k 缓冲 平均使用0.1
			    //8k 缓冲 平均使用0.12
			    
			    
				/*
				//或者可以使用两条管道联通,不必通过buffer做中转
				//inch.transferTo(0, inch.size(), outch);
				// 等同outch.transferFrom(inch, 0, inch.size());
				
				使用上通道转换方式,85M的文件10次结果平均使用 0.8秒
				比循环读取慢了十倍. 该差异或许是缓冲大小也有一定关系吧.
				推测该缓冲为512byte.
				*/
				
				inch.close();
				outch.close();
				
			}
		};
	}
	
	
	
	
	
	private abstract static class Tester {
		private String name;
		public Tester(String name) {
			this.name = name;
		}

		public void runTest() {
			System.out.print(name + ": ");
			try {
				long start = System.nanoTime();
				test();
				double duration = System.nanoTime() - start;
				System.out.format("%.2f\n", duration / 1.0e9);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public abstract void test() throws IOException;
	}

}
