package com.daily_expense_tracker.repository;

import com.daily_expense_tracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository

public interface ExpenseRepository extends JpaRepository<Expense , Long> {
    // Custom query to find expenses by userId and date range
    List<Expense> findByUserId(Long userId);
    List<Expense> findByUserIdAndDate(Long userd, LocalDate date);
    List<Expense> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
    List<Expense> findByUserIdAndCategory(Long userId, String category);
}
