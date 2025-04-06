package com.karoldm.account_service.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PixHistoryResponseDTO {
    List<PixDTO> pixHistory;
    Integer totalPix;
}
