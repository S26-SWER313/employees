package com.lab.rest;

import com.lab.rest.departments.Department;
import com.lab.rest.departments.DepartmentRepository;
import com.lab.rest.employees.Employee;
import com.lab.rest.employees.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository, DepartmentRepository departmentRepository) {

        return args -> {
            Department department = new Department();
            department.setName("Engineering");
            Employee emp1 = new Employee("Bilbo Baggins", "burglar");
            emp1.setDepartment(department);
            department.addEmployee(emp1);
            departmentRepository.save(department);
            repository.save(emp1);

            Employee emp2 = new Employee("Frodo Baggins", "thief");
            emp2.setDepartment(department);
            department.addEmployee(emp2);
            departmentRepository.save(department);
            repository.save(emp2);

            log.info("Loaded some data");
        };
    }
}