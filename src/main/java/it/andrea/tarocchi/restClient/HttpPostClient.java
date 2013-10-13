package it.andrea.tarocchi.restClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

public class HttpPostClient {
	static Logger log = Logger.getLogger(HttpPostClient.class.getName());

	private RequestExecutorFactory reqFactory;
	
	public void fireRequests() {
		ExecutorService executorPool = Executors.newCachedThreadPool();
		List<Future<String>> results = new ArrayList<Future<String>>(); 
		for(Callable<String> requestExecutor : reqFactory.buildRequestExecutors()){		
			results.add(executorPool.submit(requestExecutor));
		}
		int number = 0;
		for(Future<String> result: results){
			try {
				log.info("result "+number+": "+result.get());
			} catch (InterruptedException e) {
				log.info("result "+number+" InterruptedException caused by: "+e.getCause());
			} catch (ExecutionException e) {
				log.info("result "+number+" ExecutionException caused by: "+e.getCause());
			}
			number++;
		}
		executorPool.shutdownNow();
	}
	
	public RequestExecutorFactory getReqFactory() {
		return reqFactory;
	}

	public void setReqFactory(RequestExecutorFactory reqFactory) {
		this.reqFactory = reqFactory;
	}
}
