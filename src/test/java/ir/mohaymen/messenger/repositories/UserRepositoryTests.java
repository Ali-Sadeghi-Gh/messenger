package ir.mohaymen.messenger.repositories;

import ir.mohaymen.messenger.entities.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
public class UserRepositoryTests {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTests(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void saveUser() {
        //given
        UserEntity user = UserEntity.builder()
                .username("09123456789")
                .password("12345678")
                .build();

        //when
        UserEntity savedUser = userRepository.save(user);

        //then
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
        Assertions.assertThat(savedUser).isEqualTo(user);
    }

    @Test
    public void findExistingUserById() {
        //given
        UserEntity user = UserEntity.builder()
                .username("09123456789")
                .password("12345678")
                .build();

        //when
        UserEntity savedUser = userRepository.save(user);
        Long id = savedUser.getId();
        Optional<UserEntity> foundUserOptional = userRepository.findById(id);

        //then
        Assertions.assertThat(foundUserOptional).isNotNull();
        Assertions.assertThat(foundUserOptional).isNotEmpty();
        Assertions.assertThat(foundUserOptional.get().getId()).isGreaterThan(0);
        Assertions.assertThat(foundUserOptional.get()).isEqualTo(user);
    }

    @Test
    public void findNotExistingUserById() {
        //given
        UserEntity user = UserEntity.builder()
                .username("09123456789")
                .password("12345678")
                .build();

        //when
        Long id = userRepository.save(user).getId();
        Long notExistingId = id + 1;
        Optional<UserEntity> notFoundUserOptional = userRepository.findById(notExistingId);

        //then
        Assertions.assertThat(notFoundUserOptional).isNotNull();
        Assertions.assertThat(notFoundUserOptional).isEmpty();
    }

    @Test
    public void findExistingUserByUsername() {
        //given
        UserEntity user = UserEntity.builder()
                .username("09123456789")
                .password("12345678")
                .build();

        //when
        UserEntity savedUser = userRepository.save(user);
        String username = savedUser.getUsername();
        Optional<UserEntity> foundUserOptional = userRepository.findByUsername(username);

        //then
        Assertions.assertThat(foundUserOptional).isNotNull();
        Assertions.assertThat(foundUserOptional).isNotEmpty();
        Assertions.assertThat(foundUserOptional.get().getId()).isGreaterThan(0);
        Assertions.assertThat(foundUserOptional.get().getUsername()).isEqualTo(username);
        Assertions.assertThat(foundUserOptional.get()).isEqualTo(user);
    }

    @Test
    public void findNotExistingUserByUsername() {
        //given
        UserEntity user = UserEntity.builder()
                .username("09123456789")
                .password("12345678")
                .build();

        //when
        String username = userRepository.save(user).getUsername();
        String notExistingUsername = username + "1";
        Optional<UserEntity> notFoundUserOptional = userRepository.findByUsername(notExistingUsername);

        //then
        Assertions.assertThat(notFoundUserOptional).isNotNull();
        Assertions.assertThat(notFoundUserOptional).isEmpty();
    }
}
