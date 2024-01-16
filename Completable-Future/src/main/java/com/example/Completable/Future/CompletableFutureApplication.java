package com.example.Completable.Future;

import java.io.File;
import java.util.concurrent.ExecutionException;

// import org.springframework.boot.SpringApplication/;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
public class CompletableFutureApplication {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// SpringApplication.run(CompletableFutureApplication.class, args);

		// understading completable Future runAsync
		workingWithRunAsync();
		
	}

	private static void workingWithRunAsync() throws InterruptedException, ExecutionException  {
		RunAsyncDemo runAsyncDemo = new RunAsyncDemo();
		runAsyncDemo.saveEmployees(new File("Completable-Future\\employees.json"));
		runAsyncDemo.saveEmployeesWithExecutor(new File("Completable-Future\\employees.json"));
	}



}
