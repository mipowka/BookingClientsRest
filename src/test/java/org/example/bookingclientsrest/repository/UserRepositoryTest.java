package org.example.bookingclientsrest.repository;

import org.assertj.core.api.Assertions;
import org.example.bookingclientsrest.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void save() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");

        User save = userRepository.save(user);

        Assertions.assertThat(save).isNotNull();
        Assertions.assertThat(save.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(save.getId()).isGreaterThan(0);
    }

    @Test
    void findById() {

        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");

        User savedUser = userRepository.save(user);
        User userFromDb = userRepository.findById(savedUser.getId()).get();

        Assertions.assertThat(userFromDb).isNotNull();
        Assertions.assertThat(userFromDb.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(userFromDb.getId()).isEqualTo(savedUser.getId());

    }

    @Test
    void delete() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");

        User savedUser = userRepository.save(user);
        userRepository.delete(savedUser);
        Optional<User> byId = userRepository.findById(savedUser.getId());

        Assertions.assertThat(byId).isNotPresent();
    }

    @Test
    void update() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");

        User savedUser = userRepository.save(user);
        Optional<User> byId = userRepository.findById(savedUser.getId());

        byId.get().setUsername("NeAdmin");

        User updatedUser = userRepository.save(byId.get());

        Assertions.assertThat(updatedUser).isNotNull();
        Assertions.assertThat(updatedUser.getUsername()).isEqualTo("NeAdmin");
        Assertions.assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword());
    }
}