package com.demo.springboottest.ui.controller;

import com.demo.springboottest.service.UsersService;
import com.demo.springboottest.service.UsersServiceImpl;
import com.demo.springboottest.shared.UserDto;
import com.demo.springboottest.ui.request.UserDetailsRequestModel;
import com.demo.springboottest.ui.response.UserRest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UserController.class) //testing web layer only, only one controller class
//spring include only this controller for testing
//to disable web security
@AutoConfigureMockMvc(addFilters = false)
@MockBean({UsersServiceImpl.class})
public class UsersControllerWebLayerTest {

    @Autowired
    MockMvc mockMvc;


    //@MockBean //it will inject mock in application context
    @Autowired
    UsersService usersService;
    UserDetailsRequestModel userDetailsRequestModel;

    @BeforeEach
    void setUp(){
        userDetailsRequestModel =new UserDetailsRequestModel();
        userDetailsRequestModel.setFirstName("Sergery");
        userDetailsRequestModel.setLastName("Kargoplay");
        userDetailsRequestModel.setEmail("email@email.com");
        userDetailsRequestModel.setPassword("testtest");
        userDetailsRequestModel.setRepeatPassword("testtest");

    }

    @Test
    @DisplayName("Users can be created")
    void testCreatedUser_wehnValidUserDetailProvided_returnCreatedUserDetail() throws Exception {


        UserDto userDto=new ModelMapper().map(userDetailsRequestModel,UserDto.class);
        userDto.setUserid(UUID.randomUUID().toString());
        when(usersService.createUser(any(UserDto.class))).thenReturn(userDto);

      RequestBuilder requestBuilder= MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));
        System.out.println("\n\n\n\n");
        System.out.println(new ObjectMapper().writeValueAsString(userDetailsRequestModel));
        System.out.println("\n\n\n\n");
        MvcResult mvcResult= mockMvc.perform(requestBuilder).andReturn();
       String responseBodyAsString= mvcResult.getResponse().getContentAsString();
     UserRest createdUser=  new ObjectMapper().readValue(responseBodyAsString, UserRest.class);

        Assertions.assertEquals(userDetailsRequestModel.getFirstName(),createdUser.getFirstName(),"The return user first name is most likely incorrect");
    }



    @Test
    @DisplayName("First Name not empty")
    void testCreateUser_whenFirstNameNotProvided_return400StatusCode() throws Exception {

        userDetailsRequestModel.setFirstName("a");


        RequestBuilder requestBuilder= MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));
        //act
        MvcResult mvcResult= mockMvc.perform(requestBuilder).andReturn();


        //Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(),mvcResult.getResponse().getStatus()
        ,"Incorrect request status returned");
    }
}
