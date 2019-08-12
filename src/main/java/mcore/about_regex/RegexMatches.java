package mcore.about_regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexMatches{
	
    public static void main( String args[] ){
    	
    	String reg="^\\w+$";
    	System.out.println("1ls@23".matches(reg));
    	
    	
    	
    	Pattern p = Pattern.compile("^\\d+$");
		Matcher m = p.matcher("1231232");
		System.out.println(m.matches()); //整个匹配
		System.out.println(m.find()); //配子串,
		
		/**把order_id 替换掉
		 * 规则是从"order_id":"开始到下一个双引号"结束
		 * 才用从最小匹配*/
		String regExpr = "\"order_id\":\".*?\"";
		String orgStr = "{\"order_req\":{\"order_id\":\"QTEST17090716385108880018\",\"menye\":\"100.00\"}";
		Matcher mt = Pattern.compile(regExpr).matcher(orgStr);
		System.out.println(">> " + mt.matches()); //整个匹配
		System.out.println(">> " + mt.find()); //配子串,
		
		String rstr = orgStr.replaceFirst(regExpr, "\"order_id\":\"will be replace!\"");
		System.out.println(">> rstr : "+rstr);
		
		
		
		
//      groupTest();
//      findReplace();
//      startEndMethod();
//    	greedyTest();
//    	reluctantTest();
//    	possessiveTest();
   }

    
    /**
      	捕获组
		捕获组是把多个字符当一个单独单元进行处理的方法，它通过对括号内的字符分组来创建。
		例如，正则表达式 (dog) 创建了单一分组，组里包含"d"，"o"，和"g"。
		捕获组是通过从左至右计算其开括号来编号。例如，在表达式（（A）（B（C））），有四个这样的组：
		((A)(B(C)))
		(A)
		(B(C))
		(C)
		可以通过调用 matcher 对象的 groupCount 方法来查看表达式有多少个分组。
		groupCount 方法返回一个 int 值，表示matcher对象当前有多个捕获组。
		还有一个特殊的组（group(0)），它总是代表整个表达式。
		该组不包括在 groupCount 的返回值中。
     */
	static void groupTest() {
		// 按指定模式在字符串查找
		  String line = "This order was placed for QT3000! OK?";
		  String pattern = "(\\D*)(\\d+)(.*)";
		  /*
		   		(\\D*) 	非数字： [^0-9]
		   		(\\d+) 	数字：[0-9]
		   		(.*)	匹配除了换行符以外的任意字符
		   */
 
		  // 创建 Pattern 对象
		  Pattern r = Pattern.compile(pattern);
 
		  // 现在创建 matcher 对象
		  Matcher m = r.matcher(line);
		  if (m.find( )) {
			  int c = m.groupCount();
			  System.out.println("group count : "+c);
			  
			  System.out.println("found value: " + m.group());//原组规则
		      System.out.println("Found value: " + m.group(0) );//原组规则
		      System.out.println("Found value: " + m.group(1) );
		      System.out.println("Found value: " + m.group(2) );
		      System.out.println("Found value: " + m.group(3) ); 
		     
		  } else {
		      System.out.println("NO MATCH");
		  }

	}


	/**
	 * 替换匹配词
	 */
	static void findReplace() {
		//匹配替换
		Pattern p = Pattern.compile("cat");
		Matcher m2 = p.matcher("one cat two cats in the yard");
		StringBuffer sb = new StringBuffer();
		while (m2.find()) {
		   m2.appendReplacement(sb, "dog");
		}
		m2.appendTail(sb);
		System.out.println(sb.toString());
		/*String#replaceAll 就是使用上面的方法*/
		  
		  
		String sql=	" SELECT co_id,co_name,batch_id,action_code,status,status_date,created_date,failure_desc,object_id FROM es_co_queue where service_code ='CO_SYNC_PACKAGE'  and source_from='ECS'  union all  select co_id,co_name,batch_id,action_code,status,status_date,created_date,failure_desc,object_id  from  es_co_queue_bak  where service_code ='CO_SYNC_PACKAGE'  and source_from='ECS'  order by created_date desc";
		String rstr = sql.replaceAll("(?<=select|SELECT)[\\s\\S]*?(?=from|FROM)", " 1 ");//Reluctant 数量词
		System.out.println(rstr);
	}
	
	/**正则中的数量词有Greedy (贪婪)、Reluctant(懒惰)和Possessive(强占)三种*/
	
	/** Greedy 数量词
	 *  X?	X，一次或一次也没有
		X*	X，零次或多次
		X+	X，一次或多次
		X{n}	X，恰好 n 次
		X{n,}	X，至少 n 次
		X{n,m}	X，至少 n 次，但是不超过 m 次
		Greedy是最常用的，它的匹配方式是先把整个字符串吞下，然后匹配整个字符串，如果不匹配，
		就从右端吐出一个字符，再进行匹配，直到找到匹配或把整个字符串吐完为止。
		
		因为总是从最大 匹配开始匹配，故称贪婪。
	 */
	static void greedyTest(){
		Matcher m=Pattern.compile("a.*b").matcher("a====b=========b=====");    
		while(m.find()){    
		    System.out.println(">>>greedyTest>>: "+m.group());    
		}    
		//输出：a====b=========b   
	}
	
	
	/** Reluctant 数量词
	 *  X??	X，一次或一次也没有
		X*?	X，零次或多次
		X+?	X，一次或多次
		X{n}?	X，恰好 n 次
		X{n,}?	X，至少 n 次
		X{n,m}?	X，至少 n 次，但是不超过 m 次
		
		Reluctant正好和Greedy相反，它先从最小匹配开始，先从左端吞入一个字符，
		然后进行匹配，若不匹配就再吞入一个字符，直到找到匹配或将整个字符串吞入为止。
		
		因为总是从最小匹配开始，故称懒惰。
	 */
	static void reluctantTest(){
		Matcher m=Pattern.compile("a.*?b").matcher("a====b=========b=====");    
		while(m.find()){    
		    System.out.println(">>>reluctantTest>>: "+m.group());    
		}    
		//输出：a====b   
	}
	
	/** Possessive 数量词
	 *  X?+	X，一次或一次也没有
		X*+	X，零次或多次
		X++	X，一次或多次
		X{n}+	X，恰好 n 次
		X{n,}+	X，至少 n 次
		X{n,m}+	X，至少 n 次，但是不超过 m 次
		Possessive和Greedy的匹配方式一样，先把整个字符串吞下，
		然后匹配整个字符串，如果匹配，就认为匹配，如果不匹配，就认为整个字符串不匹配，
		它不会从右端吐出一个字符串再进行匹配，只进行一次
		
		因为贪婪但并不聪明，故称强占。
	 */
	static void possessiveTest(){
		Matcher m=Pattern.compile("a.*+b").matcher("a====b=========b=====");  //只进行一次匹配，且不回搜，故匹配不到 ，无输出 
		Matcher m2=Pattern.compile("a.*+").matcher("a====b=========b=====");
		while(m2.find()){    
		    System.out.println(">>>possessiveTest>>: "+m2.group());    
		}    
		//输出：   
	}
	
	
	static void startEndMethod(){
		final String REGEX = "\\bcat\\b";
		final String INPUT = "cat cat cat cattie cat";
		
		Pattern p = Pattern.compile(REGEX);
       Matcher m = p.matcher(INPUT); // 获取 matcher 对象
       int count = 0;
 
       while(m.find()) {
         count++;
         System.out.println("Match number "+count);
         System.out.println("start(): "+m.start());
         System.out.println("end(): "+m.end());
      }
	}
	
}