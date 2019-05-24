package mcore.about_thread.threadScope;

/**
 * 线程范围内的共享变量
 * @author JackQ
 *
 */
public class ThreadLocalDemo {
	
	public static void main(String[] args) {
		String connection = ConnectionUtils.getConnection();
		System.out.println(connection);
		
	}

}

class ConnectionUtils{
	/**只是用String 类型表示连接*/
	private static ThreadLocal<String> connection=new ThreadLocal<String>();
	
	private ConnectionUtils(){}
	
	public static String getConnection(){
		if(connection.get()==null){
			//产生一个连接，放到 connection中
			connection.set("current connection");
		}
		return connection.get();
	}
	
	public int getConnCount(){
		
		return 0;
	}
	
}

/*class DbUtils {

	private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();

	public static SqlSessionFactory createSeesionFactory() {
		String resource = "mybatis-config.xml";
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
				.build(reader);
		return sessionFactory;
	}

	//因为处理事物时需要同一个session
	public static SqlSession getSessioin() {

		if (threadLocal.get() == null) {
			threadLocal.set(DbUtils.createSeesionFactory().openSession());
		}
		return threadLocal.get();
	}

}*/
