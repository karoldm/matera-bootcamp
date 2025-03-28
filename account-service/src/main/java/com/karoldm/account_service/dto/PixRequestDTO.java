package com.karoldm.account_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@Data
public class PixRequestDTO {
    @NotEmpty
    private String pixKeyPayer;
    @NotEmpty
    private String pixKeyReceiver;
    @NotNull
    @Positive(message = "The value must be greater than zero.")
    private BigDecimal pixValue;
    @NotEmpty
    private String idempotent;
}
