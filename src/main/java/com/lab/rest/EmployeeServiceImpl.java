package com.lab.rest;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl  {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return repository.findAll();
    }

    public Employee create(Employee employee) {
        // Optional: ensure client doesn't control ID
        // employee.setId(null);
        return repository.save(employee);
    }

    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Employee findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException(email));
    }

    // Update-only: throws 404 if not found
    public Employee updateExisting(Long id, Employee newEmployee) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        existing.setName(newEmployee.getName());
        existing.setRole(newEmployee.getRole());
        // If you have email:
        // existing.setEmail(newEmployee.getEmail());

        return repository.save(existing);
    }

    public void deleteByIdOrThrow(Long id) {
        if (!repository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
