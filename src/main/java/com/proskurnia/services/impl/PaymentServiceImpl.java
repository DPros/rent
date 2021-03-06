package com.proskurnia.services.impl;

import com.proskurnia.VOs.CreditPaymentVO;
import com.proskurnia.VOs.DebitPaymentVO;
import com.proskurnia.VOs.Payment;
import com.proskurnia.dao.jdbc.MoneyFlowJdbcUtils;
import com.proskurnia.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by D on 01.04.2017.
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    MoneyFlowJdbcUtils moneyFlowJdbcUtils;

    @Override
    public Collection<Payment> getAll() {
        return moneyFlowJdbcUtils.getAll();
    }

    @Override
    public CreditPaymentVO create(CreditPaymentVO o) throws SQLException {
        return moneyFlowJdbcUtils.create(o);
    }

    @Override
    public DebitPaymentVO create(DebitPaymentVO o) throws SQLException {
        return moneyFlowJdbcUtils.create(o);
    }

    @Override
    public void delete(long id, boolean credit) throws SQLException {
        moneyFlowJdbcUtils.delete(id, credit);
    }
}