package org.example.bookingclientsrest.repository;

import org.assertj.core.api.Assertions;
import org.example.bookingclientsrest.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
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
    void save(){
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");

        User save = userRepository.save(user);

        Assertions.assertThat(save).isNotNull();
        Assertions.assertThat(save.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(save.getId()).isGreaterThan(0);
    }
}