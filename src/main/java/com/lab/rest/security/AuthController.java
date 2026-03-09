package com.lab.rest.security;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest req) {
        return ResponseEntity.ok(authService.login(req.username(), req.password()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody @Valid RefreshTokenRequest req) {
        return ResponseEntity.ok(authService.refreshToken(req.refreshToken()));
    }

    @PostMapping("/revoke")
    public ResponseEntity<Void> revoke(@RequestBody @Valid RefreshTokenRequest req) {
        authService.revokeRefreshToken(req.refreshToken());
        return ResponseEntity.noContent().build();
    }
}
