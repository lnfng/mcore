package mcore.about_webservice.service;

import mcore.about_webservice.service.impl.HelloWordImpl;

import javax.xml.ws.Endpoint;


/**
 * 发布web Service
 * @author JackQ
 */
public class WebServiceMain {

	public static void main(String[] args) {
		HelloWord hw=new HelloWordImpl();
		Endpoint.publish("http://localhost/ws", hw);
		System.out.println("web Service 发布成功!");
	}

}
