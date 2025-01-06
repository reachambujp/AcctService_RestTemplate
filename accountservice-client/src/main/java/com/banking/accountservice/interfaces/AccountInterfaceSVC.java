package com.banking.accountservice.interfaces;

import com.banking.accountservice.entity.Account;

import java.util.List;

public interface AccountInterfaceSVC {

    Account createAccount(Account account);
    List<Account> retrieveAllAccounts();
    Account retrieveAccountById(Long accountId);
    Account updateAccount(Long accountId,Account accountDetails);
    void deleteAccount(Long accountId);
    List<Account> retrieveAccountsByUserId(Long userId);
    List<Account> retrieveAcctByTypeAndUserId(Long userId, String acctType);

}
