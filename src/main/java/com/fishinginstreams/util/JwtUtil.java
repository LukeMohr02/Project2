package com.fishinginstreams.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

/**
 * Inspired by JavaBrains:
 * https://github.com/koushikkothagal/spring-security-jwt
 */

@Service
public class JwtUtil {

    // Token expiration in milliseconds
    private static int TOKEN_EXPIRATION = Integer.MAX_VALUE;
    // Security key defined in security.properties (separate from source code). Value is arbitrary.
    private static String SECURE_KEY;

    public JwtUtil() throws IOException {
        readProperties();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECURE_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECURE_KEY)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public static void readProperties() throws IOException, NumberFormatException {
        Properties properties = new Properties();
        String fileName = "security.properties";
        InputStream inputStream = JwtUtil.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("File not found: " + fileName);
        }

        // Can throw NumberFormatException
        TOKEN_EXPIRATION = Integer.parseInt(properties.getProperty("token_expiration"));
        SECURE_KEY = properties.getProperty("secure_key");
    }
}
