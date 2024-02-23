package com.vicarius.quotamanagementapi.service.interfaces;

import java.util.Map;

public interface QuotaServiceContract {
    boolean consumeQuota(String userId);
    Map<String, Integer> getUsersQuota(); // A map of user IDs and number of Quotas
}
