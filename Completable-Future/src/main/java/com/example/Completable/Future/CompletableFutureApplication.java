package com.example.Completable.Future;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.Completable.Future.dto.Employee;

// import org.springframework.boot.SpringApplication/;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
public class CompletableFutureApplication {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// SpringApplication.run(CompletableFutureApplication.class, args);

		// understanding completable Future runAsync
		// workingWithRunAsync();

		// understanding cmmpletable future supplyAsync
		workingWithSupplyAsync();
		
	}

	private static void workingWithSupplyAsync() throws InterruptedException, ExecutionException {
		SupplyAsyncDemo supplyAsyncDemo = new SupplyAsyncDemo();
		List<Employee> employees = supplyAsyncDemo.getEmployees();
		// employees.stream().forEach(System.out::println);
		System.out.println(employees.size());

		supplyAsyncDemo.testParallel();
	}

	private static void workingWithRunAsync() throws InterruptedException, ExecutionException  {
		RunAsyncDemo runAsyncDemo = new RunAsyncDemo();
		runAsyncDemo.saveEmployees(new File("Completable-Future\\employees.json"));
		runAsyncDemo.saveEmployeesWithExecutor(new File("Completable-Future\\employees.json"));
	}



}
