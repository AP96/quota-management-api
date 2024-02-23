package com.vicarius.quotamanagementapi.repository.interfaces;

import com.vicarius.quotamanagementapi.model.User;

import java.util.List;
import java.util.Optional;

public interface ElasticSearchUserRepository {
    void createUserInElasticDB(User user);

    Optional<User> getUser(String id);

    User updateUser(String id, User userDetails);

    List<User> getAllUsers();

    void deleteUser(String userID);


}
