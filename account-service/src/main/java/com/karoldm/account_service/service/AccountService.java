package com.karoldm.account_service.service;

import com.karoldm.account_service.dto.*;
import com.karoldm.account_service.exception.AccountAlreadyExistsException;
import com.karoldm.account_service.exception.AccountNotFoundException;
import com.karoldm.account_service.feign.BacenService;
import com.karoldm.account_service.feign.dto.PixKeyRequestDTO;
import com.karoldm.account_service.feign.dto.PixKeyResponseDTO;
import com.karoldm.account_service.model.Account;
import com.karoldm.account_service.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final BacenService bacenService;

    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO) {
        Optional<Account> account = accountRepository.findByOwnerNameAndAccountNumberAndPixKey(
                accountRequestDTO.getOwnerName(),
                accountRequestDTO.getAccountNumber(),
                accountRequestDTO.getPixKey()
        );

        if(account.isPresent()){
            throw new AccountAlreadyExistsException("Account already exist");
        }

        Account newAccount = Account.builder()
                .ownerName(accountRequestDTO.getOwnerName())
                .accountNumber(accountRequestDTO.getAccountNumber())
                .pixKey(accountRequestDTO.getPixKey())
                .agencyNumber(accountRequestDTO.getAgencyNumber())
                .balance(BigDecimal.valueOf(5000.0))
                .build();

        Account savedAccount = accountRepository.save(newAccount);
        bacenService.createPixKey(savedAccount.getPixKey());

        AccountResponseDTO response = AccountResponseDTO.builder()
                        .id(savedAccount.getId())
                        .ownerName(savedAccount.getOwnerName())
                        .build();

        log.info("Account created: ");

        return response;
    }

    public List<AccountDTO> listAllAccounts() {
        return accountRepository.findAll().stream().map(
                account -> AccountDTO.builder()
                        .accountNumber(account.getAccountNumber())
                        .id(account.getId())
                        .agencyNumber(account.getAgencyNumber())
                        .balance(account.getBalance())
                        .ownerName(account.getOwnerName())
                        .pixKey(account.getPixKey())
                        .build()
        ).toList();
    }

    public AccountDTO getAccount(UUID id){
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException(String.format("Account with id %s doesn't exists", id))
        );

        return AccountDTO.builder()
                .accountNumber(account.getAccountNumber())
                .pixKey(account.getPixKey())
                .agencyNumber(account.getAgencyNumber())
                .balance(account.getBalance())
                .id(account.getId())
                .ownerName(account.getOwnerName())
                .build();
    }

    @Transactional
    public AccountResponseDTO updateAccount(AccountRequestDTO accountRequestDTO, UUID id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException(String.format("Account with id %s doesn't exists", id))
        );

        if(!Objects.equals(accountRequestDTO.getPixKey(), account.getPixKey())) {
            PixKeyRequestDTO pixKeyRequestDTO = new PixKeyRequestDTO(
                    accountRequestDTO.getPixKey(),
                    Boolean.TRUE
            );
            bacenService.updatePixKey(pixKeyRequestDTO, account.getPixKey());
        }

        account.setAccountNumber(accountRequestDTO.getAccountNumber());
        account.setAgencyNumber(accountRequestDTO.getAgencyNumber());
        account.setOwnerName(accountRequestDTO.getOwnerName());
        account.setPixKey(accountRequestDTO.getPixKey());

        Account updatedAccount = accountRepository.save(account);

        return AccountResponseDTO.builder()
                .id(updatedAccount.getId())
                .ownerName(updatedAccount.getOwnerName())
                .build();
    }

    @Transactional
    public void deleteAccount(UUID id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException(String.format("Account with id %s doesn't exists", id))
        );

        bacenService.deletePixKey(account.getPixKey());
        accountRepository.deleteById(id);
    }
}
