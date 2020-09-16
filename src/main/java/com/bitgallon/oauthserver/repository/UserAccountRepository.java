package com.bitgallon.oauthserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bitgallon.oauthserver.entities.User;

import java.util.Optional;


public interface UserAccountRepository extends JpaRepository<User, Long> {

	@Query("SELECT DISTINCT user FROM User user " +
            "INNER JOIN FETCH user.authorities AS authorities " +
            "WHERE user.username = :username and user.clientId = :customerId")
    Optional<User> findByUsername(@Param("username") String username, @Param("customerId") String customerId);

}
