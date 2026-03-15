package com.lab.rest;

import com.lab.rest.departments.Department;
import com.lab.rest.departments.DepartmentRepository;
import com.lab.rest.employees.Employee;
import com.lab.rest.employees.EmployeeRepository;
import com.lab.rest.security.AppUser;
import com.lab.rest.security.AppUserRepository;
import com.lab.rest.security.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

//seed database with sample data on startup
@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private static final int EMPLOYEE_COUNT = 20;

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository, DepartmentRepository departmentRepository,
                                   AppUserRepository repo, PasswordEncoder encoder) {

        return args -> {
            List<Department> departments = new ArrayList<>();
            String[] deptNames = {"Engineering", "HR", "Sales", "Support", "R&D"};
            for (String dn : deptNames) {
                Department department = departmentRepository.findAll().stream()
                        .filter(existing -> dn.equals(existing.getName()))
                        .findFirst()
                        .orElseGet(() -> {
                            Department created = new Department();
                            created.setName(dn);
                            return departmentRepository.save(created);
                        });
                departments.add(department);
            }

            String[] firstNames = {"Bilbo", "Frodo", "Samwise", "Meriadoc", "Peregrin", "Aragorn", "Legolas", "Gimli", "Boromir", "Elrond"};
            String[] lastNames = {"Baggins", "Brandybuck", "Took", "Gamgee", "Oakenshield", "Elessar", "Greenleaf", "SonOfGloin", "Steward", "Halfelven"};
            String[] roles = {"engineer", "developer", "manager", "analyst", "tester", "support", "sysadmin", "designer"};

            Random random = new Random();
            int employeesCreated = 0;
            int usersCreated = 0;

            for (int i = 1; i <= EMPLOYEE_COUNT; i++) {
                final int employeeNumber = i;
                String first = firstNames[(employeeNumber - 1) % firstNames.length];
                String last = lastNames[(employeeNumber - 1) % lastNames.length];
                String name = first + " " + last + " #" + employeeNumber;
                String role = roles[(employeeNumber - 1) % roles.length];
                String emailLocal = (first + "." + last + employeeNumber).toLowerCase().replaceAll("[^a-z0-9.]", "");
                String email = emailLocal + "@example.com";

                Employee employee = repository.findByEmail(email).orElseGet(() -> {
                    int daysBack = random.nextInt(365 * 15 + 1);
                    LocalDate hiredDate = LocalDate.now().minusDays(daysBack);
                    int yearsOfExperience = Period.between(hiredDate, LocalDate.now()).getYears();

                    Employee emp = new Employee();
                    emp.setName(name);
                    emp.setRole(role);
                    emp.setEmail(email);
                    emp.setHiredDate(hiredDate);
                    emp.setYearsOfExperience(yearsOfExperience);
                    emp.setSalary(45000 + random.nextInt(55001));

                    Department dept = departments.get((employeeNumber - 1) % departments.size());
                    emp.setDepartment(dept);
                    dept.addEmployee(emp);

                    return repository.save(emp);
                });

                if (repo.findByUsername("employee" + employeeNumber).isEmpty()) {
                    repo.save(new AppUser(
                            null,
                            "employee" + employeeNumber,
                            encoder.encode("Emp@123"),
                            true,
                            Set.of(Role.EMPLOYEE),
                            employee.getId()
                    ));
                    usersCreated++;
                }

                if (employee.getId() != null && repository.findByEmail(email).isPresent()) {
                    employeesCreated++;
                }
            }

            if (repo.findByUsername("admin").isEmpty()) {
                repo.save(new AppUser(null, "admin", encoder.encode("Admin@123"), true, Set.of(Role.ADMIN), null));
            }
            if (repo.findByUsername("hr").isEmpty()) {
                repo.save(new AppUser(null, "hr", encoder.encode("Hr@123"), true, Set.of(Role.HR), null));
            }

            log.info("Seeded {} employees and {} employee user accounts", employeesCreated, usersCreated);
        };
    }
}
