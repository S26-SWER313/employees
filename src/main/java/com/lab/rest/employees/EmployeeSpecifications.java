package com.lab.rest.employees;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class EmployeeSpecifications {
    public static Specification<Employee> hasRole(String role) {
        return (root, query, cb) ->
                role == null ? null : cb.equal(cb.lower(root.get("role")), role.toLowerCase());
    }

    public static Specification<Employee> nameContains(String nameContains) {
        return (root, query, cb) ->
                nameContains == null ? null : cb.like(cb.lower(root.get("name")), "%" + nameContains.toLowerCase() + "%");
    }

    public static Specification<Employee> experienceBetween(Integer min, Integer max) {
        return (root, query, cb) -> {
            if (min == null && max == null)
                return null;
            if (min != null && max != null)
                return cb.between(root.get("yearsOfExperience"), min, max);
            if (min != null)
                return cb.greaterThanOrEqualTo(root.get("yearsOfExperience"), min);
            return cb.lessThanOrEqualTo(root.get("yearsOfExperience"), max);
        };
    }

    public static Specification<Employee> hiredDateBetween(LocalDate after, LocalDate before) {
        return (root, query, cb) -> {
            if (after == null && before == null) return null;
            if (after != null && before != null) return cb.between(root.get("hiredDate"), after, before);
            if (after != null) return cb.greaterThanOrEqualTo(root.get("hiredDate"), after);
            return cb.lessThanOrEqualTo(root.get("hiredDate"), before);
        };
    }
}