package com.demo.springboottest.security;

import com.demo.springboottest.service.UsersService;
import com.demo.springboottest.shared.SpringApplicationContext;
import com.demo.springboottest.shared.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {

        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            byte[] inputStreamBytes= StreamUtils.copyToByteArray(request.getInputStream());
            Map<String,String> jsonRequest=new ObjectMapper().readValue(inputStreamBytes,Map.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    jsonRequest.get("email"),
                    jsonRequest.get("password"),
                    new ArrayList<>()
            ));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
         String userName=((UserDetails)authResult.getPrincipal()).getUsername();
         String token= Jwts.builder()
                 .setSubject(userName)
                 .setExpiration(new Date(System.currentTimeMillis()+(long) 864000000))
                 .signWith(SignatureAlgorithm.HS512,SecurityConstants.TOKEN_SECRET)
                 .compact();

        UsersService usersService=(UsersService) SpringApplicationContext.getBean("usersService");
        UserDto userDto=usersService.getUser(userName);
        response.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX+token);
        response.addHeader("UserID",userDto.getUserid());

    }
}
