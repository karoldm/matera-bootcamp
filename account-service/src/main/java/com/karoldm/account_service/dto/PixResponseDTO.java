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
public class PixResponseDTO {
    private LocalDateTime createdAt;
    private String message;
    private PixDTO pix;
}
