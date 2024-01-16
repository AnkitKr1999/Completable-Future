package com.example.Completable.Future.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.example.Completable.Future.dto.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeRepository {
    public static List<Employee> fetchEmployee() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("Completable-Future\\employees.json"), new TypeReference<List<Employee>>(){});
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
