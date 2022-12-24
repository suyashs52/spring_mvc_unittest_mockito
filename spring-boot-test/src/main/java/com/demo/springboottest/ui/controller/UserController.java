package com.demo.springboottest.ui.controller;

import com.demo.springboottest.service.UsersService;
import com.demo.springboottest.shared.UserDto;
import com.demo.springboottest.ui.request.UserDetailsRequestModel;
import com.demo.springboottest.ui.response.UserRest;
import org.h2.engine.Mode;
import org.h2.engine.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    UsersService usersService;


    @Autowired
    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public UserRest createUser(@RequestBody @Valid UserDetailsRequestModel userDetails) throws Exception{
        ModelMapper modelMapper=new
                ModelMapper();
        UserDto userDto= new ModelMapper().map(userDetails,UserDto.class);
        UserDto createUser= usersService.createUser(userDto);
        return modelMapper.map(createUser,UserRest.class);
    }

    @GetMapping
    public List<UserRest> getUsers(@RequestParam(value = "page",defaultValue = "0") int page,
                                   @RequestParam(value = "limit" ,defaultValue="2") int limit){
        List<UserDto> users=usersService.getUsers(page,limit);
        Type type=new TypeToken<List<UserRest>>(){}.getType();
        return new ModelMapper().map(users,type);
    }
}
