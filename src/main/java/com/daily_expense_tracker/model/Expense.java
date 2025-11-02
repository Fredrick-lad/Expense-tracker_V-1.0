package com.daily_expense_tracker.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;



@Entity
@Table(name = "expenses")
@Data // lombok uto generates te setters getters toString equals hashcode

public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false)
    private String category; //food transport, phone

    private String subcategory;// breakfast lunch supper snacks

    @Column(length = 500)
    private String description;
    
    @Column(nullable = false)
    private Long userId; //link to user 
}
