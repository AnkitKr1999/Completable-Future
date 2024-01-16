package com.example.Completable.Future;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.Completable.Future.dto.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RunAsyncDemo {

    public Void saveEmployees(File jsonFile) throws InterruptedException, ExecutionException{
        ObjectMapper mapper = new ObjectMapper();
        CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(()->{
            try {
                List<Employee> employees = mapper.readValue(jsonFile,new TypeReference<List<Employee>>(){});
                System.out.println("Thread: "+ Thread.currentThread().getName());
                System.out.println(employees.size());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        return runAsyncFuture.get();
    }

    public void saveEmployeesWithExecutor(File jsonFile) throws InterruptedException, ExecutionException{
        ObjectMapper mapper = new ObjectMapper();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<CompletableFuture> completableFutures = new ArrayList<>();
        CompletableFuture<Void> runAsyncFuture1 = CompletableFuture.runAsync(()->{
            try {
                List<Employee> employees = mapper.readValue(jsonFile,new TypeReference<List<Employee>>(){});
                System.out.println("Thread: "+ Thread.currentThread().getName());
                System.out.println(employees.size());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        },executor);
        completableFutures.add(runAsyncFuture1);
        CompletableFuture<Void> runAsyncFuture2 = CompletableFuture.runAsync(()->{
            try {
                List<Employee> employees = mapper.readValue(jsonFile,new TypeReference<List<Employee>>(){});
                System.out.println("Thread: "+ Thread.currentThread().getName());
                System.out.println(employees.size());
                System.out.println(10/0);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        },executor);
        completableFutures.add(runAsyncFuture2);
        CompletableFuture.allOf().join();
        System.out.println("Inside Parent Thread: "+  Thread.currentThread().getName());
        executor.shutdown();
    }
}
