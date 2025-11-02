package com.daily_expense_tracker.service;

import com.daily_expense_tracker.model.Expense;
import com.daily_expense_tracker.model.RecurringExpense;
import com.daily_expense_tracker.repository.RecurringExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class RecurringExpenseService {
    @Autowired
    private RecurringExpenseRepository recurringExpenseRepository;
    
    @Autowired
    private ExpenseService expenseService;  // We'll use this to create actual expenses
    
    // Save or update recurring expense
    public RecurringExpense saveRecurringExpense(RecurringExpense recurringExpense) {
        return recurringExpenseRepository.save(recurringExpense);
    }
    
    // Get all active recurring expenses for a user
    public List<RecurringExpense> getActiveRecurringExpenses(Long userId) {
        return recurringExpenseRepository.findByUserIdAndActiveTrue(userId);
    }
    
    // Get all recurring expenses (active + inactive)
    public List<RecurringExpense> getAllRecurringExpenses(Long userId) {
        return recurringExpenseRepository.findByUserId(userId);
    }
    
    // Get by ID
    public Optional<RecurringExpense> getRecurringExpenseById(Long id) {
        return recurringExpenseRepository.findById(id);
    }
    
    // Deactivate recurring expense (soft delete)
    public void deactivateRecurringExpense(Long id) {
        Optional<RecurringExpense> recExpOpt = recurringExpenseRepository.findById(id);
        if (recExpOpt.isPresent()) {
            RecurringExpense recExp = recExpOpt.get();
            recExp.setActive(false);
            recurringExpenseRepository.save(recExp);
        }
    }
    
    // Create an actual expense from a recurring template
    public Expense createExpenseFromRecurring(RecurringExpense recurringExpense) {
        Expense expense = new Expense();
        expense.setAmount(recurringExpense.getAmount());
        expense.setCategory(recurringExpense.getCategory());
        expense.setSubcategory(recurringExpense.getSubcategory());
        expense.setDescription(recurringExpense.getDescription());
        expense.setUserId(recurringExpense.getUserId());
        expense.setDate(LocalDate.now());
        expense.setTime(recurringExpense.getPreferedtime() != null ? 
                       recurringExpense.getPreferedtime() : LocalTime.now());
        
        return expenseService.saveExpense(expense);
    }
}

