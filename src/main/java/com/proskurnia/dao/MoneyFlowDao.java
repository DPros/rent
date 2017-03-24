package com.proskurnia.dao;

import com.proskurnia.VOs.CreditPaymentVO;
import com.proskurnia.VOs.DebitPaymentVO;

import java.sql.SQLException;

/**
 * Created by dmpr0116 on 24.03.2017.
 */
public interface MoneyFlowDao {

    CreditPaymentVO createCreditPayment(CreditPaymentVO payment) throws SQLException;

    DebitPaymentVO createDebitPayment(DebitPaymentVO payment);
}
