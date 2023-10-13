package com.multipolar.bootcamp.account.service;

import com.multipolar.bootcamp.account.domain.Account;
import com.multipolar.bootcamp.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    //get all accounts
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    //get account by id
    public Optional<Account> getAccountById(String id){
        return accountRepository.findById(id);
    }

    //create new account
    public Account createOrUpdateAccount(Account account){
        return accountRepository.save(account);
    }

    // delete account
    public void deleteAccountById(String id){
        accountRepository.deleteById(id);
    }
}
