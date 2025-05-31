package com.budgetblaze.UserService;

import com.budgetblaze.UserService.Service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EntityScan(basePackages = "com.budgetblaze.UserService.Model")
public class BudgetBlazeApplication {

	@Autowired
	EmailSenderService senderService;

	public static void main(String[] args) {
		SpringApplication.run(BudgetBlazeApplication.class, args);
	}


}
