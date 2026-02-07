package com.durga.bankingsys.service;

import com.durga.bankingsys.entity.Account;
import com.durga.bankingsys.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repo;

    public Account create(Account acc){

        return repo.save(acc);
    }

    public Account get(String accNo){
        return repo.findByAccountNumber(accNo).orElseThrow();
    }

    public Account deposit(String accNo, Double amt) {
        Account a = get(accNo);
        a.setBalance(a.getBalance()+amt);
        return repo.save(a);
    }

    public Account withdraw(String accNo, Double amt) {
        Account a = get(accNo);
        if(a.getBalance() < amt){
            throw new RuntimeException("Not enough balance");
        }
        a.setBalance(a.getBalance() - amt);
        return repo.save(a);
    }

    public List<Account> getAllAccounts() {
        return repo.findAll();
    }



    public String delete(String accNo) {
        Account a = get(accNo);
        repo.delete(a);
        return "Account Deleted";
    }
}
