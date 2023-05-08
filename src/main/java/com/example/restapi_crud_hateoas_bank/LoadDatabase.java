package com.example.restapi_crud_hateoas_bank;

import com.example.restapi_crud_hateoas_bank.Entity.Account;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoadDatabase {

    private AccountRepository accountRepository;

    public LoadDatabase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            Account account1 = new Account("19823123901", 1021.99f);
            Account account2 = new Account("23151232335", 2004.23f);
            Account account3 = new Account("31239821983", 200.00f);

            accountRepository.saveAll(List.of(account1, account2, account3));
            System.out.println("sample database initialized");
        };
    }
}
