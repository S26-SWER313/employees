package com.lab.rest;

import java.time.Instant;

import com.lab.rest.employees.EmployeeNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(
            EmployeeNotFoundException ex,
            HttpServletRequest request
    ) {
        ApiError body = new ApiError(
                Instant.now().toString(),
                404,
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // Optional: catch-all (prevents stack traces from leaking to clients)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        ApiError body = new ApiError(
                Instant.now().toString(),
                500,
                "Internal Server Error",
                "Something went wrong",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
