package com.karoldm.account_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class AccountDTO {
    private UUID id;
    private String ownerName;
    private Integer agencyNumber;
    private Integer accountNumber;
    private String pixKey;
    private BigDecimal balance;
}
