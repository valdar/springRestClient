package it.andrea.tarocchi.restClient;

import it.andrea.tarocchi.restClient.RequestExecutorFactory;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.web.client.RestTemplate;

public class RestClientConfigurationTest {

	@Test
	public void testClientAutoIncrementConfiguration() {
		String url = "http://localhost/andreaServer"; 
		boolean autoIncrement = true;
		String values = ""; 
		int startingValue = 0; 
		int numberOfRequests = 1;
		RestTemplate fakeRestTemplate = new RestTemplate();
		RequestExecutorFactory fakeRequestFactory = new RequestExecutorFactory(fakeRestTemplate, url, autoIncrement, values, startingValue, numberOfRequests);
		
		fakeRequestFactory.validateParameters();
		Assert.assertNotNull(fakeRequestFactory.buildRequestExecutors());
		Assert.assertEquals(1,fakeRequestFactory.buildRequestExecutors().size());
	}
	
	@Test
	public void testClientNotAutoIncrementConfiguration() {
		String url = "http://localhost/andreaServer"; 
		boolean autoIncrement = false;
		String values = "11,22,33"; 
		int startingValue = 0; 
		int numberOfRequests = 3;
		RestTemplate fakeRestTemplate = new RestTemplate();
		RequestExecutorFactory fakeRequestFactory = new RequestExecutorFactory(fakeRestTemplate, url, autoIncrement, values, startingValue, numberOfRequests);
		
		fakeRequestFactory.validateParameters();
		Assert.assertNotNull(fakeRequestFactory.buildRequestExecutors());
		Assert.assertEquals(3,fakeRequestFactory.buildRequestExecutors().size());
	}
	
	@Test(expected=BeanInitializationException.class)
	public void testClientBadConfiguration() {
		String url = "http://localhost/andreaServer"; 
		boolean autoIncrement = false;
		String values = "11,22,33"; 
		int startingValue = 0; 
		int numberOfRequests = 2;
		RestTemplate fakeRestTemplate = new RestTemplate();
		RequestExecutorFactory fakeRequestFactory = new RequestExecutorFactory(fakeRestTemplate, url, autoIncrement, values, startingValue, numberOfRequests);
		
		fakeRequestFactory.validateParameters();
	}
}
