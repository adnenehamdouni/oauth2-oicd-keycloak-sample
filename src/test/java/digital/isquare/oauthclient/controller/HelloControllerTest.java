package digital.isquare.oauthclient.controller;

import digital.isquare.oauthclient.service.CustomRestTemplate;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomRestTemplate customRestTemplate;

    private String accessToken;

    @BeforeEach
    void setUp() {


    }

    @Test
    void getPublic() throws Exception {
        mockMvc.perform(get("/api/public")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Contenu Public"));
    }

    @Test
    void getPrivate() throws Exception {
        //
        getAccessToken();

        //
        mockMvc.perform(get("/api/private")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Contenu Privé"));
    }

    private void getAccessToken() {
        String urlParam = "/protocol/openid-connect/token";
        String url = "http://localhost:8180/auth/realms/Keycloak_SpringBoot".concat(urlParam);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", "springboot-openid-client-app");
        params.add("client_secret", "TlOjOmy4vEQbPsKjMqV009wYHdlGaIG2");
        params.add("username", "my-user");
        params.add("password", "my-password");
        params.add("grant_type", "password");
        params.add("scope", "openid");

        ResponseEntity<Map> responseEntity = customRestTemplate.post(url, params, Map.class);
        Map<String, String> response = responseEntity.getBody();

        assert response != null;
        accessToken = response.get("access_token");
    }
}