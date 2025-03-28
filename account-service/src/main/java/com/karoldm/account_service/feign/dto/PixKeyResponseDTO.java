package com.karoldm.account_service.feign.dto;

public record PixKeyResponseDTO(
        String keyValue,
        boolean enabled
) {}