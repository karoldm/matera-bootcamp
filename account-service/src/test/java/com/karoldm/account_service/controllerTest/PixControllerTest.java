package com.karoldm.account_service.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karoldm.account_service.controller.PixController;
import com.karoldm.account_service.dto.PixDTO;
import com.karoldm.account_service.dto.PixRequestDTO;
import com.karoldm.account_service.dto.PixResponseDTO;
import com.karoldm.account_service.service.PixService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PixController.class)
public class PixControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PixController pixController;

    @MockitoBean
    private PixService pixService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void mustSendPix() throws Exception {

        PixRequestDTO pixRequestDTO = PixRequestDTO.builder()
                .pixValue(BigDecimal.valueOf(100.0))
                .idempotent("test")
                .pixKeyPayer("payer@pix.com")
                .pixKeyReceiver("receiver@pix.com")
                .build();

        PixDTO pixDTO = PixDTO.builder()
                .pixValue(BigDecimal.valueOf(100.0))
                .pixKeyReceiver("receiver@pix.com")
                .pixKeyPayer("payer@pix.com")
                .idempotent("test")
                .id(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .build();

        PixResponseDTO pixResponseDTO = PixResponseDTO.builder()
                .pix(pixDTO)
                .message("Pix sent successfully")
                .createdAt(pixDTO.getCreatedAt())
                .build();

        when(pixService.sendPix(pixRequestDTO)).thenReturn(pixResponseDTO);

        mockMvc.perform(post("/api/pix")
                        .content(objectMapper.writeValueAsString(pixRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("message").value(pixResponseDTO.getMessage()))
                .andExpect(jsonPath("$.createdAt").value(pixResponseDTO.getCreatedAt().toString()))
                .andExpect(jsonPath("pix").exists())
                .andExpect(jsonPath("pix.pixKeyReceiver").value(pixDTO.getPixKeyReceiver()))
                .andExpect(jsonPath("pix.pixKeyPayer").value(pixDTO.getPixKeyPayer()))
                .andExpect(jsonPath("pix.pixValue").value(pixDTO.getPixValue()))
                .andExpect(jsonPath("pix.idempotent").value(pixDTO.getIdempotent()))
                .andExpect(status().isCreated());
    }
}
