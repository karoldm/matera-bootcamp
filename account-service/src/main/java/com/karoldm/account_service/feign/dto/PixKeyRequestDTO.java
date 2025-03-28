package com.karoldm.account_service.feign.dto;

public record PixKeyRequestDTO(
        String keyValue,
        boolean enabled
) {}