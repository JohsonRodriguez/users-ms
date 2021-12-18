package pe.edu.lordbyron.authserver.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pe.edu.lordbyron.authserver.model.Employee;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;


@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Value("${authentication.hash.secret}")
    private String secret;
    private int tokenLifetime = 20 * 60 * 1000;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        var username = request.getParameter("username").trim();
        var password = request.getParameter("password").trim();
        log.info("Username: {}", username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
       var employee = (Employee) authentication.getPrincipal();
       var algorithm = Algorithm.HMAC256(secret.getBytes());
       var accessToken = JWT.create()
               .withSubject(employee.getUsername())
               .withExpiresAt(new Date(System.currentTimeMillis() + tokenLifetime))
               .withIssuer(request.getRequestURL().toString())
               .withClaim("roles", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
               .sign(algorithm);
       var refreshToken = JWT.create()
               .withSubject(employee.getUsername())
               .withExpiresAt(new Date(System.currentTimeMillis() + tokenLifetime))
               .withIssuer(request.getRequestURL().toString())
               .sign(algorithm);
       response.setHeader("access_token", accessToken);
       response.setHeader("refresh_token", refreshToken);

    }
}