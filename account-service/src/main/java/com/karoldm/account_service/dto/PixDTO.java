package com.karoldm.account_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Data
public class PixDTO {
    private UUID id;
    private LocalDateTime createdAt;
    private String pixKeyPayer;
    private String pixKeyReceiver;
    private BigDecimal pixValue;
    private String idempotent;
}
