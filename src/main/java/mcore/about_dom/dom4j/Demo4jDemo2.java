package mcore.about_dom.dom4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

public class Demo4jDemo2 {

	@Test
	public void attr() throws Exception{
		SAXReader reader = new SAXReader();
		Document dom = reader.read("book.xml");
		Element root = dom.getRootElement();
		
		Element bookEle = root.element("");
		//bookEle.addAttribute("", "");
//		String str = bookEle.attributeValue("");
//		System.out.println(str);
		Attribute attr = bookEle.attribute("");
		attr.getParent().remove(attr);
		
		XMLWriter writer = new XMLWriter(new FileOutputStream("book.xml"),OutputFormat.createPrettyPrint());
		writer.write(dom);
		writer.close();
	}
	
	public void del() throws Exception{
		SAXReader reader = new SAXReader();
		Document dom = reader.read("book.xml");
		Element root = dom.getRootElement();
		
		Element price2Ele = root.element("").element("");
		price2Ele.getParent().remove(price2Ele);

		XMLWriter writer = new XMLWriter(new FileOutputStream("book.xml"),OutputFormat.createPrettyPrint());
		writer.write(dom);
		writer.close();
	}
	
	public void update()throws Exception{
		SAXReader reader = new SAXReader();
		Document dom = reader.read("book.xml");
		Element root = dom.getRootElement();
		
		root.element("").element("").setText("4.0");
		
		XMLWriter writer = new XMLWriter(new FileOutputStream("book.xml"),OutputFormat.createPrettyPrint());
		writer.write(dom);
		writer.close();
	}
	
	public void add()throws Exception{
		SAXReader reader = new SAXReader();
		Document dom = reader.read("book.xml");
		Element root = dom.getRootElement();
		//<>,
		Element price2Ele = DocumentHelper.createElement("");
		price2Ele.setText("40.0");
		//<>
		Element bookEle = root.element("");
		bookEle.add(price2Ele);
		
//		FileWriter writer = new FileWriter("book.xml");
//		dom.write(writer);
//		writer.flush();
//		writer.close();
		XMLWriter writer = new XMLWriter(new FileOutputStream("book.xml"),OutputFormat.createPrettyPrint());
		writer.write(dom);
		writer.close();
	}
	
	public void find() throws Exception{
		SAXReader reader = new SAXReader();
		Document dom = reader.read("book.xml");
		Element root = dom.getRootElement();
		
		List<Element> list =  root.elements();
		Element book2Ele = list.get(1);
		System.out.println(book2Ele.element("").getText());
		
	}
	
	/**
	 *  doc
	 * @param document
	 */
	public static void printPrettyDom(Document document) {
		StringWriter writer = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter xmlwriter = new XMLWriter(writer, format);
		try{
			xmlwriter.write(document);
			System.out.println(">>>  XML >>> {}"+writer.toString());
		}catch(Exception e){
			System.out.println(">>>  XML >>> {}"+e);
		}finally{
			try {	
				xmlwriter.close();writer.close(); 
			} catch (IOException e) {
				xmlwriter=null;writer=null;
			}
		}
	}
	
	/**
	 * Document
	 * @author Qian
	 */
	public static void DomcumentWay() throws Exception{
		
		/**
		 * 
		 */
		FileInputStream fis=new FileInputStream("book.xml");
		Document dom = new SAXReader().read(fis);
		
		/**
		 * XMLDocument
		 */
		Document document = DocumentHelper.parseText("<xml version=\"1.0\" encoding=\"UTF-8\"><content>xml content</content>");
		
		/**
		 * xpath
		 */
		System.out.println(document.asXML());
		List nodes = document.selectNodes("/content");
		Element elem = (Element) nodes.get(0);
		System.out.println(""+elem.getName());
	}
	
	
	public static void main(String[] args) throws Exception {
		DomcumentWay() ;
	}
	
	
	
	
}
