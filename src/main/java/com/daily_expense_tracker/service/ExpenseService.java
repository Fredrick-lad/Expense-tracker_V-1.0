package com.daily_expense_tracker.service;

import com.daily_expense_tracker.model.Expense;
import com.daily_expense_tracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    // Create or Update Expense
    public Expense saveExpense(Expense expense){
        return expenseRepository.save(expense); // 
    }
    
    // Get Expense by ID
    public Optional<Expense> getExpenseById(Long id){
        return expenseRepository.findById(id);
    }

    // Get Expenses by User ID
    public Optional<Expense> getExpensesByUserId(Long userId){
        return expenseRepository.findById(userId);
    }

    // Get expenses for a specific date
    public List<Expense> getExpensesByUserIdAndDate(Long userId, LocalDate StartDate){
        return expenseRepository.findByUserIdAndDate(userId, StartDate); 
    }

    // Get expenses in a date range
    public List<Expense> getExpensesByDateRange(Long userId, LocalDate startDate, LocalDate endDate){
        return expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }

    // Get expenses by category 
    public List<Expense> getExpensesByCategory(Long userId, String category){
        return expenseRepository.findByUserIdAndCategory(userId, category);
    }

    // Delete Expense
    public void deleteExpense(Long id){
        expenseRepository.deleteById(id);
    }

    // Get all expenses
    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }

    // Calculate total expenses for a user
    public Double calculateTotalExpenses(Long userId){
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        return expenses.stream()
                       .mapToDouble(Expense::getAmount)
                       .sum();
    }
}
