package com.e_commerceapp.E_Commerce.jwt;

import java.net.http.HttpRequest;
import java.security.Key;
import java.util.Date;
import java.util.Base64.Decoder;

import javax.crypto.SecretKey;

import org.hibernate.validator.internal.util.logging.LoggerFactory;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

@Component 
public class Jwtutils {
    private  static final Logger logger = org.slf4j.LoggerFactory.getLogger(Jwtutils.class);
    @Value("${spring.app.expirationmilisec}")
    private int expirationmilisec;
    @Value("${spring.app.jwtsecret}")
    private String jwtsecret;
    public String GetJwtFromHeaders(HttpServletRequest request){
      String bearertoken =  request.getHeader("Authorization");
      logger.debug(bearertoken);
        if(bearertoken!=null && bearertoken.startsWith("Bearer ")){
            return bearertoken.substring(7);
        }
        return  null;
    }
    public String GeneratingJwtTokens(UserDetails userdetails ){
        String username = userdetails.getUsername();
        return Jwts.builder()
        .subject(username)
        .issuedAt(new Date())
        .expiration(new Date(new Date().getTime() + expirationmilisec))
        .signWith(key())
        .compact();
    }
    public String GetUsernameFromJwt(String token){
       return Jwts.parser()
        .verifyWith((SecretKey)key())
        .build().parseSignedClaims(token)
        .getPayload().getSubject();

    }
    public Key key(){
        return Keys.hmacShaKeyFor(
            Decoders.BASE64.decode(jwtsecret)
        );

    }
    public Boolean ValidateJwtToken(String token){
        try{
         Jwts.parser()
           .verifyWith((SecretKey)key())
           .build()
           .parseSignedClaims(token);
           return true;

        }catch(MalformedJwtException e){
           logger.error("not valid",e.getMessage());
        }catch(ExpiredJwtException e){
            logger.error("expired jwt",e.getMessage());
        }catch(UnsupportedJwtException e){
            logger.error("unsupoted erro",e.getMessage());
        }catch(IllegalArgumentException e){
            logger.error("illegal arguement exception",e.getMessage());
        }
        return  false;
    }

    
}
