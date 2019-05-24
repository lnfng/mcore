package mcore.about_dom.dom4j;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4jDemo1 {
	public static void main(String[] args) throws Exception {
		//1.
		SAXReader reader = new SAXReader();
		//2.
		Document dom = reader.read("book.xml");
		//3.
		Element root = dom.getRootElement();
		//4.
		String bookName = root.element("first").element("second").getText();
		System.out.println(bookName);
	}
}
