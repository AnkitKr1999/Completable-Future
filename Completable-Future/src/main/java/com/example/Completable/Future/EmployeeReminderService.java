package com.example.Completable.Future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.example.Completable.Future.dto.Employee;
import com.example.Completable.Future.repository.EmployeeRepository;

public class EmployeeReminderService {
    public static void main(String... args) throws InterruptedException, ExecutionException{
        EmployeeReminderService service = new EmployeeReminderService();

        // send reminder in single thread
        CompletableFuture<Void> future = service.sendReminderToEmployee();
        future.get();
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
