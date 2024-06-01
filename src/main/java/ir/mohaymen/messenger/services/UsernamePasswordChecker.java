package ir.mohaymen.messenger.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UsernamePasswordChecker {

    public String checkUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad username");
        }
        return username;
    }

    public String checkPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad password");
        }
        return password;
    }

    public String checkName(String name) {
        if (name == null || name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad name");
        }
        return name;
    }
}
