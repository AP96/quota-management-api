package com.vicarius.quotemanagementapi;

import com.vicarius.quotamanagementapi.controller.QuotaController;
import com.vicarius.quotamanagementapi.service.interfaces.QuotaServiceContract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class QuotaControllerTests {
    @Mock
    private QuotaServiceContract quotaService;

    @InjectMocks
    private QuotaController quotaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsumeUserQuota_Success() {
        String userId = "user123456789id";
        when(quotaService.consumeQuota(userId)).thenReturn(true);
        ResponseEntity<String> response = quotaController.consumeUserQuota(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Quota successfully consumed for user: " + userId, response.getBody());
    }

    @Test
    public void testConsumeUserQuota_QuotaExceeded() {
        String userId = "user123456789id";
        when(quotaService.consumeQuota(userId)).thenReturn(false);
        ResponseEntity<String> response = quotaController.consumeUserQuota(userId);
        assertEquals(HttpStatus.TOO_MANY_REQUESTS, response.getStatusCode());
        assertEquals("Quota exceeded for user: " + userId, response.getBody());
    }

    @Test
    public void testGetCurrentUsersQuotas_Success() {
        Map<String, Integer> quotasHashMap = new HashMap<>();
        quotasHashMap.put("user123456789id", 10);
        when(quotaService.getUsersQuota()).thenReturn(quotasHashMap);
        ResponseEntity<Map<String, Integer>> response = quotaController.getCurrentUsersQuotas();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quotasHashMap, response.getBody());
    }
}
