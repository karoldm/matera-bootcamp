package com.karoldm.account_service.repository;

import com.karoldm.account_service.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByPixKey(String pixKeyPayer);
    Optional<Account> findByOwnerNameAndAccountNumberAndPixKey(final String ownerName, final Integer accountNumber, final String pixKey);
}
