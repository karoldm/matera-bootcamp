package com.karoldm.account_service.feign;

import com.karoldm.account_service.feign.dto.PixKeyRequestDTO;
import com.karoldm.account_service.feign.dto.PixKeyResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(
        contextId="bacenClient",
        name="Bacen",
        url="http://localhost:9090/api/bacen"
)
public interface BacenClient {
    @PostMapping("/keys")
    PixKeyResponseDTO createKey(PixKeyRequestDTO keyRequestDTO);

    @GetMapping(value = "/keys/{keyValue}")
    PixKeyResponseDTO getKey(@PathVariable final String keyValue);

    @DeleteMapping("/keys/{keyValue}")
    void deleteKey(@PathVariable final String keyValue);

    @PutMapping("/keys/{keyValue}")
    PixKeyResponseDTO updateKey(PixKeyRequestDTO pixKeyRequestDTO, @PathVariable String keyValue);
}
