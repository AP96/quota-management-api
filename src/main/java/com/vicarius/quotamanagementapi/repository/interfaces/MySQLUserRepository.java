package com.vicarius.quotamanagementapi.repository.interfaces;

import com.vicarius.quotamanagementapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MySQLUserRepository extends JpaRepository<User, String> {}
