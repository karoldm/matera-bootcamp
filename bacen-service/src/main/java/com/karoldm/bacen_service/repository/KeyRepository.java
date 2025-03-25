package com.karoldm.bacen_service.repository;

import com.karoldm.bacen_service.model.PixKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface KeyRepository extends JpaRepository<PixKey, UUID> {
    boolean existsByKeyValue(final String key);
    Optional<PixKey> findByKeyValue(final String keyValue);
}