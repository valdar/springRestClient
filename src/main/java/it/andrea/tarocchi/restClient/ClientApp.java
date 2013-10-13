package it.andrea.tarocchi.restClient;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientApp {
	private static HttpPostClient client;
	
	public static void main(String[] args) {
		ClientApp clientApp = new ClientApp();
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("springApplicationContext.xml");

		applicationContext.getAutowireCapableBeanFactory().autowireBeanProperties(clientApp,
                AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true); 

		client.fireRequests();
	}

	public static void setClient(HttpPostClient client) {
		ClientApp.client = client;
	}
}
