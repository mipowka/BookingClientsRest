package org.example.bookingclientsrest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bookingclientsrest.model.User;
import org.example.bookingclientsrest.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void getUser() {
        User user = new User();
        user.setId(2L);
        user.setBalance(399.99);
        user.setUsername("Anton");
        user.setPassword("password");

        when(userService.getUserById(user.getId())).thenReturn(user);


        try {
            mockMvc.perform(get("/users/{id}", user.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(2))
                    .andExpect(jsonPath("$.balance").value(399.99))
                    .andExpect(jsonPath("$.username").value("Anton"))
                    .andExpect(jsonPath("$.password").value("password"))
                    .andDo(print());
        } catch (Exception e) {

            System.out.println("error");
            throw new RuntimeException(e);
        }

        verify(userService, times(1)).getUserById(user.getId());

    }

    @Test
    void getAllUsers() {
        User user = new User();
        user.setId(2L);
        user.setBalance(399.99);
        user.setUsername("Anton");
        user.setPassword("password");

        User user2 = new User();
        user2.setId(3L);
        user2.setBalance(199.99);
        user2.setUsername("Anton2");
        user2.setPassword("password3");

        List<User> users = List.of(user, user2);

        when(userService.getAllUsers()).thenReturn(users);

        try {
            mockMvc.perform(get("/users/all"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(2))
                    .andExpect(jsonPath("$[0].balance").value(399.99))
                    .andExpect(jsonPath("$[0].username").value("Anton"))
                    .andExpect(jsonPath("$[0].password").value("password"))
                    .andExpect(jsonPath("$[1].id").value(3))
                    .andExpect(jsonPath("$[1].balance").value(199.99))
                    .andExpect(jsonPath("$[1].username").value("Anton2"))
                    .andExpect(jsonPath("$[1].password").value("password3"))
                    .andDo(print());
        } catch (Exception e) {

            System.out.println("error");
            throw new RuntimeException(e);
        }

        verify(userService, times(1)).getAllUsers();


    }

    @Test
    void addUser() {
        User user = new User();
        user.setId(2L);
        user.setBalance(399.99);
        user.setUsername("Anton");
        user.setPassword("password");

        when(userService.saveUser(any(User.class))).thenReturn(user);

        try {
            mockMvc.perform(post("/users/new")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(2))
                    .andExpect(jsonPath("$.balance").value(399.99))
                    .andExpect(jsonPath("$.username").value("Anton"))
                    .andExpect(jsonPath("$.password").value("password"))
                    .andDo(print());
        } catch (Exception e) {
            System.out.println("error");
        }

        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    void deleteUser() {
        Long id = 2L;

        when(userService.deleteUserById(id)).thenReturn(ResponseEntity.ok("udalen"));

        try {
            mockMvc.perform(delete("/users/delete/{id}", id))
                    .andExpect(status().isOk())
                    .andDo(print());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        verify(userService, times(1)).deleteUserById(id);
    }

    @Test
    void updateUser() {
    }

    @Test
    void patchUser() {
    }
}