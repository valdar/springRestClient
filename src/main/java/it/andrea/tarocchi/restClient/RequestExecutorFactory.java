package it.andrea.tarocchi.restClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RequestExecutorFactory {

	private static final String VALUE_SEPARATOR = ",";
	private RestTemplate restTemplate;
	private String url;
	private boolean autoIncrement;
	private String[] values;
	private long startingValue;
	private int numberOfRequests;

	public RequestExecutorFactory(RestTemplate restTemplate, String url,
			boolean autoIncrement, String rawValues, long startingValue, int numberOfRequests) {
		super();
		this.restTemplate = restTemplate;
		this.url = url;
		this.autoIncrement = autoIncrement;
		this.values = rawValues.split(VALUE_SEPARATOR);
		this.startingValue = startingValue;
		this.numberOfRequests = numberOfRequests;
	}
	
	void validateParameters(){
		if(!autoIncrement && values.length != numberOfRequests){
			throw new BeanInitializationException("If property autoIncrement=true then number of comma separeted values must be equal to numberOfRequests");
		}
	}
	
	List<Callable<String>> buildRequestExecutors(){
		List<Callable<String>> result = new ArrayList<Callable<String>>();
		if(autoIncrement){
			for(int i=0; i<this.numberOfRequests; i++){
				result.add(createCallable(String.valueOf(startingValue+i)));
			}
		}else{
			for(int i=0; i<this.numberOfRequests; i++){
				result.add(createCallable(values[i]));
			}
		}
		return result;
	}

	private Callable<String> createCallable(final String id){
		
		return new Callable<String>() {

			@Override
			public String call() {
				MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
				map.add("id", id);

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);      

				HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
				
				String response = restTemplate.postForObject(url, request, String.class);
				return "["+Thread.currentThread().getName()+"] "+response;
			}
			
		};
	}
}
