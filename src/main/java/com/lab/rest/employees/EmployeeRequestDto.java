package com.lab.rest.employees;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class EmployeeRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String role;

    @NotBlank
    @Email
    private String email;


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
