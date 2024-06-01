package ir.mohaymen.messenger.services;

import ir.mohaymen.messenger.dto.AuthResponse;
import ir.mohaymen.messenger.dto.LoginRequest;
import ir.mohaymen.messenger.dto.SigninRequest;
import ir.mohaymen.messenger.entities.Role;
import ir.mohaymen.messenger.entities.UserEntity;
import ir.mohaymen.messenger.repositories.UserRepository;
import ir.mohaymen.messenger.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public ResponseEntity<AuthResponse> login(LoginRequest request) throws ResponseStatusException {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "duplicate phone number");
        }
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .name(request.getName())
                .build();
        userRepository.save(user);
        log.info("User with id " + user.getId() + " and phone number " + user.getUsername() + " logged in.");
        return signin(SigninRequest.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build());
    }

    public ResponseEntity<AuthResponse> signin(SigninRequest request) throws ResponseStatusException {
        String username = request.getUsername();
        String password = request.getPassword();
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "wrong username or password");
        }
        UserEntity user = optionalUser.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "wrong username or password");
        }
        userRepository.save(user);
        log.info("User with id " + user.getId() + " and phone number " + user.getUsername() + " signed in");
        return ResponseEntity.ok(AuthResponse.builder()
                        .token(jwtService.generateToken(user))
                        .build());
    }
}
