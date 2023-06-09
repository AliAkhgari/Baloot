package filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Order(2)
@WebFilter(filterName = "AuthenticationFilter", asyncSupported = true, urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {
    List<String> allowedURLs = Arrays.asList("login", "signup", "callback");

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getServletPath().contains("login") || req.getServletPath().contains("signup")) {
            chain.doFilter(request, response);
            return;
        }
        String jwt = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwt == null || jwt.equals("")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("{\"Error\" : \" No JWT token \"}");
            res.setHeader("Content-Type", "application/json;charset=UTF-8");
            return;
        }

        String sign_key = "Baloot2023Baloot2023Baloot2023Baloot2023Baloot2023Baloot2023";

        SecretKey signature_type = new SecretKeySpec(sign_key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parserBuilder()
                    .setSigningKey(signature_type)
                    .build()
                    .parseClaimsJws(jwt);
            System.out.println("hhhh");
            if (claimsJws.getBody().getExpiration().before(Date.from(Instant.now()))) {
                throw new JwtException("Token is expired");
            }
            req.setAttribute("username", claimsJws.getBody().get("username"));
        } catch (JwtException je) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("{\"Error\" : \" JWT Expired \"}");
            res.setHeader("Content-Type", "application/json;charset=UTF-8");
            return;
        }
        chain.doFilter(request, response);

    }

}
