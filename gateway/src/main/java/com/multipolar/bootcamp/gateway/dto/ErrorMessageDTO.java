package com.multipolar.bootcamp.gateway.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ErrorMessageDTO {
    private String code;
    private String message;
}

