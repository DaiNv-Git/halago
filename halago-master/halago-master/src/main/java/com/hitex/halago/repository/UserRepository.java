package com.hitex.halago.repository;

import com.hitex.halago.model.DAO.UserDao;
import com.hitex.halago.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User,String> {
    @Query("select u.password from User u where u.username=:username")
    String findByUsername(@Param("username")String username);

    @Query("select u from User u where (u.username=:username or u.email=:username)")
    User findUser(@Param("username")String username);

    @Query("select Count(u) from User u where u.email=:email")
    int checkMail(@Param("email")String email);

    @Query("select u from User u where u.username=:username")
    User checkUsername(@Param("username")String username);

    @Query("select u from User u where u.id=:id")
    User findUserById(@Param("id")int id);

}
