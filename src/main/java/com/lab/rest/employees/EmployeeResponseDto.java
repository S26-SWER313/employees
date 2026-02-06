package com.lab.rest.employees;

public class EmployeeResponseDto {

    private Long id;
    private String name;
    private String role;
    private String email;
    private String departmentName;

    public EmployeeResponseDto(Long id, String name, String role, String email, String departmentName) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.departmentName = departmentName;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getEmail() { return email; }
}
