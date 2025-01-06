package com.banking.accountservice.controller;

import com.banking.accountservice.entity.Account;
import com.banking.accountservice.services.AccountServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private final AccountServiceImpl accountSvcImpl;
    public static final Logger log= LogManager.getLogger(AccountController.class);

    public AccountController(AccountServiceImpl accountSvcImpl) {
        this.accountSvcImpl = accountSvcImpl;
    }


    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return new ResponseEntity<>(accountSvcImpl.createAccount(account), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Account>> retrieveAllAccounts() {
        return new ResponseEntity<>(accountSvcImpl.retrieveAllAccounts(), HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    @Transactional
    public ResponseEntity<Account> retrieveAccountById(@PathVariable Long accountId) {
        return new ResponseEntity<>(accountSvcImpl.retrieveAccountById(accountId), HttpStatus.OK);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId, @RequestBody Account accountDetails) {
        return new ResponseEntity<>(accountSvcImpl.updateAccount(accountId,accountDetails),HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Long accountId) {

        /*accountSvcImpl.deleteAccount(accountId);
        return ResponseEntity.noContent().build();*/

        try {
            accountSvcImpl.deleteAccount(accountId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }

    }

    @GetMapping("/user/{userId}")
    @Transactional
    public ResponseEntity<List<Account>> retrieveAccountsByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(accountSvcImpl.retrieveAccountsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/acctType/userId")
    @Transactional
    public ResponseEntity<List<Account>> retrieveAccountByTypeAndUserId(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String acctType) {
        return new ResponseEntity<>(accountSvcImpl.retrieveAcctByTypeAndUserId(userId,acctType), HttpStatus.OK);
    }

    public ResponseEntity<String> respondWithError(Exception e){
        return new ResponseEntity<>("Exception Occurred. More Info:"
                + e.getMessage(), HttpStatus.BAD_REQUEST);
    }



}
