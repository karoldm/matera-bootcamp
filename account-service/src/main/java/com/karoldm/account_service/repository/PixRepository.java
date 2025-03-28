package com.karoldm.account_service.repository;

import com.karoldm.account_service.model.Pix;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PixRepository extends JpaRepository<Pix, UUID> {
    Optional<Pix> findByIdempotent(String idempotent);
}
