package com.lab.rest.employees;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee create(Employee employee);
    Employee findById(Long id);
    Employee findByEmail(String email);
    Employee updateExisting(Long id, Employee newEmployee);
    void deleteByIdOrThrow(Long id);

    }
