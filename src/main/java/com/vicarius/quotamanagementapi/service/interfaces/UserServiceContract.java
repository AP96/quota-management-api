package com.vicarius.quotamanagementapi.service.interfaces;

import com.vicarius.quotamanagementapi.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Focus on CRUD operations for the User model.
 */
public interface UserServiceContract {
    User createUser(User user);
    Optional<User> getUserById(String id);
    User updateUser(String id, User updatedUser);
    void deleteUser(String id);
    List<User> getAllUsers();
}
