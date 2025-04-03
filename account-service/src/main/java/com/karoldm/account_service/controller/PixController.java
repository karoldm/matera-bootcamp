package com.karoldm.account_service.controller;


import com.karoldm.account_service.dto.PixDTO;
import com.karoldm.account_service.dto.PixRequestDTO;
import com.karoldm.account_service.dto.PixResponseDTO;
import com.karoldm.account_service.service.PixService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pix")
@RequiredArgsConstructor
@Tag(name="Pix service", description = "API to make transitions by pix")
public class PixController {
    private final PixService pixService;

    @PostMapping
    @Operation(description = "Send pix from a account to another using the pix key")
    public ResponseEntity<PixResponseDTO> sendPix(@RequestBody @Valid PixRequestDTO pixRequestDTO) {
        PixResponseDTO response = pixService.sendPix(pixRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
