package com.example.demo.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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

    public String generateSecretKey()
    {
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGen.generateKey();
            System.out.println("Secret Key : " + secretKey.toString());
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        }
        catch(NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating key", e);
        }
    }


    public String generateToken(String username)
    {
        Map<String , Object> claims = new HashMap<>(); //claims custain custom data (eg. roles , permission)
        claims.put("username", username); // adding custom claim

        return Jwts.builder() // jwts is a predifined class to manage tokens
                .setClaims(claims) // add claim to token
                .setSubject(username) // set the subject
                .setIssuedAt(new Date(System.currentTimeMillis())) // issue time of token
                .setExpiration(new Date(System.currentTimeMillis()+ 100*60*60*3)) //Exp time of token
                .signWith( getKey() , SignatureAlgorithm.HS256) //Sign the token with secret key
                .compact(); // Generate Token


    }


    private SecretKey getKey()
    {
        byte[] keyBytes = Decoders.BASE64.decode(generateSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
c