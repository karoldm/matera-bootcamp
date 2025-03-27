package com.karoldm.account_service.controller;

import com.karoldm.account_service.dto.AccountDTO;
import com.karoldm.account_service.dto.AccountRequestDTO;
import com.karoldm.account_service.dto.AccountResponseDTO;
import com.karoldm.account_service.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody @Valid AccountRequestDTO accountRequestDTO) {
        AccountResponseDTO response = accountService.createAccount(accountRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccount(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable UUID id, @RequestBody @Valid AccountRequestDTO accountRequestDTO){
        AccountResponseDTO updatedAccount = accountService.updateAccount(accountRequestDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedAccount);
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> listAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.listAllAccounts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
