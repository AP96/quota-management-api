package com.vicarius.quotemanagementapi;

import com.vicarius.quotamanagementapi.QuotaManagementApiApplication;
import com.vicarius.quotamanagementapi.configuration.AppPropertiesConfiguration;
import com.vicarius.quotamanagementapi.model.User;
import com.vicarius.quotamanagementapi.repository.interfaces.MySQLUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = QuotaManagementApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuotaManagementTests {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AppPropertiesConfiguration appPropertiesConfiguration;

    @Autowired
    private MySQLUserRepository userRepository; // Assuming MySQL is used for demonstration

    @BeforeEach
    public void setup() {
        // Clear the repository and reset
        userRepository.deleteAll();
    }

    @Test
    public void testUserCreationAndQuotaConsumption() {
        // Create a user
        User newUser = new User();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setFirstName("Alon");
        newUser.setLastName("Zandberg");
        newUser.setEmail("alon.sandberg@gmail.com");
        ResponseEntity<User> userResponse = restTemplate.postForEntity("http://localhost:" + port + "/crudAPI/users", newUser, User.class);
        assertThat(userResponse.getStatusCode().is2xxSuccessful()).isTrue();
        User createdUser = userResponse.getBody();
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getFirstName()).isEqualTo("Alon");

        // Consume quota for the new user
        for (int i = 0; i < appPropertiesConfiguration.getMaxQuota(); i++) {
            ResponseEntity<String> quotaResponse = restTemplate.postForEntity("http://localhost:" + port + "/quotaAPI/consumeQuota/" + createdUser.getId(), null, String.class);
            assertThat(quotaResponse.getStatusCode().is2xxSuccessful()).isTrue();
        }

        // consume quota beyond the limit - test
        ResponseEntity<String> quotaExceededResponse = restTemplate.postForEntity("http://localhost:" + port + "/quotaAPI/consumeQuota/" + createdUser.getId(), null, String.class);
        assertThat(quotaExceededResponse.getStatusCode().value()).isEqualTo(429); // TOO_MANY_REQUESTS

        // Get current users quotas
        ResponseEntity<String> quotasResponse = restTemplate.getForEntity("http://localhost:" + port + "/quotaAPI/getCurrentUsersQuotas", String.class);
        assertThat(quotasResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(quotasResponse.getBody()).contains("\"" + createdUser.getId() + "\":" + appPropertiesConfiguration.getMaxQuota());
    }



}
