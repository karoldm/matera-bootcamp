package com.karoldm.account_service.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karoldm.account_service.controller.AccountController;
import com.karoldm.account_service.dto.AccountDTO;
import com.karoldm.account_service.dto.AccountRequestDTO;
import com.karoldm.account_service.dto.AccountResponseDTO;
import com.karoldm.account_service.exception.AccountAlreadyExistsException;
import com.karoldm.account_service.exception.AccountNotFoundException;
import com.karoldm.account_service.service.AccountService;
import com.karoldm.account_service.service.PixService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AccountService accountService;

    @MockitoBean
    private PixService pixService;

    @Test
    void mustCreateAccount() throws Exception {
        AccountRequestDTO request = AccountRequestDTO.builder()
                .ownerName("Paula")
                .agencyNumber(123)
                .accountNumber(15)
                .pixKey("paula@pix.com")
                .build();

        UUID createdId = UUID.randomUUID();

        AccountResponseDTO response = AccountResponseDTO.builder()
                .id(createdId)
                .ownerName(request.getOwnerName())
                .build();

        when(accountService.createAccount(request)).thenReturn(response);

        mockMvc.perform(post("/api/accounts")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("ownerName").value("Paula"))
                .andExpect(status().isCreated());

    }

    @Test
    void mustThrowsAccountAlreadyExistException() throws Exception {
        AccountRequestDTO request = AccountRequestDTO.builder()
                .ownerName("Samuel")
                .agencyNumber(10)
                .accountNumber(20)
                .pixKey("cliente@pix.com")
                .build();

        when(accountService.createAccount(request))
                .thenThrow(new AccountAlreadyExistsException("Account already exist"));

        mockMvc.perform(post("/api/accounts")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
        ;
    }

    @Test
    void mustGetAccountById() throws Exception {
        UUID id = UUID.randomUUID();

        AccountDTO accountDTO = AccountDTO.builder()
                .pixKey("account@pix.com")
                .id(id)
                .balance(BigDecimal.valueOf(100.0))
                .accountNumber(123)
                .agencyNumber(100)
                .ownerName("account test")
                .build();

        when(accountService.getAccount(id)).thenReturn(accountDTO);

        mockMvc.perform(get("/api/accounts/"+id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("pixKey").value(accountDTO.getPixKey()))
                .andExpect(jsonPath("id").value(accountDTO.getId().toString()))
                .andExpect(jsonPath("balance").value(accountDTO.getBalance()))
                .andExpect(jsonPath("accountNumber").value(accountDTO.getAccountNumber()))
                .andExpect(jsonPath("agencyNumber").value(accountDTO.getAgencyNumber()))
                .andExpect(jsonPath("ownerName").value(accountDTO.getOwnerName()));
    }

    @Test
    void mustThrowsNotFoundWhenGet() throws Exception {
        UUID id = UUID.randomUUID();

        when(accountService.getAccount(id))
                .thenThrow(new AccountNotFoundException("Account not found"));

        mockMvc.perform(get("/api/accounts/"+id))
                .andExpect(status().isNotFound());
    }

    @Test
    void mustUpdateAccount() throws Exception {
        AccountRequestDTO request = AccountRequestDTO.builder()
                .ownerName("Paula")
                .agencyNumber(123)
                .accountNumber(15)
                .pixKey("paula@pix.com")
                .build();

        UUID accountId = UUID.randomUUID();

        AccountResponseDTO response = AccountResponseDTO.builder()
                .id(accountId)
                .ownerName(request.getOwnerName())
                .build();

        when(accountService.updateAccount(request, accountId)).thenReturn(response);

        mockMvc.perform(put("/api/accounts/"+accountId)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(accountId.toString()))
                .andExpect(jsonPath("ownerName").value("Paula"))
                .andExpect(status().isOk());
    }

    @Test
    void mustThrowsNotFoundWhenUpdate() throws Exception {
        UUID accountId = UUID.randomUUID();

        AccountRequestDTO request = AccountRequestDTO.builder()
                .ownerName("Paula")
                .agencyNumber(123)
                .accountNumber(15)
                .pixKey("paula@pix.com")
                .build();


        when(accountService.updateAccount(request, accountId))
                .thenThrow(new AccountNotFoundException("Account not found"));

        mockMvc.perform(put("/api/accounts/"+accountId)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void mustDeleteAccount() throws Exception {
        UUID accountId = UUID.randomUUID();

        mockMvc.perform(delete("/api/accounts/"+accountId))
                .andExpect(status().isNoContent());
    }

    @Test
    void mustThrowsNotFoundWhenDeleteAccount() throws Exception {
        UUID accountId = UUID.randomUUID();

        doThrow(new AccountNotFoundException("Account not found"))
                .when(accountService)
                .deleteAccount(accountId);

        mockMvc.perform(delete("/api/accounts/"+accountId))
                .andExpect(status().isNotFound());
    }
}


