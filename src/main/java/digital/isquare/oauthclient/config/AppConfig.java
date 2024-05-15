package digital.isquare.oauthclient.config;

import digital.isquare.oauthclient.config.interceptor.RestTemplateInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    private final RestTemplateInterceptor restTemplateInterceptor;

    public AppConfig(RestTemplateInterceptor restTemplateInterceptor) {
        this.restTemplateInterceptor = restTemplateInterceptor;
    }

    /*@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }*/

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(restTemplateInterceptor);
        return restTemplate;
    }
}
