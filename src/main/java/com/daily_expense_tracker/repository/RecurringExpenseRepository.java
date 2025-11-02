package com.daily_expense_tracker.repository;

import com.daily_expense_tracker.model.RecurringExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface RecurringExpenseRepository extends JpaRepository<RecurringExpense, Long> {
    
    //find all active recurring expenses by userId
    //the list part is for creating a list of the recurring expenses
    List<RecurringExpense> findByUserIdAndActiveTrue(Long userId);

    //Find all recurring expenses by userId
    List<RecurringExpense> findByUserId(Long userId);

    //find by frequency
    List<RecurringExpense> findByUserIdAndFrequency(Long userId, String frequency);
    
}
