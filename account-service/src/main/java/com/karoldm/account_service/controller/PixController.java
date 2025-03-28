package com.karoldm.account_service.controller;


import com.karoldm.account_service.dto.PixDTO;
import com.karoldm.account_service.dto.PixRequestDTO;
import com.karoldm.account_service.dto.PixResponseDTO;
import com.karoldm.account_service.service.PixService;
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
public class PixController {
    private final PixService pixService;

    @PostMapping
    public ResponseEntity<PixResponseDTO> sendPix(@RequestBody @Valid PixRequestDTO pixRequestDTO) {
        PixResponseDTO response = pixService.sendPix(pixRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
