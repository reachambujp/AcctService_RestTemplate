package com.banking.accountservice.impl;

import com.banking.accountservice.entity.Account;
import com.banking.accountservice.exception.AccountNotFoundException;
import com.banking.accountservice.interfaces.AccountInterfaceSVC;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountInterfaceSVC {

    private final RestTemplate restTemplate;
    public static final Logger log = LogManager.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public Account createAccount(Account account) {
        final ParameterizedTypeReference<Account> response = new ParameterizedTypeReference<Account>(){};
        final HttpEntity<Account> request = new HttpEntity<>(account);
        String serverUrl = "http://localhost:8082/account";

        try{
            final ResponseEntity<Account> finalRes = restTemplate.exchange(
                    serverUrl, HttpMethod.POST,request,response, Optional.ofNullable(null));
            if(!finalRes.getStatusCode().is2xxSuccessful()){
                log.debug("Error while creating new Account");
            }
            else{
                log.debug("New Account created sucessfully");
            }
            log.debug("Final response from Server after Account creation:"+finalRes.getBody());
            return finalRes.getBody();
        }
        catch(RestClientException rce){
            throw new AccountNotFoundException("New Account not created="+account);
        }
    }

    @Override
    public List<Account> retrieveAllAccounts() {
        final ParameterizedTypeReference<List<Account>> response = new ParameterizedTypeReference<List<Account>>(){};
        String serverUrl = "http://localhost:8082/account";

        try{
            final ResponseEntity<List<Account>> finalRes = restTemplate.exchange(
                    serverUrl, HttpMethod.GET,null,response, Optional.ofNullable(null));
            if(!finalRes.getStatusCode().is2xxSuccessful()){
                log.debug("Error reading the account list response");
            }
            return finalRes.getBody();
        }
        catch(RestClientException rce){
            throw new AccountNotFoundException("Account list not found");
        }
    }

    @Override
    public Account retrieveAccountById(Long accountId) {
        final ParameterizedTypeReference<Account> response = new ParameterizedTypeReference<>(){};
        String serverUrl = "http://localhost:8082/account/{accountId}";

        try{
            final ResponseEntity<Account> finalRes = restTemplate.exchange(
                    serverUrl, HttpMethod.GET,null,response, accountId);
            if(!finalRes.getStatusCode().is2xxSuccessful()){
                log.debug("Error reading the response for given account id");
            }
            return finalRes.getBody();
        }
        catch(RestClientException rce){
            throw new AccountNotFoundException("Account not found for acct id="+accountId);
        }
    }


    @Override
    public Account updateAccount(Long accountId, Account accountDetails) {

        final ParameterizedTypeReference<Account> response = new ParameterizedTypeReference<Account>(){};
        final HttpEntity<Account> request = new HttpEntity<>(accountDetails);
        String serverUrl = "http://localhost:8082/account/{accountId}";

        try{
            final ResponseEntity<Account> finalRes = restTemplate.exchange(
                    serverUrl, HttpMethod.PUT,request,response, accountId);
            if(!finalRes.getStatusCode().is2xxSuccessful()){
                log.debug("Error updating Account");
            }
            else{
                log.debug(" Account updated sucessfully");
            }
            log.debug("Final response from Server after updating account:"+finalRes.getBody());
            return finalRes.getBody();
        }
        catch(RestClientException rce){
            log.error("Error while updating account", rce);
            throw new AccountNotFoundException("Account not Updated="+accountId);
        }
    }

     @Override
      public void deleteAccount(Long accountId) {
         final ParameterizedTypeReference<Account> response = new ParameterizedTypeReference<Account>(){};
         String serverUrl = "http://localhost:8082/account/{accountId}";

         try{
             final ResponseEntity<Account> finalRes = restTemplate.exchange(
                     serverUrl, HttpMethod.DELETE,null,response, accountId);
             log.debug("finalRes status code="+finalRes.getStatusCode());
             if(!finalRes.getStatusCode().is2xxSuccessful()){
                 log.debug("Error deleting Account");
             }else{
                 log.debug("User Account deleted :"+ accountId);
             }

         }
         catch(RestClientException rce){
             throw rce;
         }

      }

    @Override
    public List<Account> retrieveAccountsByUserId(Long userId) {
        final ParameterizedTypeReference<List<Account>> response = new ParameterizedTypeReference<>(){};
        String serverUrl = "http://localhost:8082/account/user/{userId}";

        try{
            final ResponseEntity<List<Account>> finalRes = restTemplate.exchange(
                    serverUrl, HttpMethod.GET,null,response, userId);
            if(!finalRes.getStatusCode().is2xxSuccessful()){
                log.debug("Error fetching account list for given user id");
            }
            return finalRes.getBody();
        }
        catch(RestClientException rce){
            throw new AccountNotFoundException("Account not found for user id="+userId);
        }
    }

    @Override
    public List<Account> retrieveAcctByTypeAndUserId(Long userId, String acctType) {
        final ParameterizedTypeReference<List<Account>> response = new ParameterizedTypeReference<>(){};
        String serverUrl = "http://localhost:8082/account/acctType/userId?userId={userId}&acctType={acctType}";

        try{
            final ResponseEntity<List<Account>> finalRes = restTemplate.exchange(
                    serverUrl, HttpMethod.GET,null,response, userId,acctType);
            if(!finalRes.getStatusCode().is2xxSuccessful()){
                log.debug("Error fetching account list for given user id and account type");
            }
            return finalRes.getBody();
        }
        catch(RestClientException rce){
            throw new AccountNotFoundException("Account not found for user id="+userId+ " & Acct Type="+acctType);
        }
    }
}
