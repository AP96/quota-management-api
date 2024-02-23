package com.vicarius.quotamanagementapi.repository.implementations;

import com.vicarius.quotamanagementapi.model.User;
import com.vicarius.quotamanagementapi.repository.interfaces.ElasticSearchUserRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Getter
public class ElasticSearchUserRepositoryImpl implements ElasticSearchUserRepository {
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchUserRepositoryImpl.class);
    private final Map<String, User> inMemoryUsersData = new ConcurrentHashMap<>();

    @Override
    public void createUserInElasticDB(User user) {

        if (user == null) {
            logger.error("Cannot Save new User in Elastic DB : User is null");
            return;
        }

        String id = user.getId();
        if (id == null || id.trim().isEmpty()) {
            logger.error("Cannot Save new User in Elastic DB : User ID is null or Empty");
            return;
        }

        try {
            logger.info("Simulating createUserInElasticDB Operation: {}", user);
            inMemoryUsersData.put(user.getId(), user);
        } catch (Exception e) {
            logger.error("createUserInElasticDB Simulation Error :{}", e.getMessage());
        }
    }

    @Override
    public Optional<User> getUser(String id) {
        if (id == null || id.trim().isEmpty()) {
            logger.error("Cannot Get User from Elastic DB : UserID is null or empty");
            return Optional.empty();
        }
        try {
            logger.info("Simulation ElasticSearchDB getUserOperation - Fetching user with ID: {}", id);
            return Optional.ofNullable(inMemoryUsersData.get(id));
        } catch (Exception e) {
            logger.error("Error fetching user in ElasticSearch simulation: {}", e.getMessage());
            return Optional.empty();
        }

    }

    @Override
    public User updateUser(String id, User userDetails) {
        if (id == null || id.trim().isEmpty()) {
            logger.error("Cannot Update User In ElasticDB: Given UserID is null or Empty");
            return null;
        }
        if (userDetails == null) {
            logger.error("Cannot Update User In ElasticDB: Given UserDetails for update are null");
            return getUser(id).orElse(null);
        }
        try {
            logger.info("Simulation ElasticSearchDB updateUserDetails Operation - Updating user with ID: {}", id);
            inMemoryUsersData.replace(id, userDetails);
            return userDetails;
        } catch (Exception e) {
            logger.error("Error updating user in ElasticSearch simulation: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("Fetching all users from the simulated ElasticSearch DB");
        return new ArrayList<>(inMemoryUsersData.values());
    }

    @Override
    public void deleteUser(String id) {
        if (id == null || id.trim().isEmpty()) {
            logger.error("Cannot Update User In ElasticDB: Given UserID is null or Empty");
            return;
        }
        try {
            logger.info("Simulation ElasticSearchDB deleteUserDetails Operation - Delete user with ID: {}", id);
            inMemoryUsersData.remove(id);
        } catch (Exception e) {
            logger.error("Error deleting user in ElasticSearch simulation: {}", e.getMessage());
        }
    }
}
