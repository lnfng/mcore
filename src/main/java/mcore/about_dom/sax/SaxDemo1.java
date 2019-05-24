package mcore.about_dom.sax;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class SaxDemo1 {
	public static void main(String[] args) throws Exception {
		//1.
		SAXParserFactory factory = SAXParserFactory.newInstance();
		//2.
		SAXParser parser = factory.newSAXParser();
		//3.
		XMLReader reader = parser.getXMLReader();
		
		reader.setContentHandler(new MyContentHandler2() );
//		reader.setContentHandler(new MyContentHandler() );
		//5.
		reader.parse("book.xml");
//		reader.parse(//
//			new InputSource(new BufferedReader(
//				new InputStreamReader(new FileInputStream("book.xml"),"utf-8"))));
	}
}

//
class MyContentHandler2 extends DefaultHandler{
	private String eleName = null;
	private int count = 0;
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		System.out.println("uri:"+uri+"  localName:"+localName+" attr:"+attributes.getValue("id"));
		this.eleName = name;
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		System.out.println(new String(ch,start,length));
	}
	
	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		eleName = null;
	}
	
}




class MyContentHandler implements ContentHandler{

	public void startDocument() throws SAXException {
		System.out.println("开始解析.......");
	}
	
	public void startElement(String uri, String localName, String name,
			Attributes atts) throws SAXException {
		System.out.println("开始元素"+name);
	}
	
	public void characters(char[] ch, int start, int length)
		throws SAXException {
		System.out.println("The content: "+new String(ch,start,length));
	}
	
	public void endElement(String uri, String localName, String name)
		throws SAXException {
		System.out.println("结束元素"+name);
	}

	
	public void endDocument() throws SAXException {
		System.out.println("结束解析.......");
	}
	
	
	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
		
	}

	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	

	

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}
	
}
