package com.example.Completable.Future;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.Completable.Future.dto.Employee;
import com.example.Completable.Future.repository.EmployeeRepository;

public class SupplyAsyncDemo {
    public List<Employee> getEmployees() throws InterruptedException, ExecutionException{
        ExecutorService executor = Executors.newCachedThreadPool();
        CompletableFuture<List<Employee>> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Executed By: "+ Thread.currentThread().getName());
            return EmployeeRepository.fetchEmployee();
        },executor);
        executor.shutdown();
        return completableFuture.get();
    }

    public void testParallel() throws InterruptedException, ExecutionException {
        LocalDateTime start = LocalDateTime.now();
        ExecutorService executor = Executors.newCachedThreadPool();
        CompletableFuture<List<Integer>> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            List<Integer> list = new ArrayList<>();
            for(int i=0;i<10;i++)
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("Inside Thread: "+ Thread.currentThread().getName());
                list.add(i);
            }
            return list;
        },executor);
        CompletableFuture<List<Integer>> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            List<Integer> list = new ArrayList<>();
            for(int i=10;i<20;i++)
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("Inside Thread: "+ Thread.currentThread().getName());
                list.add(i);
            }
            return list;
        },executor);
        List<Integer> lst2=completableFuture2.get();
        List<Integer> lst1=completableFuture1.get();
        lst1.addAll(lst2);
        lst1.stream().forEach(System.out::println);
        executor.shutdown();
        LocalDateTime end = LocalDateTime.now();
        long timeTaken = ChronoUnit.SECONDS.between(start, end);
        System.out.println("Time Taken: "+ timeTaken);
    }
}
