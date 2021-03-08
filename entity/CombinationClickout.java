package com.organization.backend.domain.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class CombinationClickout extends Clickout {

    @ManyToOne
    @JoinColumn(name = "combination_id", foreignKey = @ForeignKey(name = "combination_clickout__combination_id"))
    Combination combination;

    public CombinationClickout(final Combination combination,
                               final String cookieHash,
                               final Price price,
                               final String transactionAffiliation) {
        this.combination = combination;
        setCookieHash(cookieHash);
        setPrice(price);
        setTransactionAffiliation(transactionAffiliation);
    }
}
