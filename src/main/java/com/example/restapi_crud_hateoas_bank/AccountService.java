package com.example.restapi_crud_hateoas_bank;

import com.example.restapi_crud_hateoas_bank.Entity.Account;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountService {
    private AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public List<Account> listAll() {
        return repository.findAll();
    }

    public Account get(Integer id) {
        return repository.findById(id).get();
    }

    public Account save(Account account) {
        return repository.save(account);
    }

    public Account deposit (float amount, Integer id){
        repository.deposit(amount, id);
        return repository.findById(id).get();
    }

    public Account withdraw (float amount, Integer id){
        repository.withdraw(amount, id);
        return repository.findById(id).get();
    }

    public void delete (Integer id){
        repository.deleteById(id);
    }

}


