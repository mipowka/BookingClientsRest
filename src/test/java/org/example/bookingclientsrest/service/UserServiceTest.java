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

import java.util.List;
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
        User user2 = new User();
        user2.setId(1L);
        user2.setUsername("NeAnton");
        user2.setPassword("passparol");
        user2.setBalance(1333.11);

        List<User> users = List.of(user, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> allUsers = userService.getAllUsers();

        Assertions.assertNotNull(allUsers);
        Assertions.assertEquals(2, allUsers.size());
        Assertions.assertEquals("Anton", allUsers.get(0).getUsername());
        Assertions.assertEquals("NeAnton", allUsers.get(1).getUsername());
        Assertions.assertEquals("qwerty", allUsers.get(0).getPassword());
        Assertions.assertEquals("passparol", allUsers.get(1).getPassword());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void saveUser() {
        when(userRepository.save(user)).thenReturn(user);

        User saved = userService.saveUser(user);

        Assertions.assertNotNull(saved);
        Assertions.assertEquals("Anton", saved.getUsername());
        Assertions.assertEquals("qwerty", saved.getPassword());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUserById() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        ResponseEntity<String> stringResponse = userService.deleteUserById(2L);

        Assertions.assertNotNull(stringResponse);
        Assertions.assertEquals("User deleted successfully", stringResponse.getBody());

        verify(userRepository, times(1)).findById(2L);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void updateUser() {
        User updatedUser = new User();
        updatedUser.setUsername("Oleg");


        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        ResponseEntity<User> userEntity = userService.updateUser(updatedUser, 2L);

        Assertions.assertEquals(2L, userEntity.getBody().getId());
        Assertions.assertEquals("Oleg", userEntity.getBody().getUsername());
        Assertions.assertNull(userEntity.getBody().getPassword());
        Assertions.assertNull(userEntity.getBody().getBalance());

        verify(userRepository, times(1)).findById(2L);
        verify(userRepository, times(1)).save(user);

    }

    @Test
    void partialUpdateUser() {
        User updatedUser = new User();
        updatedUser.setUsername("Oleg");

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        ResponseEntity<User> userEntity = userService.partialUpdateUser(updatedUser, 2L);

        Assertions.assertEquals(2L, userEntity.getBody().getId());
        Assertions.assertEquals("Oleg", userEntity.getBody().getUsername());
        Assertions.assertEquals("qwerty", userEntity.getBody().getPassword());
        Assertions.assertEquals(333.33, userEntity.getBody().getBalance());

        verify(userRepository, times(1)).findById(2L);
        verify(userRepository, times(1)).save(user);
    }
}