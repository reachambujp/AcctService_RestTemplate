package com.banking.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    private Long accountId;
    private User user;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private String currency;
    private LocalDateTime createdAt;



}

