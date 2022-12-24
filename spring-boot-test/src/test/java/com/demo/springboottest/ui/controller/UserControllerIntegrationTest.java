package com.demo.springboottest.ui.controller;

import com.demo.springboottest.security.SecurityConstants;
import com.demo.springboottest.ui.response.UserRest;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.net.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,properties = {"server.port=8081","hostname=192.168.0.2"}) //look for springbootapplication class
@TestPropertySource(locations = "/application-test.properties",
properties = {"server.port=8082"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerIntegrationTest {

    @Value("${server.port}")
    private int serverPort;

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String authorizationToken;

    @Test
    void contextLoads(){
        System.out.println("server.port="+serverPort);
        System.out.println("Local Server Port="+localServerPort);

    }



    @Test
    @DisplayName("Users can be created")
    @Order(1)
    void testCreatedUser_whenValidDetailsProvided_returnUserDetails() throws JSONException {
        //Arrayange
        String createUserJson="{\"firstName\":\"Sergery\",\"lastName\":\"Kargoplay\"," +
                "\"email\":\"test3@test.com\",\"password\":\"12345678\"," +
                "\"repeatPassword\":\"12345678\"}";

        JSONObject userDetailJson=new JSONObject();
        userDetailJson.put("firstName","Sergery");
        userDetailJson.put("lastName","Kargoplay");
        userDetailJson.put("email","test3@test.com");
        userDetailJson.put("password","12345678");
        userDetailJson.put("repeatPassword","12345678");


        org.springframework.http.HttpHeaders header=new org.springframework.http.HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));


        HttpEntity<String> request=new HttpEntity<>(userDetailJson.toString(),header);

        //Act
       // testRestTemplate.postForEntity("/users",request,String.class);//return json string
        ResponseEntity<UserRest> createUserDetailsEntity= testRestTemplate.postForEntity(
                    "/users",request,UserRest.class);

        UserRest createUserDetails=createUserDetailsEntity.getBody();

        //Assert
        Assertions.assertEquals(HttpStatus.OK,createUserDetailsEntity.getStatusCode());
        Assertions.assertEquals(userDetailJson.getString("firstName"),createUserDetails.getFirstName(),
                "returned user's first name seems to be incorrect");


    }

    @Test
    @DisplayName("Get /Users requires JWT")
    @Order(2)
    void testGetUsers_whenMissingJWT_return403(){
        org.springframework.http.HttpHeaders headers=new org.springframework.http.HttpHeaders();
        headers.set("Accept","application/json");

        HttpEntity requestEntity=new HttpEntity(null,headers);

        ResponseEntity<List<UserRest>> response= testRestTemplate.exchange("/users", HttpMethod.GET, requestEntity
                , new ParameterizedTypeReference<List<UserRest>>() {
                });


        Assertions.assertEquals(HttpStatus.FORBIDDEN,response.getStatusCode(),"Http Statud code 403 has been  returned");

    }


    @Test
    @DisplayName("/login workds")
    @Order(3)
    void testUserLogin_whenValidCredentialsProvided_returnsJWTinAuthorizationHeader() throws JSONException {

        String loginCredentialsJson="{\"email\":\"test3@test.com\",\"password\":\"12345678\"}";
        JSONObject loginCredentials=new JSONObject();
        loginCredentials.put("email","test3@test.com");
        loginCredentials.put("password","12345678");

        HttpEntity<String> request=new HttpEntity<>(loginCredentialsJson.toString());

       ResponseEntity<String> response=  testRestTemplate.postForEntity("/users/login",request,null);

       authorizationToken=response.getHeaders().getValuesAsList(SecurityConstants.HEADER_STRING).get(0);
       Assertions.assertEquals(HttpStatus.OK,response.getStatusCode(),"HTTP status code should be 200");
        Assertions.assertNotNull(response.getHeaders().getValuesAsList(SecurityConstants.HEADER_STRING).get(0),"Response should contain Authorization header with JWT");
        Assertions.assertNotNull(response.getHeaders().getValuesAsList("UserID").get(0),"Response should contain USerID in response");
    }

    @Test
    @Order(4)
    @DisplayName("GET User works")
    void testGetUsers_whenValidJWTProvided_returnsUsers(){
        org.springframework.http.HttpHeaders headers=new org.springframework.http.HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(authorizationToken);
        HttpEntity requestEntity=new HttpEntity(headers);

        //act
        ResponseEntity<List<UserRest>> response= testRestTemplate.exchange("/users",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<UserRest>>() {
                });

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode(),"Http Status code must be 200");
        Assertions.assertTrue(response.getBody().size()==1,"There should be exactly 1 user");
    }
}
