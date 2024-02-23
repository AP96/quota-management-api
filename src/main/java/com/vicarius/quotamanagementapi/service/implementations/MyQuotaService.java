package com.vicarius.quotamanagementapi.service.implementations;

import com.vicarius.quotamanagementapi.configuration.AppPropertiesConfiguration;
import com.vicarius.quotamanagementapi.service.interfaces.QuotaServiceContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MyQuotaService implements QuotaServiceContract {
    private static final Logger logger = LoggerFactory.getLogger(MyQuotaService.class);

    private final AppPropertiesConfiguration appPropertiesConfiguration;
    private final Map<String, AtomicInteger> quotaMap = new ConcurrentHashMap<>();

    @Autowired
    public MyQuotaService(AppPropertiesConfiguration appPropertiesConfiguration) {
        this.appPropertiesConfiguration = appPropertiesConfiguration;
    }

    @Override
    public boolean consumeQuota(String userId) {
        try {
            if (userId == null || userId.trim().isEmpty()) {
                logger.error("Error - consumeQuota operation called with null or empty userId");
                return false;
            }
            int maxQuota = appPropertiesConfiguration.getMaxQuota();
            AtomicInteger currentCount = quotaMap.computeIfAbsent(userId, k -> new AtomicInteger(0));

            if (currentCount.get() >= maxQuota) {
                logger.warn("Quota exceeded for user: {}", userId);
                return false; // Quota exceeded, block the request.
            }

            currentCount.incrementAndGet(); // Safely increment
            return true;
        } catch (Exception e) {
            logger.error("Exception occurred while consuming quota for user {}: {}", userId, e.getMessage(), e);
            return false;
        }

    }

    @Override
    public Map<String, Integer> getUsersQuota() {
        Map<String, Integer> usersQuotaMap = new ConcurrentHashMap<>();
        quotaMap.forEach((userId, quota) -> usersQuotaMap.put(userId, quota.get()));
        return usersQuotaMap;
    }
}
