package ir.mohaymen.messenger.controllers;

import ir.mohaymen.messenger.dto.AuthResponse;
import ir.mohaymen.messenger.dto.LoginRequest;
import ir.mohaymen.messenger.dto.SigninRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class UserControllerTests {
    private final UserController userController;

    @Autowired
    public UserControllerTests(UserController userController) {
        this.userController = userController;
    }

    @Test
    public void login() {
        //given
        LoginRequest request = LoginRequest.builder()
                .name("ali")
                .username("9987654321")
                .password("Aa123456789")
                .build();

        //when
        ResponseEntity<AuthResponse> response = userController.login(request);

        //then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isGreaterThan(0);
        Assertions.assertThat(response.getBody().getToken()).isNotNull();
    }

    @Test
    public void signin() {
        //given
        LoginRequest loginRequest = LoginRequest.builder()
                .name("ali")
                .username("9987654321")
                .password("Aa123456789")
                .build();
        userController.login(loginRequest);

        SigninRequest request = SigninRequest.builder()
                .username("9987654321")
                .password("Aa123456789")
                .build();

        //when
        ResponseEntity<AuthResponse> response = userController.signin(request);

        //then
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isGreaterThan(0);
        Assertions.assertThat(response.getBody().getToken()).isNotNull();
    }
}
