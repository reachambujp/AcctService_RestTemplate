package com.banking.accountservice.repositories;

import com.banking.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    // Custom query to fetch all accounts by userId
    List<Account> findByUserUserId(Long userId);

    // Custom query to fetch accounts by userId and accountType
    List<Account> findByUserUserIdAndAccountType(Long userId, String accountType);
}
