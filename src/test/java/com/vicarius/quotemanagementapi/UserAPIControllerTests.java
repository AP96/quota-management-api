package com.vicarius.quotemanagementapi;

import com.vicarius.quotamanagementapi.controller.UserAPIController;
import com.vicarius.quotamanagementapi.model.User;
import com.vicarius.quotamanagementapi.service.UserServiceDecisionPointFactory;
import com.vicarius.quotamanagementapi.service.interfaces.UserServiceContract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserAPIControllerTests {

    @Mock
    private UserServiceDecisionPointFactory userServiceFactory;

    @Mock
    private UserServiceContract userService;

    @InjectMocks
    private UserAPIController userAPIController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUserCase_Success() {
        User user = new User();
        when(userServiceFactory.getUserService()).thenReturn(userService);
        when(userService.createUser(user)).thenReturn(user);
        ResponseEntity<User> response = userAPIController.createUser(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserByIdCase_Success() {
        String userId = "user123456789id";
        User user = new User();
        when(userServiceFactory.getUserService()).thenReturn(userService);
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        ResponseEntity<User> response = userAPIController.getUserById(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserByIdCase_UserNotFound() {
        String userId = "user123456789id";
        when(userServiceFactory.getUserService()).thenReturn(userService);
        when(userService.getUserById(userId)).thenReturn(Optional.empty());
        ResponseEntity<User> response = userAPIController.getUserById(userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
