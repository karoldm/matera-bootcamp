package com.karoldm.bacen_service.service;

import com.karoldm.bacen_service.dto.PixKeyRequestDTO;
import com.karoldm.bacen_service.dto.PixKeyResponseDTO;
import com.karoldm.bacen_service.excetions.KeyAlreadyExistException;
import com.karoldm.bacen_service.excetions.KeyNotFoundException;
import com.karoldm.bacen_service.model.PixKey;
import com.karoldm.bacen_service.repository.KeyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PixKeyService {

    private final KeyRepository keyRepository;

    @Transactional
    public PixKeyResponseDTO createKey(final PixKeyRequestDTO keyRequestDTO) throws KeyAlreadyExistException {

        if(keyRepository.existsByKeyValue(keyRequestDTO.keyValue())){
            throw new KeyAlreadyExistException(
                    String.format("The key: %s already exist on the system.", keyRequestDTO.keyValue())
            );
        }

        PixKey newKey = PixKey
                .builder()
                .keyValue(keyRequestDTO.keyValue())
                .enabled(keyRequestDTO.enabled())
                .build();
        keyRepository.save(newKey);

        return new PixKeyResponseDTO(newKey.getKeyValue(), newKey.isEnabled());
    }

    @Transactional
    public PixKeyResponseDTO getKey(final String keyValue) throws KeyNotFoundException {
        PixKey key = keyRepository.findByKeyValue(keyValue).orElseThrow(
                () -> new KeyNotFoundException(String.format("The key %s doesn't exists.", keyValue))
        );
        return new PixKeyResponseDTO(key.getKeyValue(), key.isEnabled());
    }
}
