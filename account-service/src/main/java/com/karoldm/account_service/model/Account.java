package com.karoldm.account_service.model;

import com.karoldm.account_service.exception.InsufficientBalanceException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name="account")
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator(style=UuidGenerator.Style.TIME)
    private UUID id;

    @Column
    private String ownerName;

    @Column
    private Integer agencyNumber;

    @Column
    private Integer accountNumber;

    @Column
    private String pixKey;

    @Column
    private BigDecimal balance;

    @OneToMany(mappedBy="account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Pix> pixHistoric;

    public void deposit(BigDecimal value) {
        this.balance = this.balance.add(value);
    }

    public void withdraw(BigDecimal value) {
        this.balance = this.balance.subtract(value);
    }
}
