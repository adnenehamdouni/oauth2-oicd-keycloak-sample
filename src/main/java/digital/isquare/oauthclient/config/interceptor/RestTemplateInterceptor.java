package digital.isquare.oauthclient.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // Vous pouvez ajouter des en-têtes ou modifier la requête ici
        // Par exemple, ajouter un en-tête d'autorisation :
        request.getHeaders().add("Authorization", "Bearer your_token");

        log.info("Request: {} {}", request.getMethod(), request.getURI());

        // Puis exécutez la requête
        return execution.execute(request, body);
    }
}
