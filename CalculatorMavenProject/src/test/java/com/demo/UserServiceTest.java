package com.demo;

import com.demo.data.UserRepository;
import com.demo.model.User;
import com.demo.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //use mockito class
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    EmailVerificationServiceImpl emailVerificationService;

    @InjectMocks
    UserServiceImpl userService;
    String firstName="Sergev";
    String  lastName="Kargopplov";
    String email="test@test.com";
    String password="123456";
    String repeatPass="123456";

    @BeforeEach
    void beforeEach(){
          firstName="Sergev";
           lastName="Kargopplov";
          email="test@test.com";
          password="123456";
          repeatPass="123456";
         // userService=new UserServiceImpl();
    }
    @Test
    void testCreatedUser_whenUserDetailProvided_returnUserObject(){
        //Arrange
        when(userRepository.save(any(User.class))).thenReturn(true);

        //Act

        User user=userService.createUser(firstName,lastName,email,password,repeatPass);

        //Assert
        assertNotNull(user,"The createUser should not be null");
        Mockito.verify(userRepository,Mockito.times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_whenUserCreated_returnedUserObjectContainSameFirstName(){
        //arrange
        UserService userService=new UserServiceImpl();
        String firstName="Sergev";
        String  lastName="Kargopplov";
        String email="test@test.com";
        String password="123456";
        String repeatPass="123456";
        //act
        User user=userService.createUser(firstName,lastName,email,password,repeatPass);


        //assert
        assertEquals(firstName,user.getFirstName());
    }



    @Test
    void testCreateUser_whenSaveMethodThrowsException_thenThrowsUserServiceException(){
        //arrange
        when(userRepository.save(any(User.class))).thenThrow(RuntimeException.class);
        //act & assert
        assertThrows(UserServiceException.class,()->{
            User user=userService.createUser(firstName,lastName,email,password,repeatPass);

        },"shold throw UserServiceException");
        //assert
    }

    @Test
    void testCreateUser_whenNotificationExceptionThrown_throwsUserServiceExcpetion(){
        when(userRepository.save(any(User.class))).thenReturn(true);
//throw the exception
        doThrow(EmailVerificationServiceException.class).
                when(emailVerificationService)
                .scheduleEmailConfirmation(any(User.class));

        assertThrows(UserServiceException.class,()->{
            User user=userService.createUser(firstName,lastName,email,password,repeatPass);

        },"shold throw UserServiceException");

        verify(emailVerificationService,times(1)).scheduleEmailConfirmation(any(User.class));
    }


    @Test
    void testCreateUser_whenUserCreated_callRealMethod(){
        when(userRepository.save(any(User.class))).thenReturn(true);
        doCallRealMethod().when(emailVerificationService)
                        .scheduleEmailConfirmation(any(User.class));


        User user=userService.createUser(firstName,lastName,email,password,repeatPass);

        verify(emailVerificationService,times(1)).scheduleEmailConfirmation(any(User.class));
    }
}
