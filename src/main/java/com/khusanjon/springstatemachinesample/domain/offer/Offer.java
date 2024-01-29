package com.khusanjon.springstatemachinesample.domain.offer;

import com.khusanjon.springstatemachinesample.domain.currency.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OfferState offerState;

    private UUID offerIdentifier;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private Long buyerId;

    private Long sellerId;
}
