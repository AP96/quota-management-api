package com.vicarius.quotamanagementapi.controller;

import com.vicarius.quotamanagementapi.model.User;
import com.vicarius.quotamanagementapi.service.UserServiceDecisionPointFactory;
import com.vicarius.quotamanagementapi.service.interfaces.UserServiceContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/crudAPI/users")
public class UserAPIController {

    private static final Logger logger = LoggerFactory.getLogger(UserAPIController.class);
    private final UserServiceDecisionPointFactory userServiceFactory;

    @Autowired
    public UserAPIController(UserServiceDecisionPointFactory userServiceFactory) {
        this.userServiceFactory = userServiceFactory;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            UserServiceContract userServiceContract = userServiceFactory.getUserService();
            User newUser = userServiceContract.createUser(user);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            logger.error("Error creating user: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating user", e);
        }
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        try {
            UserServiceContract userServiceContract = userServiceFactory.getUserService();
            return userServiceContract.getUserById(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error retrieving user details with ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving user", e);
        }
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        try {
            UserServiceContract userServiceContract = userServiceFactory.getUserService();
            User user = userServiceContract.updateUser(id, updatedUser);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error updating user with ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating user", e);
        }
    }

    @DeleteMapping("deleteUser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        try {
            UserServiceContract userServiceContract = userServiceFactory.getUserService();
            userServiceContract.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting user with ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting user", e);
        }
    }

    @GetMapping("getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            UserServiceContract userService = userServiceFactory.getUserService();
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error retrieving all users: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving all users", e);
        }
    }
}
