package com.demo.springboottest.io;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long> {

    UserEntity findByEmail(String email);

    UserEntity save(UserEntity userEntity);

    @Query("select user from UserEntity user where user.email like %:emailDomain")
    List<UserEntity> findByUsersWithEmailEndingWith(@Param("emailDomain") String emailDomain);
}
