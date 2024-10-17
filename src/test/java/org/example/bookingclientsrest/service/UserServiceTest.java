package org.example.bookingclientsrest.service;

import org.example.bookingclientsrest.model.User;
import org.example.bookingclientsrest.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(2L);
        user.setUsername("Anton");
        user.setPassword("qwerty");
        user.setBalance(333.33);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUserById() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User userById = userService.getUserById(2L);

        Assertions.assertNotNull(userById);
        Assertions.assertEquals("Anton", userById.getUsername());
        Assertions.assertEquals("qwerty", userById.getPassword());

        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    void getAllUsers() {

    }

    @Test
    void saveUser() {
    }

    @Test
    void deleteUserById() {
    }

    @Test
    void updateUser() {
        User updatedUser = new User();
        updatedUser.setUsername("Oleg");
        updatedUser.setPassword("nePass");
        updatedUser.setBalance(666.66);

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        ResponseEntity<User> userEntity = userService.updateUser(updatedUser, 2L);

        Assertions.assertEquals(2L, userEntity.getBody().getId());
        Assertions.assertEquals("Oleg", userEntity.getBody().getUsername());
        Assertions.assertEquals("nePass", userEntity.getBody().getPassword());
        Assertions.assertEquals(666.66, userEntity.getBody().getBalance());

        verify(userRepository, times(1)).findById(2L);
        verify(userRepository,times(1)).save(user);

    }

    @Test
    void partialUpdateUser() {
    }
}