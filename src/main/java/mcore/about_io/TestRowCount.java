package mcore.about_io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.*;



public class TestRowCount {
	
	
    public static void main(String[] args) throws Exception {
    	
    	final String fileName="F:/InfServices.java";
    	long startTime = System.currentTimeMillis();
    	
    	InfMethodInfo info = new InfMethodInfo();
		info.setComment("红包活动订单:活体审核结果实时通知接口");
		info.setMethodSign("notify001"); // 方法名
		info.setOpCode("notify.auth.result.req"); // 接口编码
		info.setReqClassName("zte.net.ecsord.params.ability.req.NotifyAuthResultReq");	// 请求类全量类名
		info.setRespClassName("zte.net.ecsord.params.ability.resp.NotifyAuthResultResp"); // 响应类全量类名
    	
		info.setTempNames(Arrays.asList("inf-imp"));
		List<TempCode> tempCodes = new ArrayList();//AutoGenerateCode.generateTempCode(info);
		
		for(TempCode tempCode:tempCodes){
			tempCode.setSourcePath(fileName);
			if(!"sql".equals(tempCode.getCodeType())){
//				TestRowCount.writeJaveCodeToFile(tempCode);
				TestRowCount.writeJaveCodeToFile001(tempCode);
			}
		}
		
		System.out.println(">> 生成代码花费时间:"+(System.currentTimeMillis()-startTime));
		
    }
 
