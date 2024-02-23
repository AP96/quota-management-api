package com.vicarius.quotamanagementapi.service;

import com.vicarius.quotamanagementapi.service.implementations.MyElasticDBUserService;
import com.vicarius.quotamanagementapi.service.implementations.MySQLDBUserService;
import com.vicarius.quotamanagementapi.service.interfaces.UserServiceContract;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZoneId;

@Component
public class UserServiceDecisionPointFactory {
    private final MySQLDBUserService mySQLUserService;

    private final MyElasticDBUserService elasticUserService;

    public UserServiceDecisionPointFactory(MySQLDBUserService mySQLUserService, MyElasticDBUserService elasticUserService) {
        this.mySQLUserService = mySQLUserService;
        this.elasticUserService = elasticUserService;
    }
    public UserServiceContract getUserService() {
        LocalTime utcNow = LocalTime.now(ZoneId.of("UTC"));
        if (utcNow.isAfter(LocalTime.of(9, 0)) && utcNow.isBefore(LocalTime.of(17, 0))) {
            return mySQLUserService;
        } else {
            return elasticUserService;
        }
    }
}
