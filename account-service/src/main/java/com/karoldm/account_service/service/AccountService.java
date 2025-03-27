package com.karoldm.account_service.service;

import com.karoldm.account_service.dto.AccountDTO;
import com.karoldm.account_service.dto.AccountRequestDTO;
import com.karoldm.account_service.dto.AccountResponseDTO;
import com.karoldm.account_service.exception.AccountAlreadyExistsException;
import com.karoldm.account_service.exception.AccountNotFoundException;
import com.karoldm.account_service.model.Account;
import com.karoldm.account_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;

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
                .balance(5000.0)
                .build();

        Account savedAccount = accountRepository.save(newAccount);

        AccountResponseDTO response = AccountResponseDTO.builder()
                        .id(savedAccount.getId())
                        .ownerName(savedAccount.getOwnerName())
                        .build();

        log.info("Account created: ");

        return response;
    }

    public List<AccountDTO> listAllAccounts() {
        List<AccountDTO> accounts = accountRepository.findAll().stream().map(
                account -> AccountDTO.builder()
                        .accountNumber(account.getAccountNumber())
                        .id(account.getId())
                        .agencyNumber(account.getAgencyNumber())
                        .balance(account.getBalance())
                        .ownerName(account.getOwnerName())
                        .pixKey(account.getPixKey())
                        .build()
        ).toList();

        return accounts;
    }

    public AccountDTO getAccount(UUID id){
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException(String.format("Account with id %s doesn't exists", id))
        );

        AccountDTO response = AccountDTO.builder()
                .accountNumber(account.getAccountNumber())
                .pixKey(account.getPixKey())
                .agencyNumber(account.getAgencyNumber())
                .balance(account.getBalance())
                .id(account.getId())
                .ownerName(account.getOwnerName())
                .build();

        return response;
    }

    public AccountResponseDTO updateAccount(AccountRequestDTO accountRequestDTO, UUID id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException(String.format("Account with id %s doesn't exists", id))
        );

        account.setAccountNumber(accountRequestDTO.getAccountNumber());
        account.setAgencyNumber(accountRequestDTO.getAgencyNumber());
        account.setOwnerName(accountRequestDTO.getOwnerName());
        account.setPixKey(accountRequestDTO.getPixKey());

        account = accountRepository.save(account);

        return AccountResponseDTO.builder()
                .id(account.getId())
                .ownerName(account.getOwnerName())
                .build();
    }

    public void deleteAccount(UUID id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new AccountNotFoundException(String.format("Account with id %s doesn't exists", id))
        );

        accountRepository.deleteById(id);
    }
}
