package mcore.about_webservice.service;

import javax.jws.WebService;


@WebService
public interface HelloWord {
	
	String sayHello(String name);

}
