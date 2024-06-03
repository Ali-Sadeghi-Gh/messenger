package ir.mohaymen.messenger.services;

import ir.mohaymen.messenger.dto.AuthResponse;
import ir.mohaymen.messenger.dto.LoginRequest;
import ir.mohaymen.messenger.dto.SigninRequest;
import ir.mohaymen.messenger.entities.Role;
import ir.mohaymen.messenger.entities.UserEntity;
import ir.mohaymen.messenger.repositories.UserRepository;
import ir.mohaymen.messenger.security.JwtService;
import lombok.NonNull;
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
    private final UsernamePasswordChecker checker;

    public ResponseEntity<AuthResponse> login(@NonNull LoginRequest request) throws ResponseStatusException {
        String username = checker.checkUsername(request.getUsername());
        String password = checker.checkPassword(request.getPassword());
        String name = checker.checkName(request.getName());
        if (userRepository.findByUsername(username).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate phone number");
        }
        UserEntity user = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .name(name)
                .build();
        userRepository.save(user);
        log.info("User with id " + user.getId() + " and phone number " + user.getUsername() + " logged in.");
        return signin(SigninRequest.builder()
                .username(username)
                .password(password)
                .build());
    }

    public ResponseEntity<AuthResponse> signin(@NonNull SigninRequest request) throws ResponseStatusException {
        String username = request.getUsername();
        String password = request.getPassword();
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong phone number or password");
        }
        UserEntity user = optionalUser.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong phone number or password");
        }
        log.info("User with id " + user.getId() + " and phone number " + user.getUsername() + " signed in");
        return ResponseEntity.ok(AuthResponse.builder()
                        .id(user.getId())
                        .token(jwtService.generateToken(user))
                        .build());
    }
}
