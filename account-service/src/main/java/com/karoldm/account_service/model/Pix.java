package com.karoldm.account_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Entity
@Builder
public class Pix {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator(style=UuidGenerator.Style.TIME)
    private UUID id;

    @Column
    private LocalDateTime createdAt;

    @Column
    private String pixKeyPayer;

    @Column
    private String pixKeyReceiver;

    @Column
    private BigDecimal pixValue;

    @Column(unique = true)
    private String idempotent;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
}
