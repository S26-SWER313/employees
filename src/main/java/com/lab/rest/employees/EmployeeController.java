package com.lab.rest.employees;

import java.net.URI;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<EmployeeResponseDto>> listEmployees(
            // pagination + sorting handled automatically by Spring
            @PageableDefault(size = 10)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "id", direction = Sort.Direction.DESC)
            })
            Pageable pageable,

            // filters (optional)
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String nameContains
    ) {
        return ResponseEntity.ok(employeeService.listEmployees(pageable, role, nameContains));
    }

    // POST /employees -> 201 Created + Location: /employees/{id}
    @PostMapping
    ResponseEntity<EmployeeResponseDto> newEmployee(
            @Valid @RequestBody EmployeeRequestDto newEmployee,
            UriComponentsBuilder uriBuilder
    ) {
        Employee saved = employeeService.create(EmployeeMapper.toEntity(newEmployee));
        EmployeeResponseDto employeeResponseDto = EmployeeMapper.toDto(saved);

        URI location = uriBuilder
                .path("/employees/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(employeeResponseDto);
    }

    @GetMapping("/{id}")
    EmployeeResponseDto one(@PathVariable Long id) {
        return EmployeeMapper.toDto(employeeService.findById(id));
    }

    @GetMapping("/email/{email}")
    EmployeeResponseDto email(@PathVariable String email) {
        return EmployeeMapper.toDto(employeeService.findByEmail(email));
    }

    // PUT update-only: 200 OK on update; 404 if missing
    @PutMapping("/{id}")
    ResponseEntity<EmployeeResponseDto> replaceEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDto newEmployee
    ) {
        Employee updated = employeeService.updateExisting(id, EmployeeMapper.toEntity(newEmployee));
        return ResponseEntity.ok(EmployeeMapper.toDto(updated));
    }

    // DELETE -> 204 No Content; 404 if missing
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteByIdOrThrow(id);
        return ResponseEntity.noContent().build();
    }
}
