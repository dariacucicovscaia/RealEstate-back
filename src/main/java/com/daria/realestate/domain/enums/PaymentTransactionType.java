package com.daria.realestate.domain.enums;
public enum PaymentTransactionType {
    /**
     * The buyer pays the whole price right away
     */
    SALE,
    /**
     * The buyer wants to rent the place for a short period of time ~ 30 days
     */
    RENT,
    /**
     *  The buyer wants to rent the place for a longer period of time ~ 12 months
     */
    LEASE
}
