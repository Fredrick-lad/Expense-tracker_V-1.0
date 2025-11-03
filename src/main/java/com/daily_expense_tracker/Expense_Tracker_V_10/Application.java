package com.daily_expense_tracker.Expense_Tracker_V_10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
    "com.daily_expense_tracker.model"  // Where your entities are
})
@EnableJpaRepositories(basePackages = {
    "com.daily_expense_tracker.repository"
})
@ComponentScan(basePackages = {
    "com.daily_expense_tracker" ,
	"com.daily_expense_traacker.service" // Where your services and other components are
})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
