package com.proskurnia.services;

import com.proskurnia.VOs.CreditPaymentVO;
import com.proskurnia.VOs.DebitPaymentVO;
import com.proskurnia.VOs.Payment;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by D on 01.04.2017.
 */
public interface PaymentService {

    Collection<Payment> getAll();

//    T getById(I id);

    CreditPaymentVO create(CreditPaymentVO o) throws SQLException;
    DebitPaymentVO create(DebitPaymentVO o) throws SQLException;

    void delete(long id, boolean credit) throws SQLException;

//    void update(T o) throws SQLException;

//    void delete(I id) throws SQLException;
}
