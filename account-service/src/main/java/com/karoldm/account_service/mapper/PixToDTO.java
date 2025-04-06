package com.karoldm.account_service.mapper;

import com.karoldm.account_service.dto.PixDTO;
import com.karoldm.account_service.model.Pix;

public class PixToDTO {
    public static PixDTO entityToDTO(Pix pix){
        return PixDTO.builder()
                .id(pix.getId())
                .pixKeyPayer(pix.getPixKeyPayer())
                .pixKeyReceiver(pix.getPixKeyReceiver())
                .pixValue(pix.getPixValue())
                .createdAt(pix.getCreatedAt())
                .idempotent(pix.getIdempotent())
                .build();
    }
}
