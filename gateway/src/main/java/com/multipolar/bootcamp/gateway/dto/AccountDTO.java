package com.multipolar.bootcamp.gateway.dto;

import lombok.*;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AccountDTO {
    private String id;
    private String accountNumber;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private AccountHolder accountHolder;
    private Double balance;
    private LocalDateTime openingDate;
    private LocalDateTime closingDate;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
class AccountHolder {
    private String nik;
    private String name;
    private String address;
}

enum AccountStatus {
    OPEN,
    CLOSED,
    FROZEN,
    SPECIAL_STATUS
}

enum AccountType {
    SAVINGS,
    CHECKING,
    CERTIFICATE_OF_DEPOSIT
}
