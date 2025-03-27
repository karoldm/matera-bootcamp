package com.karoldm.account_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Data
public class AccountRequestDTO {
    @NotEmpty
    private String ownerName;
    @NotNull
    private Integer agencyNumber;
    @NotNull
    private Integer accountNumber;
    @NotEmpty
    private String pixKey;
}
