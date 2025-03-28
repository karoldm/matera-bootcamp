package com.karoldm.account_service.service;

import com.karoldm.account_service.dto.PixDTO;
import com.karoldm.account_service.dto.PixRequestDTO;
import com.karoldm.account_service.dto.PixResponseDTO;
import com.karoldm.account_service.exception.AccountNotFoundException;
import com.karoldm.account_service.exception.InsufficientBalanceException;
import com.karoldm.account_service.feign.BacenService;
import com.karoldm.account_service.model.Account;
import com.karoldm.account_service.model.Pix;
import com.karoldm.account_service.repository.AccountRepository;
import com.karoldm.account_service.repository.PixRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PixService {
    private final PixRepository pixRepository;
    private final AccountRepository accountRepository;
    private final BacenService bacenService;

    @Transactional
    public PixResponseDTO sendPix(PixRequestDTO pixRequestDTO) {

        Optional<Pix> existingPix = pixRepository.findByIdempotent(pixRequestDTO.getIdempotent());

        if(existingPix.isPresent()){
            return PixResponseDTO
                    .builder()
                    .createdAt(existingPix.get().getCreatedAt())
                    .pix(entityToDTO(existingPix.get()))
                    .message("Pix already sent with successfully")
                    .build();
        }

        String payerKey = bacenService.getPixKey(pixRequestDTO.getPixKeyPayer()).keyValue();
        Optional<Account> payerAccountOptional = accountRepository.findByPixKey(payerKey);

        if(payerAccountOptional.isEmpty()){
            throw new AccountNotFoundException(String.format("Account with the key %s doesn't exists.", pixRequestDTO.getPixKeyReceiver()));
        }

        String receiverKey = bacenService.getPixKey(pixRequestDTO.getPixKeyReceiver()).keyValue();
        Optional<Account> receiverAccountOptional = accountRepository.findByPixKey(receiverKey);

        if(receiverAccountOptional.isEmpty()){
            throw new AccountNotFoundException(String.format("Account with the key %s doesn't exists.", pixRequestDTO.getPixKeyReceiver()));
        }

        Account payerAccount = payerAccountOptional.get();
        Account receiverAccount = receiverAccountOptional.get();

        // compareTo returns the values:
        // > 0 if pixRequestDTO.getValue() > payerAccount.getBalance()
        // == 0 if pixRequestDTO.getValue() == payerAccount.getBalance()
        // < 0 if pixRequestDTO.getValue() < payerAccount.getBalance()
        if(pixRequestDTO.getPixValue().compareTo(payerAccount.getBalance()) > 0){
            throw new InsufficientBalanceException(String.format("Insufficient balance. The pix value is %s and the account's balance is %s",
                    pixRequestDTO.getPixValue(), payerAccount.getBalance()));
        }

        payerAccount.withdraw(pixRequestDTO.getPixValue());
        receiverAccount.deposit(pixRequestDTO.getPixValue());

        accountRepository.save(payerAccount);
        accountRepository.save(receiverAccount);

        Pix pix = Pix.builder()
                .pixKeyPayer(pixRequestDTO.getPixKeyPayer())
                .pixKeyReceiver(pixRequestDTO.getPixKeyReceiver())
                .account(payerAccount)
                .pixValue(pixRequestDTO.getPixValue())
                .idempotent(pixRequestDTO.getIdempotent())
                .createdAt(LocalDateTime.now())
                .build();

        pixRepository.save(pix);

        return PixResponseDTO.builder()
                .message("Pix sent successfully")
                .pix(entityToDTO(pix))
                .createdAt(pix.getCreatedAt())
                .build();
    }

    private PixDTO entityToDTO(Pix pix){
        return PixDTO.builder()
                .id(pix.getId())
                .pixKeyPayer(pix.getPixKeyPayer())
                .pixKeyReceiver(pix.getPixKeyReceiver())
                .pixValue(pix.getPixValue())
                .createdAt(pix.getCreatedAt())
                .idempotent(pix.getIdempotent())
                .build();
    }
}
