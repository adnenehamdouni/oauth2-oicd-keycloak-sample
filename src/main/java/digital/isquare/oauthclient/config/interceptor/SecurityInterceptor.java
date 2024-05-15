package digital.isquare.oauthclient.config.interceptor;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Slf4j
public class SecurityInterceptor extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Récupérez l'authentification actuelle
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Vérifiez si l'utilisateur est authentifié
        /*if (authentication == null || !authentication.isAuthenticated()) {
            log.info("User is not authenticated");
            // Si l'utilisateur n'est pas authentifié, vous pouvez renvoyer une erreur
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }*/

        //log.info("User is authenticated");
        // Si l'utilisateur est authentifié, continuez avec la chaîne de filtres
        filterChain.doFilter(request, response);
    }
}
