package com.daily_expense_tracker.controller;

import com.daily_expense_tracker.model.Expense;
import com.daily_expense_tracker.model.RecurringExpense;
import com.daily_expense_tracker.service.RecurringExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recurring")
@CrossOrigin(origins = "*")

public class RecurringExpenseController {
    @Autowired
    private RecurringExpenseService recurringExpenseService;

    // ==================== GET ENDPOINTS ====================
    
    /**
     * Get all active recurring expenses for a user
     * URL: GET /api/recurring?userId=1
     * Returns: List of active recurring expense templates
     * Use case: Show user their current subscriptions/recurring expenses
     */

     @GetMapping
     public ResponseEntity<List<RecurringExpense>> getActiveRecurringExpenses(@RequestParam Long userId){
        List<RecurringExpense> recurringExpenses = recurringExpenseService.getActiveRecurringExpenses(userId);

        return ResponseEntity.ok(recurringExpenses);
     }

    /**
     * Get ALL recurring expenses (active + inactive)
     * URL: GET /api/recurring/all?userId=1
     * Returns: Complete history including cancelled recurring expenses
     */

     @GetMapping("/all")
     public ResponseEntity<List<RecurringExpense>> getAllRecurringExpenses(@RequestParam Long userId){
        List<RecurringExpense> recurringExpenses = recurringExpenseService.getAllRecurringExpenses(userId);

        return ResponseEntity.ok(recurringExpenses);
     }
    /**
     * Get a single recurring expense by ID
     * URL: GET /api/recurring/5
     */

    @GetMapping("/{id}")
    public ResponseEntity<RecurringExpense> getRecurringExpenseById(@PathVariable Long id){
        Optional<RecurringExpense> recurringExpense = recurringExpenseService.getRecurringExpenseById(id);

        return recurringExpense.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // ==================== POST ENDPOINT ====================
    
    /**
     * Create a new recurring expense template
     * URL: POST /api/recurring
     */ 
    public ResponseEntity<RecurringExpense> createRecurringExpense(@RequestBody RecurringExpense recurringExpense) {
        
    // Ensure it's active by default if not specified
        if (recurringExpense.getActive() == null) {
            recurringExpense.setActive(true);
        }
        
        RecurringExpense saved = 
            recurringExpenseService.saveRecurringExpense(recurringExpense);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

      // ==================== PUT ENDPOINT ====================
    
    /**
     * Update a recurring expense template
     * URL: PUT /api/recurring/5
     * Body: Updated recurring expense data (same format as POST)
     * Use case: User increases subscription amount, changes frequency, etc.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecurringExpense> updateRecurringExpense(
            @PathVariable Long id,
            @RequestBody RecurringExpense recurringExpense) {
        
        recurringExpense.setId(id);
        RecurringExpense updated = 
            recurringExpenseService.saveRecurringExpense(recurringExpense);
        
        return ResponseEntity.ok(updated);
    }
    
    
    // ==================== DEACTIVATE (Soft Delete) ====================
    
    /**
     * Deactivate a recurring expense (soft delete)
     * URL: PUT /api/recurring/5/deactivate
     * Returns: 204 No Content
     * Use case: User cancels subscription but keeps history
     */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateRecurringExpense(@PathVariable Long id) {
        recurringExpenseService.deactivateRecurringExpense(id);
        return ResponseEntity.noContent().build();
    }
    
    
    // ==================== CREATE EXPENSE FROM TEMPLATE ====================
    
    /**
     * Manually create an expense from a recurring template
     * URL: POST /api/recurring/5/create-expense
     * Returns: The newly created Expense
     * Use case: User wants to manually log today's recurring expense
     * (Normally this would be automated with a scheduled job)
     */
    @PostMapping("/{id}/create-expense")
    public ResponseEntity<Expense> createExpenseFromRecurring(
            @PathVariable Long id) {
        
        // Get the recurring expense template
        Optional<RecurringExpense> recurringExpenseOpt = 
            recurringExpenseService.getRecurringExpenseById(id);
        
        if (recurringExpenseOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        // Create actual expense from template
        Expense createdExpense = recurringExpenseService
            .createExpenseFromRecurring(recurringExpenseOpt.get());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }
}






