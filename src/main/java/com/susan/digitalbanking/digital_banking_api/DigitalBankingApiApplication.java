package com.susan.digitalbanking.digital_banking_api;

import com.susan.digitalbanking.digital_banking_api.entity.AccountType;
import com.susan.digitalbanking.digital_banking_api.entity.BankAccount;
import com.susan.digitalbanking.digital_banking_api.entity.Customer;
import com.susan.digitalbanking.digital_banking_api.repository.BankAccountRepositoy;
import com.susan.digitalbanking.digital_banking_api.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class DigitalBankingApiApplication {

	public static void main(String[] args) {

        SpringApplication.run(DigitalBankingApiApplication.class, args);
	}

//    @Bean
//    CommandLineRunner testDb(CustomerRepository customerRepo, BankAccountRepositoy accountRepo){
//        return args -> {
//            Customer c = new Customer(null, "John Doe", "john@gmail.com");
//            customerRepo.save(c);
//
//            BankAccount acc = new BankAccount("ACC1001", new BigDecimal("1000.00"), AccountType.SAVINGS, c);
//            accountRepo.save(acc);
//
//            System.out.println("Customer and account saved successfully!");
//        };
//    }

}
