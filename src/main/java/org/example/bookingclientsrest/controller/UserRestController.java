package org.example.bookingclientsrest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookingclientsrest.model.User;
import org.example.bookingclientsrest.repository.UserRepository;
import org.example.bookingclientsrest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/new")
    public User addUser(@RequestBody User user) {
        log.info("Adding new user: {}", user);
        return userService.saveUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User userFromBody, @PathVariable Long id) {
        return userService.updateUser(userFromBody, id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody User userFromBody) {
        return userService.partialUpdateUser(userFromBody, id);
    }

}
