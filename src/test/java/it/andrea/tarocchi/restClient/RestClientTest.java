package it.andrea.tarocchi.restClient;

import it.andrea.tarocchi.restClient.HttpPostClient;
import it.andrea.tarocchi.restClient.RequestExecutorFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;



//MockMvcResultMatchers.* MockMvcRequestBuilders.* MockMultipartHttpServletRequestBuilder.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/springApplicationContext.xml")
public class RestClientTest {

	@Autowired
	private HttpPostClient hpc;

	@Test
	public void testClient() {
		String url = "http://localhost/andreaServer"; 
		boolean autoIncrement = true;
		String values = ""; 
		int startingValue = 0; 
		int numberOfRequests = 1;
		RestTemplate fakeRestTemplate = new RestTemplate();
		MockRestServiceServer mockServer =  MockRestServiceServer.createServer(fakeRestTemplate);
		mockServer.expect(requestTo("http://localhost/andreaServer"))
					.andExpect(content().string("id=0"))
					.andRespond(withSuccess("1",MediaType.APPLICATION_FORM_URLENCODED));
		RequestExecutorFactory fakeRequestFactory = new RequestExecutorFactory(fakeRestTemplate, url, autoIncrement, values, startingValue, numberOfRequests);
		
		hpc.setReqFactory(fakeRequestFactory);
		
		hpc.fireRequests();
		
		mockServer.verify();
	}
	
	@Test
	public void testClientReal() {
		
		hpc.fireRequests();
	}
}
