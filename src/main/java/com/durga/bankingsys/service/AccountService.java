package com.durga.bankingsys.service;

import com.durga.bankingsys.entity.Account;
import com.durga.bankingsys.entity.Transaction;
import com.durga.bankingsys.repository.AccountRepository;
import com.durga.bankingsys.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repo;

    @Autowired
    private TransactionRepository transactionRepo;

    public Account create(Account acc){

        return repo.save(acc);
    }

    public Account get(String accNo){
        return repo.findByAccountNumber(accNo).orElseThrow();
    }

    public Account deposit(String accNo, Double amt) {
        Account a = get(accNo);
        a.setBalance(a.getBalance()+amt);
        repo.save(a);

        Transaction t = new Transaction();
        t.setType("DEPOSIT");
        t.setAmount(amt);
        t.setTime(LocalDateTime.now());
        t.setAccount(a);

        transactionRepo.save(t);

        return a;
    }

    public Account withdraw(String accNo, Double amt) {
        Account a = get(accNo);
        if(a.getBalance() < amt){
            throw new RuntimeException("Not enough balance");
        }
        a.setBalance(a.getBalance() - amt);
        repo.save(a);

        Transaction t = new Transaction();
        t.setType("WITHDRAW");
        t.setAmount(amt);
        t.setTime(LocalDateTime.now());
        t.setAccount(a);

        transactionRepo.save(t);

        return a;
    }

    public List<Account> getAllAccounts() {
        return repo.findAll();
    }



    public String delete(String accNo) {
        Account a = get(accNo);
        repo.delete(a);
        return "Account Deleted";
    }

    @Transactional
    public void transfer(String senderAccNo, String receiverAccNo, Double amount) {
        Account sender = get(senderAccNo);
        Account receiver = get(receiverAccNo);

        if(sender.getBalance() < amount){
            throw new RuntimeException("Not enough balance");
        }
        //subtracting from sender
        sender.setBalance(sender.getBalance()-amount);

        //adding amount to receiver
        receiver.setBalance(receiver.getBalance()+amount);

        repo.save(sender);
        repo.save(receiver);

        Transaction t1 = new Transaction();
        t1.setType("SENT");
        t1.setAccount(sender);
        t1.setAmount(amount);
        t1.setTime(LocalDateTime.now());

        Transaction t2 = new Transaction();
        t2.setType("RECEIVED");
        t2.setAccount(sender);
        t2.setAmount(amount);
        t2.setTime(LocalDateTime.now());

        transactionRepo.save(t1);
        transactionRepo.save(t2);


    }

    public List<Transaction> history(String accNo) {
        return transactionRepo.findByAccount_AccountNumber(accNo);
    }
}
