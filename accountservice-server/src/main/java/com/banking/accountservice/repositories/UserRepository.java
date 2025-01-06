package com.banking.accountservice.repositories;

import com.banking.accountservice.entity.Account;
import com.banking.accountservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
