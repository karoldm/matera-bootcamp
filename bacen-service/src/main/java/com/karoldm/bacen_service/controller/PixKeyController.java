package com.karoldm.bacen_service.controller;

import com.karoldm.bacen_service.dto.PixKeyRequestDTO;
import com.karoldm.bacen_service.dto.PixKeyResponseDTO;
import com.karoldm.bacen_service.excetions.KeyAlreadyExistException;
import com.karoldm.bacen_service.service.PixKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
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

    @DeleteMapping("/{keyValue}")
    public ResponseEntity<Void> deleteKey(@PathVariable String keyValue){
        keyService.deletekey(keyValue);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @PutMapping("/{keyValue}")
    public ResponseEntity<PixKeyResponseDTO> updateKey(@RequestBody @Valid PixKeyRequestDTO pixKeyRequestDTO, @PathVariable String keyValue){
        PixKeyResponseDTO response = keyService.updateKey(pixKeyRequestDTO, keyValue);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
