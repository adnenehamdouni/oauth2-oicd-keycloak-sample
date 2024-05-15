package digital.isquare.oauthclient.controller;

import digital.isquare.oauthclient.model.rest.AuthRequest;
import digital.isquare.oauthclient.model.rest.AuthResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testLogin() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("my-user");
        authRequest.setPassword("my-password");

        ResponseEntity<AuthResponse> response = restTemplate.postForEntity("http://localhost:" + port + "/auth/login", authRequest, AuthResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("my-user", response.getBody().getUsername());
        assertNotNull(response.getBody().getToken());
        assertEquals("OK", response.getBody().getStatus());
    }

    @Test
    public void testLogout() {
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/auth/logout", null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logged out successfully", response.getBody());
    }
}