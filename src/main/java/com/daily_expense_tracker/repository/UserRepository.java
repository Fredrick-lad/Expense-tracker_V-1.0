package com.daily_expense_tracker.repository;

import com.daily_expense_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository

public interface UserRepository extends JpaRepository<User, Long> {

    //find by usernamr(for login)
    Optional<User> findByUsername(String username);

    //check if username exists during registration
    Boolean existsByUsername(String username);

    //find user by email
    Optional<User> findByEmail(String email);
    
}