    /**
     * 比较耗时
     * @param fileName
     * @return
     */
    public static int getFileLineCount(String fileName) {
        int cnt = 0;
        LineNumberReader reader = null;
        try {
        	File file = new File(fileName);
            reader = new LineNumberReader(new FileReader(file));
            reader.skip(file.length());
            cnt = reader.getLineNumber(); // 这个还是比较耗时, 有锁操作
            
            // 通过通道可以自由活动
            
//            try { // JDK 8
//            	long startTime=System.currentTimeMillis();
//                long lines = Files.lines(Paths.get(new File("e://test.fa").getPath())).count();
//                System.out.println("Total number of lines : " + lines);
//                long endTime=System.currentTimeMillis();
//                System.out.println("Total time is:"+ (endTime-startTime) );
//            } catch (IOException e) {
//              System.out.println("No File Found");
//            }

            
        } catch (Exception ex) {
            cnt = -1;
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return cnt;
    }
 
    /**
     * 较快, 比上面的快一倍
     * @param filename
     * @return
     */
    public static int getFileLineCounts(String fileName) {
        int cnt = 0;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(fileName));
            int readChars = 0;
            byte[] buffer = new byte[1024*8];
            while ((readChars = is.read(buffer))!= -1) {
                for (int i = 0; i < readChars; i++) {
                    if (buffer[i] == '\n') {
                    	++cnt;
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {is.close();}catch (Exception e){}
        }
        return cnt;
    }
    
    /**
     * 查找字符串 
     * 失败,setLine 并非定位到某一行
     * @param fileName
     * @param begin
     * @param end
     * @return
     */
    public static int findStr(String fileName,String cstr, int begin, int end) {
        int cnt = 0;
        LineNumberReader reader = null;
        try {
            reader = new LineNumberReader(new FileReader(fileName));
            String line = "";
            reader.setLineNumber(begin); // 并非定位到某一行
            while ((line = reader.readLine()) != null) {
            	cnt = reader.getLineNumber();
            	if(line.contains(cstr)){
            		System.out.println(reader.getLineNumber()+"->"+line);
            	}
            	if(cnt==end) break;
            }
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            try {reader.close();}catch (Exception e){}
        }
        return cnt;
    }
    
    static Map<Integer,Long> getLinePosition(String fileName){
    	
    	RandomAccessFile raf = null;
		try{
			
			
			Long lastPosition = 0L;
			Long currPosition = 0L;
			raf = new RandomAccessFile(fileName, "r"); // 该类读取非常慢
			Map<Integer,Long> linePosition 
				= new LinkedHashMap<Integer, Long>(50);
			Integer line=1;
			for (;raf.readLine()!= null;line++) {
				lastPosition = currPosition;
				currPosition = raf.getFilePointer();
				linePosition.put(line, lastPosition);
			}
			System.out.println(line);
			return linePosition;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			if(raf!=null) try{raf.close();}catch (IOException e) {}
		}
    }
    
    /**
     * 统计行数对比
     * @param fileName
     */
    static void compareReadLine(String fileName){
    	
    	LineNumberReader reader = null;
    	RandomAccessFile raf = null; // 文件访问对象
    	InputStream is = null;
        try {
            reader = new LineNumberReader(new FileReader(fileName));
            raf = new RandomAccessFile(fileName, "rw");
            
            String line = "";
            int firstCount = 0;
            int secondCount = 0;
            int thirdCount = 0;
            int fouthCount = 0;
            
            long startTime = System.currentTimeMillis();
            while ((line = reader.readLine()) != null) {
            	firstCount++;
            }
            System.out.println(">> firstCount="+firstCount
            		+" 花费时间:"+(System.currentTimeMillis()-startTime));
            
            
            final int BASE_BUF = 1024*16;
            Charset cs = Charset.forName ("UTF-8");
            startTime = System.currentTimeMillis();
            FileChannel channel = raf.getChannel();
            for(ByteBuffer buffer = ByteBuffer.allocateDirect(BASE_BUF);
            		channel.read(buffer) != -1;) {
		      
            	buffer.flip(); // Prepare for writing
            	
            	char[] array = cs.decode(buffer).array();
            	for (int i = 0; i < array.length; i++) {
            		if (array[i] == '\n') {
            			secondCount++;
            		}
            		// 可以追加成完整的行, 
            		// 缓冲的最后不是一行则衔接下一个缓冲
            	}
		      
            	buffer.clear();  // Prepare for reading
		    }
            System.out.println(">> secondCount="+secondCount
            		+" 花费时间:"+(System.currentTimeMillis()-startTime));
            
            startTime = System.currentTimeMillis();
            is = new BufferedInputStream(new FileInputStream(fileName));
            int readChars = 0;
            byte[] buffer = new byte[BASE_BUF];
            while ((readChars = is.read(buffer))!= -1) {
                for (int i = 0; i < readChars; i++) {
                    if (buffer[i] == '\n') {
                    	thirdCount++;
                    }
                }
            }
            System.out.println(">> thirdCount="+thirdCount
            		+" 花费时间:"+(System.currentTimeMillis()-startTime));
            
            startTime = System.currentTimeMillis();
            raf.seek(0);
            while((line = raf.readLine())!= null){
            	fouthCount++;
            }
            System.out.println(">> fouthCount="+fouthCount
            		+" 花费时间:"+(System.currentTimeMillis()-startTime));
            
            /* 	下面是读取一万行
              	平均结果基本如下:
             	>> firstCount=11002 花费时间:56
				>> secondCount=11002 花费时间:48
				>> thirdCount=11002 花费时间:16
				>> fouthCount=11002 花费时间:2720
				
				
				*/
            
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if(reader!=null)
				try {reader.close();}catch(Exception e){}
            if(raf!=null)
				try {raf.close();}catch(Exception e){}
            if(is!=null)
				try {is.close();}catch(Exception e){}
        }
    	
    }
    
    /**
	 * 将生成的代码写到文件
	 */
    static String SEPARATOR = System.getProperty("line.separator"); 	// 换行符
	public static void writeJaveCodeToFile(TempCode tempCode){
		if(isBlank(tempCode.getCodeTemp())){
			System.err.println(">> CodeTemp is required!!!");return ;
		}
		int endPoint = 0;	 	// 最后一个括号
		int infPoint = 0; 		// 接口方法体插入的位置
		int importPoint = 0; 	// 导入语句插入的位置
		int methodPoint = 0; 	// 方法体插入的位置
		boolean isComm = false; // 是否是注释体
		RandomAccessFile raf = null; // 文件访问对象
		LineNumberReader reader = null; // 文件访问对象
		try {
            
            String line=null;
            String importTemp = tempCode.getImportTemp();
            StringBuilder builder = new StringBuilder();
            raf = new RandomAccessFile(tempCode.getSourcePath(), "rw");
            reader = new LineNumberReader(new FileReader(tempCode.getSourcePath()));
            while ((line = reader.readLine()) != null) {
				builder.append(line).append(SEPARATOR);
				String cline=line.trim(); 
				// 排除掉注释
				if(cline.startsWith("//")||cline.matches("/\\*.*\\*/")) {continue;}
				if(cline.startsWith("/*")) {isComm = true; continue;}
				if(cline.endsWith("*/")) {isComm = false; continue;}
				if(isComm) {continue;}
				
				if(cline.startsWith("import ")){
					if(importTemp.contains(cline)){
						importTemp=importTemp.replaceFirst(cline, "");
						continue;
					}
					importPoint = builder.length();
				}
				if(cline.endsWith("}")){
					methodPoint = endPoint;
					endPoint = builder.length();
				}
				else if(cline.endsWith(";")){
					infPoint = builder.length();
				}
            }
			
            importTemp = importTemp.trim(); // trim 会把换行符也去掉
			int insertPoint = importPoint; // 代码片段插入的位置
			if(!isBlank(importTemp)){
				importTemp = importTemp+SEPARATOR;
				builder.insert(insertPoint, importTemp);// 插入import语句
			}

			if("class".equals(tempCode.getCodeType())){
				insertPoint = (methodPoint==0 ? endPoint-4:methodPoint);
			}
			else if("interface".equals(tempCode.getCodeType())){
				insertPoint = (infPoint==0 ? endPoint-4:infPoint);
			}
			insertPoint +=importTemp.length(); // 如果有import语句插入位置就会发生变化
			builder.insert(insertPoint, tempCode.getCodeTemp()); // 插入代码片段

			raf.seek(0);
			raf.writeBytes(new String(builder.toString()
				.getBytes("UTF-8"), /*写入文件*/
				"ISO-8859-1")); 
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			if(raf!=null)
				try {raf.close();}catch(Exception e) {}
			if(reader!=null)
				try {reader.close();}catch(Exception e) {}
		}
	}
	
	/**
	 * 超级复杂, 哈哈哈
	 * 
	 */
	public static void writeJaveCodeToFile001(TempCode tempCode){
		if(isBlank(tempCode.getCodeTemp())){
			System.err.println(">> CodeTemp is required!!!");return ;
		}
		int endPoint = 0;	 	// 最后一个括号
		int infPoint = 0; 		// 接口方法体插入的位置
		int importPoint = 0; 	// 导入语句插入的位置
		int methodPoint = 0; 	// 方法体插入的位置
		boolean isComm = false; // 是否是注释体
		RandomAccessFile raf = null; // 文件访问对象
		try{
			raf = new RandomAccessFile(tempCode.getSourcePath(), "rw");
			byte[] bs =null;
			byte lineSep = (byte)'\n';	// 换行符
			String line,tline = null;	// 辅助字段
			FileChannel channel = raf.getChannel();
			long countLen = 0,totalLen = raf.length();
			StringBuilder builder = new StringBuilder();
			String importTemp = tempCode.getImportTemp();
			for (ByteBuffer buffer=ByteBuffer.allocate(8*1024);channel.read(buffer)!=-1;) {
				buffer.flip(); 
				int offset=0;	// 偏移量
				byte[] array = buffer.array();
				int arrayLen = array.length;
				countLen += arrayLen;
				boolean isFileEnd = true;
				if(countLen>totalLen){ // 正确字节数
					arrayLen = (int)(arrayLen - (countLen-totalLen));
				}
				for (int i = 0; i < arrayLen; i++) {
					if(array[i]!=lineSep){ // 不够一行则缓存够一行
						if(i==(arrayLen-1)){
							byte[] temp = bs==null ?
								new byte[arrayLen-offset]
									: new byte[bs.length+arrayLen-offset];
							if(bs!=null){ // 动态扩容
								System.arraycopy(bs, 0, temp, 0, bs.length);
							}
							System.arraycopy(array, offset, temp, bs==null?0:bs.length, arrayLen-offset);
							isFileEnd = (i==array.length-1);
							bs = temp;
						}
						if(isFileEnd)continue; // 如果最后一个不包含换行符
					}
					int copyLen = i+1-offset;
					if(bs!=null){
						if(!isFileEnd){ 
							// 如果是文件结尾且不包含换行作为一行
							line = new String(bs,0,bs.length,"UTF-8");
						}else{
							// 需要拼接的行
							byte[] dest=new byte[copyLen+bs.length];
							System.arraycopy(bs, 0, dest, 0, bs.length);
							System.arraycopy(array, 0, dest, bs.length, copyLen);
							line = new String(dest,0,dest.length,"UTF-8");
						}
						bs = null;
					}else{
						// 普通行
						line = new String(array,offset,copyLen,"UTF-8");
					}
					offset=i+1;
					builder.append(line);
					tline = line.trim();
					// 排除掉注释
					if(tline.startsWith("//")||tline.matches("/\\*.*\\*/")) {continue;}
					if(tline.startsWith("/*")) {isComm = true; continue;}
					if(tline.endsWith("*/")) {isComm = false; continue;}
					if(isComm) {continue;}
					
					if(tline.startsWith("import ")){
						if(importTemp.contains(tline)){
							importTemp=importTemp.replaceFirst(tline, "");
							continue;
						}// 记录导入语句的插入点
						importPoint = builder.length();
					}
					// 用冗余点记录上一个结束括号的位置
					if(tline.equals("}")){ 
						methodPoint = endPoint;
						endPoint = builder.length();
					}
					else if("interface".equals(tempCode.getCodeType())
						&& tline.endsWith(";")){// 接口的插入点
						infPoint = builder.length();
					}
				}
				buffer.clear();
			}
			importTemp = importTemp.trim(); // trim 会把换行符也去掉
			int insertPoint = importPoint;  // 代码片段插入的位置
			if(!isBlank(importTemp)){
				importTemp = importTemp+SEPARATOR;
				builder.insert(insertPoint, importTemp);// 插入import语句
			}

			if("class".equals(tempCode.getCodeType())){
				insertPoint = (methodPoint==0 ? endPoint-4:methodPoint);
			}
			else if("interface".equals(tempCode.getCodeType())){
				insertPoint = (infPoint==0 ? endPoint-4:infPoint);
			}
			insertPoint +=importTemp.length(); // 如果有import语句插入位置就会发生变化
			builder.insert(insertPoint, tempCode.getCodeTemp()); // 插入代码片段

			raf.seek(0);
			raf.writeBytes(new String(builder.toString()
				.getBytes("UTF-8"), /*写入文件*/
				"ISO-8859-1")); 
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			if(raf!=null)
			try {raf.close();}catch(Exception e) {}
		}
	}
	
	public static int getBytesContentLen(byte[] bs){
		int len = -1;
		if(bs!=null){
			for(len=0;len<bs.length;len++){
				if(bs[len]==0){break;}
			}
		}
		return len;
	}
	
	
	static boolean isBlank(String str){
		return str==null||"".equals(str.trim());
	}
    
    
    /** 
     * 实现向指定位置 
     * 插入数据 
     * @param fileName 文件名 
     * @param points 指针位置 
     * @param insertContent 插入内容 
     * **/  
    @SuppressWarnings("resource")
	public static void insert(String fileName,long points,String insertContent){ 
    	
    	/** 方法名                     作用
			getFilePointer()   返回文件记录指针的当前位置
			seek(long pos)    将文件记录指针定位到pos的位置
			1.功能1，读取任意位置的数据
			2.功能2，追加数据
			3.功能3，任意位置插入数据 */
    	
        try{  
        File tmp=File.createTempFile("tmp", null);  
        tmp.deleteOnExit();//在JVM退出时删除  
          
        RandomAccessFile raf=new RandomAccessFile(fileName, "rw");  
        //创建一个临时文件夹来保存插入点后的数据  
        FileOutputStream tmpOut=new FileOutputStream(tmp);  
        FileInputStream tmpIn=new FileInputStream(tmp);  
        raf.seek(points);  
        /**将插入点后的内容读入临时文件夹**/  
          
        byte [] buff=new byte[1024];  
        //用于保存临时读取的字节数  
        int hasRead=0;  
        //循环读取插入点后的内容  
        while((hasRead=raf.read(buff))>0){  
            // 将读取的数据写入临时文件中  
            tmpOut.write(buff, 0, hasRead);  
        }  
          
        //插入需要指定添加的数据  
        raf.seek(points);//返回原来的插入处  
        //追加需要追加的内容  
        raf.write(insertContent.getBytes());  
        //最后追加临时文件中的内容  
        while((hasRead=tmpIn.read(buff))>0){  
            raf.write(buff,0,hasRead);  
        }  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    }   

    
}