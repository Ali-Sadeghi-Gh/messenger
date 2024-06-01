package ir.mohaymen.messenger.controllers;

import ir.mohaymen.messenger.dto.AuthResponse;
import ir.mohaymen.messenger.dto.LoginRequest;
import ir.mohaymen.messenger.dto.SigninRequest;
import ir.mohaymen.messenger.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping(path = "login")
    public ResponseEntity<AuthResponse> register(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @PostMapping(path = "signin")
    public ResponseEntity<AuthResponse> login(@RequestBody SigninRequest request) {
        return userService.signin(request);
    }
}
