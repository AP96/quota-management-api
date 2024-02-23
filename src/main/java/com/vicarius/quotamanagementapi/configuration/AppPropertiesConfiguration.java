package com.vicarius.quotamanagementapi.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * This class is a source of bean definitions
 * for the Spring application context.
 */
@Configuration
@Getter
public class AppPropertiesConfiguration {

    @Value("${user.max.quota}")
    private int maxQuota;

}
