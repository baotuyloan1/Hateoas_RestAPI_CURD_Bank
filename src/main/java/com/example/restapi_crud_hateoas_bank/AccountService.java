package com.example.restapi_crud_hateoas_bank;

import com.example.restapi_crud_hateoas_bank.Account.Account;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountService {
    private AccountRepository repository;

    public AccountService(AccountRepository repository){
        this.repository = repository;
    }

    public List<Account> listAll(){
        return repository.findAll();
    }

    
}


