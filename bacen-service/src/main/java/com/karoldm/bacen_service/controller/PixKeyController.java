package com.karoldm.bacen_service.controller;

import com.karoldm.bacen_service.dto.PixKeyRequestDTO;
import com.karoldm.bacen_service.dto.PixKeyResponseDTO;
import com.karoldm.bacen_service.excetions.KeyAlreadyExistException;
import com.karoldm.bacen_service.service.PixKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bacen/keys")
public class PixKeyController {

    private final PixKeyService keyService;

    @PostMapping
    public ResponseEntity<PixKeyResponseDTO> createKey(@RequestBody PixKeyRequestDTO keyRequestDTO) {
        return ResponseEntity.status(CREATED).body(keyService.createKey(keyRequestDTO));
    }

    @GetMapping("/{keyValue}")
    public ResponseEntity<PixKeyResponseDTO> getKey(@PathVariable String keyValue) {
        return ResponseEntity.status(OK).body(keyService.getKey(keyValue));
    }
}
