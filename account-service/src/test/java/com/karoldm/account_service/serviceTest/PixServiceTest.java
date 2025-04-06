package com.karoldm.account_service.serviceTest;


import com.karoldm.account_service.dto.PixHistoryResponseDTO;
import com.karoldm.account_service.dto.PixRequestDTO;
import com.karoldm.account_service.dto.PixResponseDTO;
import com.karoldm.account_service.exception.AccountNotFoundException;
import com.karoldm.account_service.exception.InsufficientBalanceException;
import com.karoldm.account_service.feign.BacenService;
import com.karoldm.account_service.feign.dto.PixKeyResponseDTO;
import com.karoldm.account_service.model.Account;
import com.karoldm.account_service.model.Pix;
import com.karoldm.account_service.repository.AccountRepository;
import com.karoldm.account_service.repository.PixRepository;
import com.karoldm.account_service.service.PixService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PixServiceTest {
    @Mock
    private PixRepository pixRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private BacenService bacenService;

    @InjectMocks
    private PixService pixService;

    private Account payerAccount;
    private Account receiverAccount;

    @BeforeEach
    void setup(){
        payerAccount = Account.builder()
                .id(UUID.randomUUID())
                .pixKey("payer@pix.com")
                .accountNumber(1234)
                .agencyNumber(213)
                .balance(BigDecimal.valueOf(100.0))
                .ownerName("payer")
                .build();

        receiverAccount = Account.builder()
                .id(UUID.randomUUID())
                .pixKey("receiver@pix.com")
                .accountNumber(1235)
                .agencyNumber(213)
                .balance(BigDecimal.valueOf(0))
                .ownerName("receiver")
                .build();
    }

    @Test
    void mustSendPix(){
        PixRequestDTO pixRequestDTO = PixRequestDTO.builder()
                .pixValue(BigDecimal.valueOf(100.0))
                .pixKeyReceiver(receiverAccount.getPixKey())
                .pixKeyPayer(payerAccount.getPixKey())
                .idempotent("test")
                .build();

        when(pixRepository.findByIdempotent("test")).thenReturn(Optional.empty());

        when(bacenService.getPixKey(payerAccount.getPixKey()))
                .thenReturn(new PixKeyResponseDTO(
                        payerAccount.getPixKey(),
                        Boolean.TRUE
                ));

        when(bacenService.getPixKey(receiverAccount.getPixKey()))
                .thenReturn(new PixKeyResponseDTO(
                        receiverAccount.getPixKey(),
                        Boolean.TRUE
                ));

        when(accountRepository.findByPixKey(payerAccount.getPixKey()))
                .thenReturn(Optional.of(payerAccount));

        when(accountRepository.findByPixKey(receiverAccount.getPixKey()))
                .thenReturn(Optional.of(receiverAccount));


        PixResponseDTO pixResponseDTO = pixService.sendPix(pixRequestDTO);

        verify(accountRepository, times(1)).save(payerAccount);
        verify(accountRepository, times(1)).save(receiverAccount);
        verify(pixRepository, times(1)).save(any(Pix.class));

        assertEquals(BigDecimal.valueOf(0.0), payerAccount.getBalance());
        assertEquals(BigDecimal.valueOf(100.0), receiverAccount.getBalance());
        assertEquals("Pix sent successfully", pixResponseDTO.getMessage());
    }

    @Test
    void mustReturnSuccessMessageWhenIdempotentExist(){
        PixRequestDTO pixRequestDTO = PixRequestDTO.builder()
                .pixValue(BigDecimal.valueOf(100.0))
                .pixKeyReceiver(receiverAccount.getPixKey())
                .pixKeyPayer(payerAccount.getPixKey())
                .idempotent("test")
                .build();

        Pix pix = Pix.builder()
                .idempotent("test")
                .createdAt(LocalDateTime.now())
                .pixValue(pixRequestDTO.getPixValue())
                .pixKeyPayer(pixRequestDTO.getPixKeyPayer())
                .pixKeyReceiver(pixRequestDTO.getPixKeyReceiver())
                .id(UUID.randomUUID())
                .account(payerAccount)
                .build();

        when(pixRepository.findByIdempotent("test")).thenReturn(Optional.of(pix));

        PixResponseDTO pixResponseDTO = pixService.sendPix(pixRequestDTO);

        verify(accountRepository, times(0)).save(payerAccount);
        verify(accountRepository, times(0)).save(receiverAccount);
        verify(pixRepository, times(0)).save(any(Pix.class));

        assertEquals("Pix already sent with successfully", pixResponseDTO.getMessage());
        assertEquals(pix.getIdempotent(), pixResponseDTO.getPix().getIdempotent());
        assertEquals(BigDecimal.valueOf(100.0), payerAccount.getBalance());
        assertEquals(BigDecimal.ZERO, receiverAccount.getBalance());
    }

    @Test
    void mustThrowInsufficientBalanceException(){
        payerAccount.setBalance(BigDecimal.ZERO);

        PixRequestDTO pixRequestDTO = PixRequestDTO.builder()
                .pixValue(BigDecimal.valueOf(100.0))
                .pixKeyReceiver(receiverAccount.getPixKey())
                .pixKeyPayer(payerAccount.getPixKey())
                .idempotent("test")
                .build();

        when(pixRepository.findByIdempotent("test")).thenReturn(Optional.empty());

        when(bacenService.getPixKey(payerAccount.getPixKey()))
                .thenReturn(new PixKeyResponseDTO(
                        payerAccount.getPixKey(),
                        Boolean.TRUE
                ));

        when(bacenService.getPixKey(receiverAccount.getPixKey()))
                .thenReturn(new PixKeyResponseDTO(
                        receiverAccount.getPixKey(),
                        Boolean.TRUE
                ));

        when(accountRepository.findByPixKey(payerAccount.getPixKey()))
                .thenReturn(Optional.of(payerAccount));

        when(accountRepository.findByPixKey(receiverAccount.getPixKey()))
                .thenReturn(Optional.of(receiverAccount));


        assertThrows(
                InsufficientBalanceException.class,
                () -> pixService.sendPix(pixRequestDTO)
        );

        verify(accountRepository, times(0)).save(payerAccount);
        verify(accountRepository, times(0)).save(receiverAccount);
        verify(pixRepository, times(0)).save(any(Pix.class));

        assertEquals(BigDecimal.ZERO, payerAccount.getBalance());
        assertEquals(BigDecimal.ZERO, receiverAccount.getBalance());
    }

    @Test
    void mustThrowAccountNotFoundException(){
        PixRequestDTO pixRequestDTO = PixRequestDTO.builder()
                .pixValue(BigDecimal.valueOf(100.0))
                .pixKeyReceiver(receiverAccount.getPixKey())
                .pixKeyPayer(payerAccount.getPixKey())
                .idempotent("test")
                .build();

        when(pixRepository.findByIdempotent("test")).thenReturn(Optional.empty());

        when(bacenService.getPixKey(payerAccount.getPixKey()))
                .thenReturn(new PixKeyResponseDTO(
                        payerAccount.getPixKey(),
                        Boolean.TRUE
                ));

        assertThrows(
                AccountNotFoundException.class,
                () -> pixService.sendPix(pixRequestDTO)
        );

        verify(accountRepository, times(0)).save(payerAccount);
        verify(accountRepository, times(0)).save(receiverAccount);
        verify(pixRepository, times(0)).save(any(Pix.class));

        assertEquals(BigDecimal.valueOf(100.0), payerAccount.getBalance());
        assertEquals(BigDecimal.ZERO, receiverAccount.getBalance());
    }

    @Test
    void mustGetPixHistoryOfUser(){
        UUID userId = UUID.randomUUID();

        payerAccount.addPixHistoric(Pix.builder()
                .account(payerAccount)
                .id(UUID.randomUUID())
                .pixKeyReceiver(receiverAccount.getPixKey())
                .pixKeyPayer(payerAccount.getPixKey())
                .pixValue(BigDecimal.valueOf(100.0))
                .createdAt(LocalDateTime.now())
                .idempotent("pix-1")
                .build());

        when(accountRepository.findById(userId)).thenReturn(Optional.of(payerAccount));

        PixHistoryResponseDTO pixHistoryResponseDTO = pixService.getPixHistory(userId);

        verify(accountRepository, times(1)).findById(userId);

        assertEquals(1, pixHistoryResponseDTO.getTotalPix());
        assertEquals(payerAccount.getPixHistory().get(0).getId(), pixHistoryResponseDTO.getPixHistory().get(0).getId());
    }

    @Test
    void mustThrowsNotFoundWhenGetPixHistory(){
        UUID id = UUID.randomUUID();

        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> pixService.getPixHistory(id)
        );
    }
}
