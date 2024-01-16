package com.example.Completable.Future;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.example.Completable.Future.dto.Employee;
import com.example.Completable.Future.repository.EmployeeRepository;

public class EmployeeReminderService {
    public static void main(String... args) throws InterruptedException, ExecutionException{
        EmployeeReminderService service = new EmployeeReminderService();

        // send reminder in single thread
        // LocalDateTime start = LocalDateTime.now();
        // CompletableFuture<Void> future = service.sendReminderToEmployee();
        // future.get();
        // LocalDateTime end = LocalDateTime.now();
        // System.out.println(ChronoUnit.MILLIS.between(start, end));

        // send reminder in multiple threads.
        LocalDateTime startAsync = LocalDateTime.now();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CompletableFuture<Void> future = service.sendReminderToEmployeeAsync(executor);
        future.get();
        executor.shutdown();
        LocalDateTime endAsync = LocalDateTime.now();
        System.out.println(ChronoUnit.MILLIS.between(startAsync, endAsync));

        /*
         * PENDING
         * Combining multiple future objects together
         * thenCompose()
         * thenCombine()
         * allOf()
         * anyOf()
         */

    }

    private CompletableFuture<Void> sendReminderToEmployeeAsync(ExecutorService executor) {
       
        // multiple executors can also be passed but not recommended.
        // ExecutorService executor1 = Executors.newFixedThreadPool(5);
        // ExecutorService executor2 = Executors.newFixedThreadPool(5);
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching employee: "+ Thread.currentThread().getName());
            return EmployeeRepository.fetchEmployee();
        },executor).thenApplyAsync(employees -> {
            System.out.println("Filter new Joiners: "+ Thread.currentThread().getName());
            return employees.stream()
            .filter(employee -> employee.getNewJoiner().equals("TRUE"))
            .collect(Collectors.toList());
        },executor).thenApplyAsync(employees ->{
            System.out.println("Filter new Joiners with pending training: "+ Thread.currentThread().getName());
            return employees.stream()
            .filter(employee -> employee.getLearningPending().equals("TRUE"))
            .collect(Collectors.toList());
        },executor).thenApplyAsync(employees -> {
            System.out.println("Fetch Emails: "+ Thread.currentThread().getName());
            return employees.stream()
            .map(Employee::getEmail)
            .collect(Collectors.toList());
        },executor).thenAcceptAsync(emails -> {
            System.out.println("send email  : " + Thread.currentThread().getName());
            emails.stream().map(email -> "Sending training reminder email to: " + email)
            .forEach(System.out::println);
        },executor);
        return future;
    }

    public CompletableFuture<Void> sendReminderToEmployee(){

        // Here everyting will be executed in a single thread synchronously
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching employee: "+ Thread.currentThread().getName());
            return EmployeeRepository.fetchEmployee();
        }).thenApply(employees -> {
            System.out.println("Filter new Joiners: "+ Thread.currentThread().getName());
            return employees.stream()
            .filter(employee -> employee.getNewJoiner().equals("TRUE"))
            .collect(Collectors.toList());
        }).thenApply(employees ->{
            System.out.println("Filter new Joiners with pending training: "+ Thread.currentThread().getName());
            return employees.stream()
            .filter(employee -> employee.getLearningPending().equals("TRUE"))
            .collect(Collectors.toList());
        }).thenApply(employees -> {
            System.out.println("Fetch Emails: "+ Thread.currentThread().getName());
            return employees.stream()
            .map(Employee::getEmail)
            .collect(Collectors.toList());
        }).thenAccept(emails -> {
            emails.stream().map(email -> "Sending training reminder email to: " + email)
            .forEach(System.out::println);
        });
        return future;
    }
}
