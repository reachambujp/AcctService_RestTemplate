package com.banking.accountservice.services;

import com.banking.accountservice.entity.Account;
import com.banking.accountservice.entity.User;
import com.banking.accountservice.interfaces.AccountInterfaceSVC;
import com.banking.accountservice.repositories.AccountRepository;
import com.banking.accountservice.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountInterfaceSVC {

    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final UserRepository userRepository;
    public static final Logger log = LogManager.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Account createAccount(Account account) {
        System.out.println("Value of user id:"+account.getUser().getUserId());
        User usr = userRepository.findById(account.getUser().getUserId()).
                orElseThrow(() -> new RuntimeException("User not found with id: " +
                        account.getUser().getUserId()));
        account.setUser(usr);
        return accountRepository.save(account);
    }

    @Override
    public List<Account> retrieveAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account retrieveAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));
    }


    @Override
    public Account updateAccount(Long accountId, Account accountDetails) {

        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setAccountNumber(accountDetails.getAccountNumber());
            account.setAccountType(accountDetails.getAccountType());
            account.setBalance(accountDetails.getBalance());
            account.setCurrency(accountDetails.getCurrency());
            return accountRepository.save(account);
        } else {
            throw new RuntimeException("Account not found");
        }
    }

    @Override
    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);

    }

    @Override
    public List<Account> retrieveAccountsByUserId(Long userId) {
        return accountRepository.findByUserUserId(userId);
    }

    @Override
    public List<Account> retrieveAcctByTypeAndUserId(Long userId, String acctType) {
        return accountRepository.findByUserUserIdAndAccountType(userId, acctType);
    }
}
