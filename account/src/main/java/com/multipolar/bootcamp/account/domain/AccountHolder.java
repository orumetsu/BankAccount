package com.multipolar.bootcamp.account.domain;

import lombok.*;

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
