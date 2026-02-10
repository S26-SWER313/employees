package com.lab.rest.employees;

import java.time.LocalDate;
import java.util.Objects;

import com.lab.rest.departments.Department;
import jakarta.persistence.*;

@Entity
public class Employee {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String name;
    private String role;
    @Column(unique = true)
    private String email;
    private double salary;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private Integer yearsOfExperience;
    private LocalDate hiredDate;

    public Employee() {}

    public Employee(String name, String role, String email, Integer yearsOfExperience, LocalDate hiredDate) {

        this.name = name;
        this.role = role;
        this.email = email;
        this.yearsOfExperience = yearsOfExperience;
        this.hiredDate = hiredDate;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getRole() {
        return this.role;
    }

    public String getEmail() {
        return email;
    }

    public Department getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public LocalDate getHiredDate() {
        return hiredDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setHiredDate(LocalDate hiredDate) {
        this.hiredDate = hiredDate;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Employee))
            return false;
        Employee employee = (Employee) o;
        return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
                && Objects.equals(this.role, employee.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.role);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
    }
}