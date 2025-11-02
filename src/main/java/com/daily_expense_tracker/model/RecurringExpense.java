package com.daily_expense_tracker.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Entity
@Table(name = "recurring_expenses")
@Data

public class RecurringExpense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String category;

    private String subcategory;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private String  frequency;// DAYLY WEEKLY OOR MONTHLY OR YEARLY

    private LocalTime preferedtime; // what time of day is it ussualy logges at@

    @Column(nullable = false)
    private Boolean active; // is this expense still recurring?

    // link to user 
    private Long userId;
    
}
