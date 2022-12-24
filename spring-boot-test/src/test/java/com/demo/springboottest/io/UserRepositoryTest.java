package com.demo.springboottest.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.UUID;

@DataJpaTest
public class UserRepositoryTest {
    UserEntity userEntity;

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UserRepository userRepository;
    @Test
    void testFindByEmail_whenCorrectEmail_returnUserEntity(){
        //Arrange
        userEntity=new UserEntity();
        userEntity.setUserid(UUID.randomUUID().toString());
        userEntity.setFirstName("Sergey");
        userEntity.setLastName("Kargopoloy");
        userEntity.setEmail("test@test.com");
        userEntity.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(userEntity);

        //Act
       UserEntity storeUser= userRepository.findByEmail(userEntity.getEmail());

        //Assertion
        Assertions.assertEquals(userEntity.getEmail(),storeUser.getEmail(),"The return email address should match with excepted value");
    }

    @Test
    void testFindUserWithEmailEndsWith_whenGivenEmailDomain_returnUserWithGivenDomain(){
        //Arrange
        userEntity=new UserEntity();
        userEntity.setUserid(UUID.randomUUID().toString());
        userEntity.setFirstName("Sergey");
        userEntity.setLastName("Kargopoloy");
        userEntity.setEmail("test@test.com");
        userEntity.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(userEntity);

        String emailDomain="@test.com";

        List<UserEntity> userEntityList= userRepository.findByUsersWithEmailEndingWith(emailDomain);

        Assertions.assertEquals(1,userEntityList.size(),"there should be 1 user in list");

    }
}
