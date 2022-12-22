package com.daria.realestate.dao;

import com.daria.realestate.domain.enums.PaymentTransactionType;

import java.util.List;

public interface EstateDAO<Estate> extends GenericDAO<Estate>{
    List<com.daria.realestate.domain.Estate> getAllEstatesFilteredByPaymentTransactionType(PaymentTransactionType paymentTransactionType);
}
