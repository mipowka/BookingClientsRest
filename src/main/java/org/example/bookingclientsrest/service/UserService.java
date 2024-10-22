package org.example.bookingclientsrest.service;

import lombok.RequiredArgsConstructor;
import org.example.bookingclientsrest.exception.UserNotFoundException;
import org.example.bookingclientsrest.model.User;
import org.example.bookingclientsrest.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public ResponseEntity<String> deleteUserById(Long userId) {
        User userById = getUserById(userId);
        userRepository.delete(userById);
        return ResponseEntity.ok("User deleted successfully");
    }

    public ResponseEntity<User> updateUser(User userFromForm, Long userId) {
        User userToUpdate = getUserById(userId);

        userToUpdate.setUsername(userFromForm.getUsername());
        userToUpdate.setPassword(userFromForm.getPassword());
        userToUpdate.setBalance(userFromForm.getBalance());
        userToUpdate.setCreatedAt(userFromForm.getCreatedAt());

        saveUser(userToUpdate);
        return ResponseEntity.ok(userToUpdate);
    }

    public ResponseEntity<User> partialUpdateUser(User userFromForm, Long userId) {
        User userToUpdate = getUserById(userId);

        if (userFromForm.getUsername() != null) {
            userToUpdate.setUsername(userFromForm.getUsername());
        }
        if (userFromForm.getPassword() != null) {
            userToUpdate.setPassword(userFromForm.getPassword());
        }
        if (userFromForm.getBalance() != null) {
            userToUpdate.setBalance(userFromForm.getBalance());
        }

        saveUser(userToUpdate);
        return ResponseEntity.ok(userToUpdate);
    }
}
