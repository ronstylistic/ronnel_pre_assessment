package com.rcjsolutions.SpringAppTest.security;

import com.rcjsolutions.SpringAppTest.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
public class TokenUtils {
    private final Environment env;

    @Autowired
    public TokenUtils(Environment env) {
        this.env = env;
    }

    String getUserNameFromToken(String token) {
        String userName;
        try {
            final Claims claims = getClaimsFromToken(token);
            userName =  claims != null ? claims.getSubject() : null;
        } catch (Exception e) {
            userName = null;
        }
        return userName;
    }

    Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims != null ? claims.getExpiration() : null;
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(env.getProperty("TOKEN_SECRET"))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + Long.parseLong(Objects.requireNonNull(env.getProperty("TOKEN_EXPIRATION"))) * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(this.generateCurrentDate());
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", user.getEmail());
        claims.put("created", this.generateCurrentDate());
        return this.generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(this.generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, env.getProperty("TOKEN_SECRET"))
                .compact();
    }

    boolean validateToken(String token, UserDetails userDetails) {
        User identity = (User) userDetails;
        final String userName = this.getUserNameFromToken(token);
        return (userName.equals(identity.getEmail()) && !(this.isTokenExpired(token)));
    }
}
