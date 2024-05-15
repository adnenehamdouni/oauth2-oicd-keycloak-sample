package digital.isquare.oauthclient.controller;

import digital.isquare.oauthclient.model.rest.KeycloakRequest;
import digital.isquare.oauthclient.model.rest.KeycloakResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        // Mock the behavior of RestTemplate
        KeycloakResponse mockKeycloakResponse = new KeycloakResponse();
        mockKeycloakResponse.setAccessToken("Mock access token");
        when(restTemplate.postForEntity(anyString(), any(KeycloakRequest.class), any()))
                .thenReturn(ResponseEntity.ok(mockKeycloakResponse));
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"my-user\"," +
                                "\"password\":\"my-password\"," +
                                "\"client_id\":\"my-client-id\"," +
                                "\"client_secret\":\"my-client-secret\"," +
                                "\"grant_type\":\"password\"," +
                                "\"scope\":\"openid\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("my-user"))
                .andExpect(jsonPath("$.token").value("Mock access token"))
                .andExpect(jsonPath("$.status").value("OK"));
    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(post("/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}