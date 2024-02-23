package com.vicarius.quotamanagementapi.service.implementations;

import com.vicarius.quotamanagementapi.model.User;
import com.vicarius.quotamanagementapi.repository.interfaces.ElasticSearchUserRepository;
import com.vicarius.quotamanagementapi.service.interfaces.UserServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyElasticDBUserService implements UserServiceContract {

    private final ElasticSearchUserRepository elasticSearchUserRepository;

    @Autowired
    public MyElasticDBUserService(ElasticSearchUserRepository elasticSearchUserRepository) {
        this.elasticSearchUserRepository = elasticSearchUserRepository;
    }

    @Override
    public User createUser(User user) {
        elasticSearchUserRepository.createUserInElasticDB(user);
        return elasticSearchUserRepository.getUser(user.getId()).orElse(null);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return elasticSearchUserRepository.getUser(id);
    }

    @Override
    public User updateUser(String id, User updatedUser) {
        return elasticSearchUserRepository.updateUser(id, updatedUser);
    }

    @Override
    public void deleteUser(String id) {
        elasticSearchUserRepository.deleteUser(id);

    }
    @Override
    public List<User> getAllUsers() {
        return elasticSearchUserRepository.getAllUsers();
    }

}
