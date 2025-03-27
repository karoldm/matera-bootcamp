package com.karoldm.account_service.model;

import com.karoldm.account_service.exception.InsufficientBalanceException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

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
    private Double balance;

    public void deposit(Double value) {
        this.balance += value;
    }

    public void withdraw(Double value) {
        if(value < 0){

        }

        if(this.balance < value) {
            throw new InsufficientBalanceException(String.format(
                    "Insufficient balance. Your balance is: %s, and you are trying withdraw %s",
                    this.balance,
                    value
            ));
        }
        this.balance -= value;
    }
}
