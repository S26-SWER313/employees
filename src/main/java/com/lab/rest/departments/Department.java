package com.lab.rest.departments;

import com.lab.rest.employees.Employee;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    // Assuming a one-to-many relationship with Employee
    @OneToMany(mappedBy = "department")
    List<Employee> employees;

    public Department(){
        employees = new ArrayList<>();
    }
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee){
        employees.add(employee);
    }
}
