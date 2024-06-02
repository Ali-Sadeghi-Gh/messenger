package ir.mohaymen.messenger.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
public class UsernamePasswordCheckerTests {
    private final UsernamePasswordChecker checker;

    @Autowired
    public UsernamePasswordCheckerTests(UsernamePasswordChecker checker) {
        this.checker = checker;
    }

    @Test
    public void checkName() {
        //given
        String name = "ali";

        //when
        String checkedName = checker.checkName(name);

        //then
        Assertions.assertThat(checkedName).isNotNull();
        Assertions.assertThat(checkedName).isEqualTo(name);
    }

    @Test
    public void checkBadName() {
        //given
        String name = "";

        //when
        ResponseStatusException responseStatusException = null;
        try {
            checker.checkName(name);
        } catch (ResponseStatusException e) {
            responseStatusException = e;
        }

        //then
        Assertions.assertThat(responseStatusException).isNotNull();
        Assertions.assertThat(responseStatusException.getMessage()).isEqualTo("400 BAD_REQUEST \"Name must not be empty\"");
    }

    @Test
    public void checkUsername() {
        //given
        String username = "9123456789";

        //when
        String checkedUsername = checker.checkUsername(username);

        //then
        Assertions.assertThat(checkedUsername).isNotNull();
        Assertions.assertThat(checkedUsername).isEqualTo(username);
    }

    @Test
    public void checkBadUsername() {
        //given
        String username = "9123456789a";

        //when
        ResponseStatusException responseStatusException = null;
        try {
            checker.checkUsername(username);
        } catch (ResponseStatusException e) {
            responseStatusException = e;
        }

        //then
        Assertions.assertThat(responseStatusException).isNotNull();
        Assertions.assertThat(responseStatusException.getMessage()).isEqualTo("400 BAD_REQUEST \"Phone number must be 10 digits\"");
    }

    @Test
    public void checkPassword() {
        //given
        String password = "A9s12kjfdj";

        //when
        String checkedPassword = checker.checkPassword(password);

        //then
        Assertions.assertThat(checkedPassword).isNotNull();
        Assertions.assertThat(checkedPassword).isEqualTo(password);
    }

    @Test
    public void checkBadPassword() {
        //given
        String password = "9123456789a";

        //when
        ResponseStatusException responseStatusException = null;
        try {
            checker.checkPassword(password);
        } catch (ResponseStatusException e) {
            responseStatusException = e;
        }

        //then
        Assertions.assertThat(responseStatusException).isNotNull();
        Assertions.assertThat(responseStatusException.getMessage()).isEqualTo("400 BAD_REQUEST \"Password must contain at least one uppercase letter\"");
    }
}
