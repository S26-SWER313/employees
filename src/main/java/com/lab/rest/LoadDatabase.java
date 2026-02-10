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

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository, DepartmentRepository departmentRepository) {

        return args -> {
            // Prepare departments
            List<Department> departments = new ArrayList<>();
            String[] deptNames = {"Engineering", "HR", "Sales", "Support", "R&D"};
            for (String dn : deptNames) {
                Department d = new Department();
                d.setName(dn);
                departmentRepository.save(d);
                departments.add(d);
            }

            // Sample name parts and roles
            String[] firstNames = {"Bilbo", "Frodo", "Samwise", "Meriadoc", "Peregrin", "Aragorn", "Legolas", "Gimli", "Boromir", "Elrond"};
            String[] lastNames = {"Baggins", "Brandybuck", "Took", "Gamgee", "Oakenshield", "Elessar", "Greenleaf", "SonOfGloin", "Steward", "Halfelven"};
            String[] roles = {"engineer", "developer", "manager", "analyst", "tester", "support", "sysadmin", "designer"};

            Random random = new Random();

            // Create 50 employees
            for (int i = 1; i <= 50; i++) {
                String first = firstNames[(i - 1) % firstNames.length];
                String last = lastNames[(i - 1) % lastNames.length];
                String name = first + " " + last + " #" + i;
                String role = roles[(i - 1) % roles.length];

                // generate email
                String emailLocal = (first + "." + last + i).toLowerCase().replaceAll("[^a-z0-9\\.]", "");
                String email = emailLocal + "@example.com";

                // random hired date within the last 15 years
                int daysBack = random.nextInt(365 * 15 + 1);
                LocalDate hiredDate = LocalDate.now().minusDays(daysBack);

                // compute years of experience from hiredDate
                int yearsOfExperience = Period.between(hiredDate, LocalDate.now()).getYears();

                // construct employee using setters (no-arg or existing setters assumed)
                Employee emp = new Employee();
                emp.setName(name);
                emp.setRole(role);
                emp.setEmail(email);
                emp.setHiredDate(hiredDate);
                emp.setYearsOfExperience(yearsOfExperience);

                // assign to a department in round-robin
                Department dept = departments.get((i - 1) % departments.size());
                emp.setDepartment(dept);
                dept.addEmployee(emp);

                // persist department and employee
                departmentRepository.save(dept);
                repository.save(emp);
            }

            log.info("Loaded 50 employees with email, hiredDate and yearsOfExperience");
        };
    }
}
