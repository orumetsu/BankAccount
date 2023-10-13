package com.multipolar.bootcamp.account.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ErrorMessage {
    private String code;
    private String message;
}
