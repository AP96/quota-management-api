package com.vicarius.quotamanagementapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime registrationDate;
    private LocalDateTime previousLoginTime;
    // Quota limit threshold value is injected from the application.properties file
    @Value("${user.max.quota}")
    private int maxQuota;
}
