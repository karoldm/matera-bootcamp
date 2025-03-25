package com.karoldm.bacen_service.service;

import com.karoldm.bacen_service.dto.PixKeyRequestDTO;
import com.karoldm.bacen_service.dto.PixKeyResponseDTO;
import com.karoldm.bacen_service.model.PixKey;
import com.karoldm.bacen_service.repository.KeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PixKeyService {

    private final KeyRepository keyRepository;

    public PixKeyResponseDTO createKey(final PixKeyRequestDTO keyRequestDTO) {
        PixKey newKey = PixKey
                .builder()
                .keyValue(keyRequestDTO.keyValue())
                .enabled(keyRequestDTO.enabled())
                .build();
        keyRepository.save(newKey);

        return new PixKeyResponseDTO(newKey.getKeyValue(), newKey.isEnabled());
    }

    public PixKeyResponseDTO getKey(final String keyValue) throws NoSuchElementException {
        PixKey key = keyRepository.findByKeyValue(keyValue).orElseThrow(
                () -> new NoSuchElementException("No key founded!")
        );
        return new PixKeyResponseDTO(key.getKeyValue(), key.isEnabled());
    }
}
