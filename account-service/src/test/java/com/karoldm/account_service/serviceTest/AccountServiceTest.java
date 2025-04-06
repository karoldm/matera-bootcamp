package com.karoldm.account_service.serviceTest;

import com.karoldm.account_service.dto.AccountDTO;
import com.karoldm.account_service.dto.AccountRequestDTO;
import com.karoldm.account_service.dto.AccountResponseDTO;
import com.karoldm.account_service.exception.AccountAlreadyExistsException;
import com.karoldm.account_service.exception.AccountNotFoundException;
import com.karoldm.account_service.feign.BacenService;
import com.karoldm.account_service.feign.dto.PixKeyRequestDTO;
import com.karoldm.account_service.model.Account;
import com.karoldm.account_service.repository.AccountRepository;
import com.karoldm.account_service.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private BacenService bacenService;

    private Account account;

    @BeforeEach
    void setup(){
        account = Account.builder()
                .id(UUID.randomUUID())
                .balance(BigDecimal.valueOf(0.0))
                .ownerName("Maria")
                .agencyNumber(123)
                .accountNumber(12345)
                .pixKey("maria@pix.com")
                .pixHistory(List.of())
                .build();
    }

    @Test
    void mustCreateAccount(){
        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .accountNumber(account.getAccountNumber())
                .agencyNumber(account.getAgencyNumber())
                .ownerName(account.getOwnerName())
                .pixKey(account.getPixKey())
                .build();

        when(accountRepository.findByOwnerNameAndAccountNumberAndPixKey(
                accountRequestDTO.getOwnerName(),
                accountRequestDTO.getAccountNumber(),
                accountRequestDTO.getPixKey()
        )).thenReturn(Optional.empty());

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountResponseDTO accountResponseDTO = accountService.createAccount(accountRequestDTO);

        verify(accountRepository, times(1)).save(any(Account.class));
        verify(bacenService, times(1)).createPixKey(account.getPixKey());

        assertEquals(accountResponseDTO.getOwnerName(), account.getOwnerName());
    }

    @Test
    void mustThrowAccountAlreadyExistsException(){
        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .accountNumber(account.getAccountNumber())
                .agencyNumber(account.getAgencyNumber())
                .ownerName(account.getOwnerName())
                .pixKey(account.getPixKey())
                .build();

        when(accountRepository.findByOwnerNameAndAccountNumberAndPixKey(
                accountRequestDTO.getOwnerName(),
                accountRequestDTO.getAccountNumber(),
                accountRequestDTO.getPixKey()
        )).thenReturn(Optional.of(account));

        assertThrows(
                AccountAlreadyExistsException.class,
                ()-> accountService.createAccount(accountRequestDTO)
        );

        verify(accountRepository, times(0)).save(account);
        verify(bacenService, times(0)).createPixKey(account.getPixKey());
    }

    @Test
    void mustGetAccountById(){
        when(accountRepository.findById(any(UUID.class))).thenReturn(Optional.of(account));

        AccountDTO accountDTO = accountService.getAccount(UUID.randomUUID());

        verify(accountRepository, times(1)).findById(any(UUID.class));

        assertEquals(accountDTO.getOwnerName(), account.getOwnerName());
        assertEquals(accountDTO.getAccountNumber(), account.getAccountNumber());
        assertEquals(accountDTO.getAgencyNumber(), account.getAgencyNumber());
        assertEquals(accountDTO.getPixKey(), account.getPixKey());
    }

    @Test
    void mustThrowAccountNotFoundById(){
        when(accountRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> accountService.getAccount(UUID.randomUUID())
        );

        verify(accountRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void mustUpdateAccount(){
        Account updatedAccount = Account.builder()
                .id(account.getId())
                .accountNumber(1000)
                .agencyNumber(100)
                .balance(account.getBalance())
                .pixKey("updated@pix.com")
                .ownerName("Maria updated")
                .build();

        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .accountNumber(1000)
                .agencyNumber(100)
                .ownerName("Maria updated")
                .pixKey("updated@pix.com")
                .build();

        UUID id = account.getId();

        when(accountRepository.findById(id)).thenReturn(Optional.of(account));

        when(accountRepository.save(account)).thenReturn(updatedAccount);

        AccountResponseDTO accountResponseDTO = accountService.updateAccount(accountRequestDTO, id);

        verify(accountRepository, times(1)).findById(id);
        verify(accountRepository, times(1)).save(account);
        verify(bacenService, times(1)).updatePixKey(
                new PixKeyRequestDTO(
                        accountRequestDTO.getPixKey(),
                        Boolean.TRUE
                ),
                "maria@pix.com"
        );

        assertEquals(account.getOwnerName(), accountResponseDTO.getOwnerName());
        assertEquals(accountRequestDTO.getOwnerName(), accountResponseDTO.getOwnerName());
        assertEquals(account.getPixKey(), accountRequestDTO.getPixKey());
    }

    @Test
    void mustThrowsAccountNotFoundOnUpdate() {
        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .accountNumber(1000)
                .agencyNumber(100)
                .ownerName("Maria updated")
                .pixKey("updated@pix.com")
                .build();

        UUID id = account.getId();

        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> accountService.updateAccount(accountRequestDTO, id)
        );
    }

    @Test
    void mustDeleteAccount() {
        UUID id = UUID.randomUUID();

        when(accountRepository.findById(id)).thenReturn(Optional.of(account));

        accountService.deleteAccount(id);

        verify(bacenService, times(1)).deletePixKey(account.getPixKey());
        verify(accountRepository, times(1)).deleteById(id);
    }
}
