package mcore.about_io;
import java.io.*;
/**
 * writeObject 要使该对象实现序列化
 * 类通过实现 java.io.Serializable 接口以启用其序列化功能。
 * 未实现此接口的类将无法使其任何状态序列化或反序列化。
 * 可序列化类的所有子类型本身都是可序列化的。
 * 
 * 静态的方法，字段和静态内部类都不可序列化，只能把堆区的内容序列化
 * 方法区无法序列化
 * 
 * private static final long serialVersionUID = 5250416723503229541L;
 * 序列化UID是给编译器用来识别类的，它是通过类Field(方法和字段)来生成的,故field
 * 发生的变化都会影响UID，方法内部的改变是不会影响的。
 * 如果不加UID，默认会通过类成员来生成，这样的话，当类成员在反序列化之前发生了
 * 变化，反序列化会发生异常。
 * 所以最好加上UID
 * 
 * 帮助文档：
 *   * 列化运行时使用一个称为 serialVersionUID 的版本号与每个可序列化类相关联，
	 * 该序列号在反序列化过程中用于验证序列化对象的发送者和接收者是否为该对象加载了
	 * 与序列化兼容的类。如果接收者加载的该对象的类的 serialVersionUID 
	 * 与对应的发送者的类的版本号不同，则反序列化将会导致 InvalidClassException。
	 * 可序列化类可以通过声明名为 "serialVersionUID" 的字段（该字段必须是静态 (static)、
	 * 最终 (final) 的 long 型字段）显式声明其自己的 serialVersionUID：

 	ANY-ACCESS-MODIFIER static final long serialVersionUID = 42L;
 	
 	如果可序列化类未显式声明 serialVersionUID，则序列化运行时将基于该类的各个方面计算
 	该类的默认 serialVersionUID 值，如“Java(TM) 对象序列化规范”中所述。
 	不过，强烈建议 所有可序列化类都显式声明 serialVersionUID 值，
 	原因是计算默认的 serialVersionUID 对类的详细信息具有较高的敏感性，
 	根据编译器实现的不同可能千差万别，这样在反序列化过程中
 	可能会导致意外的 InvalidClassException。
 	因此，为保证 serialVersionUID 值跨不同 java 编译器实现的一致性，
 	序列化类必须声明一个明确的 serialVersionUID 值。
 	还强烈建议使用 private 修饰符显示声明 serialVersionUID（如果可能），
 	原因是这种声明仅应用于直接声明类 -- serialVersionUID 字段作为继承成员没有用处。
 	数组类不能声明一个明确的 serialVersionUID，因此它们总是具有默认的计算值，
 	但是数组类没有匹配 serialVersionUID 值的要求。
 * @author JackQ
 *
 */
public class ObjectStreamDemo {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		writeObject();
		readObject();
	}

	private static void readObject() throws IOException, ClassNotFoundException{
		ObjectInputStream ois=
				new ObjectInputStream(new FileInputStream("test//object.txt"));
		Person p=(Person) ois.readObject();
		ois.close();
		System.out.println(p);
	}

	private static void writeObject() throws IOException, FileNotFoundException {
		ObjectOutputStream oos=
				new ObjectOutputStream(new FileOutputStream("test//object.txt"));
		oos.writeObject(new Person("ZhaoLiu", 27,"ZhongGuo"));
		oos.close();
	}

}

class Person implements Serializable{
	
	//private static final long serialVersionUID = 1L;
	private static final long serialVersionUID = 5250416723503229541L;
	private String name;
	//transient意思是瞬变，加上这个修饰，就不能被序列化了
	//瞬变的和静态的都不能被序列化
	private transient int age;
	private String country;
	
	public Person(){}
	
	public Person(String name,int age,String country) {
		this.name=name;
		this.age=age;
		this.country=country;
	}
	public String toString(){
		return "name:"+name+" age:"+age+" country:"+country;
	}
	
	public void test(){
		System.out.println("isokay");
	}
}