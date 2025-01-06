package com.banking.accountservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {


    private Long userId;
    private String username;
    private String email;
    private String passwordHash;
    private String phoneNumber;
    private Boolean twoFactorEnabled;
    private String kycStatus;



}
