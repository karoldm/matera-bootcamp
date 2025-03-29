package com.karoldm.bacen_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PixKeyRequestDTO(
        @NotEmpty
        String keyValue,
        boolean enabled
) {}
