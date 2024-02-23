package com.vicarius.quotamanagementapi.controller;


import com.vicarius.quotamanagementapi.service.interfaces.QuotaServiceContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/quotaAPI")
public class QuotaController {
    private static final Logger logger = LoggerFactory.getLogger(QuotaController.class);
    private final QuotaServiceContract quotaService;

    @Autowired
    public QuotaController(QuotaServiceContract quotaService) {
        this.quotaService = quotaService;
    }

    @PostMapping("/consumeQuota/{userId}")
    public ResponseEntity<String> consumeUserQuota(@PathVariable String userId) {
        try {
            boolean isUserAllowedToConsume = quotaService.consumeQuota(userId);
            if (isUserAllowedToConsume) {
                return ResponseEntity.ok("Quota successfully consumed for user: " + userId);
            } else {
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Quota exceeded for user: " + userId);
            }
        } catch (Exception e) {
            logger.error("Error consuming quota for user {}: {}", userId, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing quota consumption", e);
        }
    }

    @GetMapping("/getCurrentUsersQuotas")
    public ResponseEntity<Map<String, Integer>> getCurrentUsersQuotas() {
        try {
            Map<String, Integer> quotas = quotaService.getUsersQuota();
            return ResponseEntity.ok(quotas);
        } catch (Exception e) {
            logger.error("Error retrieving current quotas: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving quotas", e);
        }
    }
}
