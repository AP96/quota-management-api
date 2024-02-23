package com.vicarius.quotamanagementapi.service.implementations;

import com.vicarius.quotamanagementapi.model.User;
import com.vicarius.quotamanagementapi.repository.interfaces.MySQLUserRepository;
import com.vicarius.quotamanagementapi.service.interfaces.UserServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MySQLDBUserService implements UserServiceContract {
    private final MySQLUserRepository repository;

    @Autowired
    public MySQLDBUserService(MySQLUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User createUser(User user) {
        return repository.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return repository.findById(id);
    }

    @Override
    public User updateUser(String id, User details) {
        return repository.findById(id)
                .map(user -> {
                    user.setFirstName(details.getFirstName());
                    user.setLastName(details.getLastName());
                    user.setPreviousLoginTime(details.getPreviousLoginTime());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    details.setId(id);
                    return repository.save(details);
                });
    }

    @Override
    public void deleteUser(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

}
