package com.khusanjon.springstatemachinesample.domain.wallet;

import com.khusanjon.springstatemachinesample.domain.currency.Currency;
import com.khusanjon.springstatemachinesample.domain.personal.Personal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal accountAmount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne
    private Personal personal;
}
