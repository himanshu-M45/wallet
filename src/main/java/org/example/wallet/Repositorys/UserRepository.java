package org.example.wallet.Repositorys;

import org.example.wallet.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u.password FROM User u WHERE u.username = :username")
    Optional<String> findPasswordByUsername(@Param("username") String username);

    @Query("SELECT u.wallet.id FROM User u WHERE u.id = :userId")
    Integer findWalletIdByUserId(@Param("userId") Integer userId);

    Optional<User> findByUsername(String username);
}
