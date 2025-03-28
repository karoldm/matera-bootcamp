package com.karoldm.account_service.feign;

import com.karoldm.account_service.exception.ErrorBacenIntegrationException;
import com.karoldm.account_service.feign.dto.PixKeyRequestDTO;
import com.karoldm.account_service.feign.dto.PixKeyResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class BacenService {

    private final BacenClient bacenClient;

    public PixKeyResponseDTO createPixKey(final String pixKey) {
        try {
            PixKeyRequestDTO pixKeyRequestDTO = new PixKeyRequestDTO(
                    pixKey,
                    Boolean.TRUE
            );

            return bacenClient.createKey(pixKeyRequestDTO);
        } catch (Exception ex) {
            log.error("Error while creating pix key on bacen", ex);
            throw new ErrorBacenIntegrationException("Error while creating pix key on bacen");
        }
    }

    public PixKeyResponseDTO getPixKey(final String pixKey) {
        try {
            return bacenClient.getKey(pixKey);
        } catch (Exception ex) {
            log.error("Error while getting pix key on bacen", ex);
            throw new ErrorBacenIntegrationException("Error while getting pix key on bacen");
        }
    }
}
