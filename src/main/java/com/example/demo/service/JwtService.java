package com.example.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



@Service
public class JwtService {

    private final SecretKey key = Jwts.SIG.HS256.key().build();   // created ONCE

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>(); //claims custain custom data (eg. roles , permission)
        claims.put("username", username); // adding custom claim

        return Jwts.builder() // jwts is a predifined class to manage tokens
                .setClaims(claims) // add claim to token
                .setSubject(username) // set the subject
                .setIssuedAt(new Date(System.currentTimeMillis())) // issue time of token
                .setExpiration(new Date(System.currentTimeMillis() + 100 * 60 * 60 * 3)) //Exp time of token
                .signWith(getKey(), SignatureAlgorithm.HS256) //Sign the token with secret key
                .compact(); // Generate Token


    }

    private SecretKey getKey() {
        return key;
    }

    public Claims extractClaims(String token) {
        // throws if the signature is bad or the token is expired
        return Jwts.parser().verifyWith(getKey()).build()
                .parseSignedClaims(token).getPayload();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isValid(String token, UserDetails user) {
        Claims c = extractClaims(token);
        return c.getSubject().equals(user.getUsername())
                && c.getExpiration().after(new Date());


    }
}