package com.susan.digitalbanking.digital_banking_api;

import com.susan.digitalbanking.digital_banking_api.entity.AccountType;
import com.susan.digitalbanking.digital_banking_api.entity.BankAccount;
import com.susan.digitalbanking.digital_banking_api.entity.Customer;
import com.susan.digitalbanking.digital_banking_api.repository.BankAccountRepository;
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

    @Bean
    CommandLineRunner testDb(CustomerRepository customerRepo, BankAccountRepository accountRepo){
        return args -> {

            if(customerRepo.findByEmail("john@gmail.com").isEmpty()){

                Customer c = new Customer(null, "John Doe", "john@gmail.com");
                customerRepo.save(c);

                BankAccount acc1 = new BankAccount(
                        "ACC1001",
                        new BigDecimal("1000.00"),
                        AccountType.SAVINGS,
                        c
                );

                BankAccount acc2 = new BankAccount(
                        "ACC1002",
                        new BigDecimal("500.00"),
                        AccountType.CURRENT,
                        c
                );

                accountRepo.save(acc1);
                accountRepo.save(acc2);

                System.out.println("Customer and accounts saved successfully!");
            }
        };
    }

}
