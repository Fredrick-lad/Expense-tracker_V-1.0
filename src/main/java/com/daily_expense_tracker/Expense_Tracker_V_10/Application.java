package com.daily_expense_tracker.Expense_Tracker_V_10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.daily_expense_tracker.model") // where the entities are
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
