package com.example.Completable.Future;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
}
