package mcore.about_webservice.service.impl;

import mcore.about_webservice.service.HelloWord;

import javax.jws.WebService;


@WebService(name="HelloWordImpl",
endpointInterface="mcore.about_webservice.service.HelloWord")
public class HelloWordImpl implements HelloWord {

	@Override
	public String sayHello(String name) {
		return name+" 你好!";
	}


}
