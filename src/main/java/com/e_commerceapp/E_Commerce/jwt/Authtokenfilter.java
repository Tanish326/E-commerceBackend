package com.e_commerceapp.E_Commerce.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zaxxer.hikari.util.Credentials;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Component 
public class Authtokenfilter extends OncePerRequestFilter {
    @Autowired 
    private Jwtutils jwtutils;
    @Autowired 
    private UserDetailsService UserDetailsService;


    @Override 
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,IOException{
          try{
            String jwt = parseJwt(request);
            if(jwt!=null && jwtutils.ValidateJwtToken(jwt)){
                String username = jwtutils.GetUsernameFromJwt(jwt);
            UserDetails userDetails = UserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }


          }catch(Exception e){
            logger.error(e.getMessage());
          }
          filterChain.doFilter(request, response);
    }
    public String parseJwt(HttpServletRequest request){
        String jwt = jwtutils.GetJwtFromHeaders(request);
        return jwt;
    }
}
