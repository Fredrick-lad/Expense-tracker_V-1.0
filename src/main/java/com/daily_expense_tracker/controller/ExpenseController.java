package com.daily_expense_tracker.controller;

import com.daily_expense_tracker.model.Expense; // to represent expense data
import com.daily_expense_tracker.service.ExpenseService; // to handle business logic
import org.springframework.beans.factory.annotation.Autowired; // to inject dependencies
import org.springframework.http.HttpStatus; // to handle HTTP status codes
import org.springframework.http.ResponseEntity; // to handle HTTP responses
import org.springframework.web.bind.annotation.*; // to define REST endpoints

// import java.time.LocalDate; // to handle date operations
import java.util.List; // to handle lists
import java.util.Optional; // to handle optional values

@RestController // to define this class as a REST controller
@RequestMapping("/api/expenses") // base path for all expense-related endpoints
@CrossOrigin(origins = "*") // even if front end is deployed elsewhere it can access this api

public class ExpenseController {

    @Autowired // inject ExpenseService to handle business logic
    private ExpenseService expenseService;
    

     // ==================== GET ENDPOINTS (Retrieve Data) ====================


    /**
     * Get all expenses for a user.
     * URL: /api/expenses?userId=1
     * Returns a list of all expenses for the specified user.
     * Method: GET
     */
    @GetMapping
    public ResponseEntity<Optional<Expense>> getAllUserExpenses(@RequestParam Long userId){
        //@RequestParam extracts userId from URL query parameters (?userId=1)

        // Call the service to get expenses by userId
        Optional<Expense> expenses = expenseService.getExpensesByUserId(userId);

        // Return the list of expenses with HTTP status 200 (OK)
        return ResponseEntity.ok(expenses);
    }

    /**
     * Get a single expense by ID
     * URL: GET /API/expenses/5
     * Returns: The expense with ID 5, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id){
        //@PathVariable extracts id from the URL path (/api/expenses/5)

        // Call the service to get the expense by ID
        Optional<Expense> expense = expenseService.getExpenseById(id);

        // If expense exists, return it with 200 OK
        // If not, return 404 Not Found
        return expense.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get expenses filtered by category
     * URL: GET /API/expenses/category?userId=1&category=food
     * Returns: All food expenses for that user
     */
    
    @GetMapping("/category")
    public ResponseEntity<List<Expense>> getExpensesByCategory (
            @RequestParam Long userId,
            @RequestParam String category){
        
        // Calls service method to get expense by user id and category
        List<Expense> expenses = expenseService.getExpensesByCategory(userId, category);
        return ResponseEntity.ok(expenses);
    }
    
    /**
     * Calculate total expenses for a date range
     * URL: GET /api/expenses/total?userId=1&startDate=2025-10-01&endDate=2025-10-31
     * Returns: A single number (e.g., 523.50) - total amount spent
     */
    
    @GetMapping("/total")
    public ResponseEntity<Double> getTotalExpenses(@RequestParam Long userId){
        Double total = expenseService.calculateTotalExpenses(userId);
        return ResponseEntity.ok(total);
    }
    
    
     // ==================== POST ENDPOINT (Create Data) ====================
    
    
    /**
     * Create a new expense
     * URL: POST /api/expenses
     * Body: JSON with expense data
     * Returns: The created expense (now with an ID assigned by database)
     */
        
    @PostMapping  // Handles POST requests to /api/expenses
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        // @RequestBody: Spring converts JSON from request body into Expense object
        
        // Save expense (service handles it)
        Expense savedExpense = expenseService.saveExpense(expense);
        
        // Return 201 CREATED status with the saved expense
        // 201 means "successfully created a new resource"
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
    }
     
    // ==================== PUT ENDPOINT (Update Data) ====================
    
    /**
     * Update an existing expense
     * URL: PUT /api/expenses/5
     * Body: JSON with updated expense data (same format as POST)
     * Returns: The updated expense
     */
    
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id,  // Extract ID from URL path
            @RequestBody Expense expense){  // Extract expense data from JSON body
        
        // Set id to ensure we updating the correct expense
        expense.setId(id);
        
        // Save (repository.save() updates if ID exists, creates if not)
        Expense updatedExpense = expenseService.saveExpense(expense);
        
        // Return 200 OK with updated expense
        return ResponseEntity.ok(updatedExpense);
        
    }
    
     // ==================== DELETE ENDPOINT (Remove Data) ====================
    
    /**
     * Delete an expense
     * URL: DELETE /api/expenses/5
     * Returns: 204 No Content (success, but no data to return)
     */
    @DeleteMapping("/{id}")  // Handles DELETE requests to /api/expenses/{id}
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        // ResponseEntity<Void> means "no data in response body"
        
        // Delete the expense
        expenseService.deleteExpense(id);
        
        // Return 204 No Content (successful deletion, no data to send back)
        return ResponseEntity.noContent().build();
    }
}