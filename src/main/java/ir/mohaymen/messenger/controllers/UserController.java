package ir.mohaymen.messenger.controllers;

import ir.mohaymen.messenger.dto.AuthResponse;
import ir.mohaymen.messenger.dto.LoginRequest;
import ir.mohaymen.messenger.dto.SigninRequest;
import ir.mohaymen.messenger.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(path = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @PostMapping(path = "signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody SigninRequest request) {
        return userService.signin(request);
    }
}
