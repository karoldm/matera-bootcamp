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
            log.error("Error while create pix key on bacen", ex);
            throw new ErrorBacenIntegrationException("Error while create pix key on bacen");
        }
    }

    public PixKeyResponseDTO getPixKey(final String pixKey) {
        try {
            return bacenClient.getKey(pixKey);
        } catch (Exception ex) {
            log.error("Error while get pix key on bacen", ex);
            throw new ErrorBacenIntegrationException("Error while get pix key on bacen");
        }
    }

    public Void deletePixKey(final String pixKey){
        try {
            bacenClient.deleteKey(pixKey);
        } catch (Exception ex) {
            log.error("Error while delete pix key on bacen", ex);
            throw new ErrorBacenIntegrationException("Error while delete pix key on bacen");
        }
        return null;
    }

    public PixKeyResponseDTO updatePixKey(PixKeyRequestDTO pixKeyRequestDTO, final String keyValue) {
        try {
            return bacenClient.updateKey(pixKeyRequestDTO, keyValue);
        } catch (Exception ex) {
            log.error("Error while update pix key on bacen", ex);
            throw new ErrorBacenIntegrationException("Error while update pix key on bacen");
        }
    }
}
