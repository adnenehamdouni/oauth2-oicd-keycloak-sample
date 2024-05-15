package digital.isquare.oauthclient.controller;

import digital.isquare.oauthclient.model.rest.AuthRequest;
import digital.isquare.oauthclient.model.rest.AuthResponse;
import digital.isquare.oauthclient.model.rest.KeycloakRequest;
import digital.isquare.oauthclient.model.rest.KeycloakResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class AuthController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();

        if (username == null || password == null) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setStatus("KO");
            authResponse.setErrorMessage("Missing username or password");
            return ResponseEntity.badRequest().body(authResponse);
        }

        // Prepare the request body
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        map.add("client_id", "springboot-openid-client-app");
        map.add("client_secret", "TlOjOmy4vEQbPsKjMqV009wYHdlGaIG2");
        map.add("grant_type", "password");

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);

            // Send the request to Keycloak
            ResponseEntity<KeycloakResponse> response = restTemplate.postForEntity("http://localhost:8180/auth/realms/Keycloak_SpringBoot/protocol/openid-connect/token", requestEntity, KeycloakResponse.class);

            // If the request is successful, create AuthResponse and return
            if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.info("Authorized");
                AuthResponse authResponse = new AuthResponse();
                authResponse.setUsername(username);
                authResponse.setToken(response.getBody().getAccessToken());
                authResponse.setStatus("OK");
                return ResponseEntity.ok(authResponse);
            } else {
                log.info("Unauthorized");
                AuthResponse authResponse = new AuthResponse();
                authResponse.setStatus("KO");
                authResponse.setErrorMessage("Unauthorized");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse);
            }

        } catch (HttpClientErrorException e) {
            log.error("Error while calling Keycloak: {}", e.getResponseBodyAsString());
            // If the request fails, return the status code and error message
            AuthResponse authResponse = new AuthResponse();
            authResponse.setStatus("KO");
            authResponse.setErrorMessage(e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode()).body(authResponse);
        }
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout() {
        // Implement the logout logic here
        return ResponseEntity.ok("Logged out successfully");
    }
}

