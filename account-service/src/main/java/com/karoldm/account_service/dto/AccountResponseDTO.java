package com.karoldm.account_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Data
public class AccountResponseDTO {
    private UUID id;
    private String ownerName;
}
