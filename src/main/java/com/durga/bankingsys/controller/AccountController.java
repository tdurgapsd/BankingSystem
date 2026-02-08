package com.durga.bankingsys.controller;

import com.durga.bankingsys.entity.Account;
import com.durga.bankingsys.entity.Transaction;
import com.durga.bankingsys.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService service;

    @PostMapping("/create")
    public Account create(@RequestBody Account acc){
        return service.create(acc);
    }

    @GetMapping("/{accNo}")
    public Account get(@PathVariable String accNo){
        return service.get(accNo);
    }

    @GetMapping()
    public List<Account> getAllAccounts(){
        return service.getAllAccounts();
    }

    @PostMapping("/deposit/{accNo}/{amt}")
    public Account deposit(@PathVariable String accNo, @PathVariable Double amt){
        return service.deposit(accNo, amt);
    }

    @PostMapping("/withdraw/{accNo}/{amt}")
    public Account withdraw(@PathVariable String accNo, @PathVariable Double amt){
        return service.withdraw(accNo, amt);
    }
    @DeleteMapping("/delete/{accNo}")
    public String delete(@PathVariable String accNo){
        return service.delete(accNo);
    }
    @PostMapping("/transfer/{sender}/{receiver}/{amount}")
    public void transfer(@PathVariable String sender, @PathVariable String receiver, @PathVariable Double amount){
         service.transfer(sender , receiver, amount);
    }

    @GetMapping("/history/{accNo}")
    public List<Transaction> history(@PathVariable String accNo){
        return service.history(accNo);
    }


}
