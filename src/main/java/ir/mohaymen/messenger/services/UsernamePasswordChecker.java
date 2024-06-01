package ir.mohaymen.messenger.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UsernamePasswordChecker {

    public String checkUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username must not be empty");
        }
        if (username.length() < 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number must not be 10 digits");
        }
        username = username.substring(username.length() - 10);
        if (username.matches("^[0-9]{9}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number must be 10 digits");
        }
        return username;
    }

    public String checkPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must not be empty");
        }
        if (password.length() < 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must contain at least one lowercase letter");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*\\d.*")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must contain at least one digit");
        }
        return password;
    }

    public String checkName(String name) {
        if (name == null || name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name must not be empty");
        }
        return name;
    }
}
