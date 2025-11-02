package com.daily_expense_tracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data

public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable= false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;  // hahing done later using spring securty

    @Column(nullable = false, unique = true)
    private String email;

}
