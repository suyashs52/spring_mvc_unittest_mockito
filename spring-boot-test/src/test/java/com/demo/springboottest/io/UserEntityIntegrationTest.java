package com.demo.springboottest.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.util.UUID;

@DataJpaTest
public class UserEntityIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    UserEntity userEntity;

    @BeforeEach
    void beforeEach(){
        userEntity=new UserEntity();
        userEntity.setUserid(UUID.randomUUID().toString());
        userEntity.setFirstName("Sergey");
        userEntity.setLastName("Kargopoloy");
        userEntity.setEmail("test@test.com");
        userEntity.setEncryptedPassword("12345678");

    }

    @Test
    void testUserEntity_whenValidUserDetailsProvided_shouldReturnStoreUserDetails(){
        //Arrange

        //Act
        UserEntity storeUserEntity= testEntityManager.persistAndFlush(userEntity);

        System.out.println(storeUserEntity.getId());
        //Assert
        Assertions.assertTrue(storeUserEntity.getId()>0);
        Assertions.assertEquals(userEntity.getUserid(),storeUserEntity.getUserid());
        Assertions.assertEquals(userEntity.getFirstName(),storeUserEntity.getFirstName());

    }

    @Test
    void testUserEntity_whenFirstNameTooLong_shouldThrowException(){
        //Arrange
        userEntity.setFirstName("123456781234567812345678123456781234567812345678");
        //Act

        //Assert
        Assertions.assertThrows(PersistenceException.class,()->{
            testEntityManager.persistAndFlush(userEntity);
        },"was expecting persistence excpetion");
    }


}
