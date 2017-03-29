package com.proskurnia.dao;

import com.proskurnia.VOs.CreditPaymentVO;
import com.proskurnia.VOs.DebitPaymentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by dmpr0116 on 24.03.2017.
 */
@Repository
public class MoneyFlowJdbcUtils {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    final static String INSERT_CREDIT = "INSERT INTO credit_payments (date,amount,comment,deposit,confirmed,contract_id,account_number) " +
            "VALUES(?,?,?,?,?,?,(SELECT account_number FROM buildings WHERE building_id IN " +
            "(SELECT building_id FROM apartments WHERE apartment_id IN " +
            "(SELECT apartment_id FROM renting_contracts WHERE contract_id=?)))) RETURNING payment_id;";

    final static String INSERT_DEBIT = "INSERT INTO debit_payments (date,amount,comment,type,reason_id,account_number) " +
            "VALUES(?,?,?,?,?,(CASE ? WHEN 0 THEN (SELECT account_number FROM buildings " +
            "WHERE building_id IN (SELECT building_id FROM service_accounts WHERE accound_id=?)) " +
            "WHEN 1 THEN (SELECT account_number FROM buildings WHERE building_id=?) " +
            "WHEN 2 THEN (SELECT account_number FROM buildings WHERE building_id IN " +
            "(SELECT building_id FROM apartments WHERE apartment_id=?))END));";

    private final static String UPDATE_RENTING_CONTRACT_BALANCE = "UPDATE renting_contracts SET balance=?+balance WHERE contract_id=?;";
    private final static String UPDATE_RENTING_CONTRACT_DEPOSIT = "UPDATE renting_contracts SET deposit=?+balance WHERE contract_id=?;";

    private final static String UPDATE_OWNER_ACCOUNT_BY_BUILDING_ID = "UPDATE accounts SET balance=?+balance WHERE account_number IN" +
            "(SELECT account_number FROM buildings WHERE building_id=?);";

    private final static String UPDATE_OWNER_ACCOUNT_BY_APARTMENT_ID = "UPDATE accounts SET balance=?+balance WHERE account_number IN" +
            "(SELECT account_number FROM buildings WHERE building_id IN " +
            "(SELECT building_id FROM apartments WHERE apartment_id=?));";

    private final static String UPDATE_OWNER_ACCOUNT_BY_SERVICE_CONTRACT_ID = "UPDATE accounts SET balance=?+balance WHERE account_number IN" +
            "(SELECT account_number FROM buildings WHERE building_id IN " +
            "(SELECT building_id FROM service_contracts WHERE contract_id=?));";

    private final static String UPDATE_OWNER_ACCOUNT_BY_RENTING_CONTRACT_ID = "UPDATE accounts SET balance=?+balance WHERE account_number IN" +
            "(SELECT account_number FROM buildings WHERE building_id IN " +
            "(SELECT building_id FROM apartments WHERE apartment_id IN " +
            "(SELECT apartment_id FROM renting_contracts WHERE contract_id=?)));";

    @Transactional
    public CreditPaymentVO createCreditPayment(CreditPaymentVO payment) throws SQLException {
        Connection con = null;
        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            PreparedStatement updateTenantBalance = payment.isDeposit() ? con.prepareStatement(UPDATE_RENTING_CONTRACT_DEPOSIT) : con.prepareStatement(UPDATE_RENTING_CONTRACT_BALANCE);
            updateTenantBalance.setBigDecimal(1, payment.isDeposit() || !payment.isConfirmed() ? payment.getAmount().negate() : BigDecimal.ZERO);
            updateTenantBalance.setInt(2, payment.getContractId());
            updateTenantBalance.execute();
            PreparedStatement updateOwnerBalance = con.prepareStatement(UPDATE_OWNER_ACCOUNT_BY_RENTING_CONTRACT_ID);
            updateOwnerBalance.setBigDecimal(1, payment.getAmount());
            updateOwnerBalance.setInt(2, payment.getContractId());
            updateOwnerBalance.execute();
            PreparedStatement createPayment = con.prepareStatement(INSERT_CREDIT);
            int index = 0;
            createPayment.setTimestamp(++index, payment.getDate());
            createPayment.setBigDecimal(++index, payment.getAmount());
            createPayment.setString(++index, payment.getComment());
            createPayment.setBoolean(++index, payment.isDeposit());
            createPayment.setBoolean(++index, payment.isConfirmed());
            createPayment.setInt(++index, payment.getContractId());
            createPayment.setInt(++index, payment.getContractId());
            payment.setId(createPayment.executeQuery().getLong(1));
            con.commit();
            return payment;
        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) con.close();
        }
    }

    public DebitPaymentVO createDebitPayment(DebitPaymentVO payment) throws SQLException {
        Connection con = null;
        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            PreparedStatement updateOwnerBalance = con.prepareStatement(payment.getType() == DebitPaymentVO.DebitPaymentType.ServicePayment ? UPDATE_OWNER_ACCOUNT_BY_SERVICE_CONTRACT_ID : payment.getType() == DebitPaymentVO.DebitPaymentType.ApartmentPayment ? UPDATE_OWNER_ACCOUNT_BY_APARTMENT_ID : UPDATE_OWNER_ACCOUNT_BY_BUILDING_ID);
            updateOwnerBalance.setBigDecimal(1, payment.getAmount().negate());
            updateOwnerBalance.setInt(2, payment.getReasonId());
            updateOwnerBalance.execute();
            PreparedStatement createPayment = con.prepareStatement(INSERT_DEBIT);
            int index = 0;
            createPayment.setTimestamp(++index, payment.getDate());
            createPayment.setBigDecimal(++index, payment.getAmount());
            createPayment.setString(++index, payment.getComment());
            createPayment.setInt(++index, payment.getType().getVal());
            createPayment.setInt(++index, payment.getReasonId());
            createPayment.setInt(++index, payment.getType().getVal());
            createPayment.setInt(++index, payment.getReasonId());
            createPayment.setInt(++index, payment.getReasonId());
            createPayment.setInt(++index, payment.getReasonId());
            payment.setId(createPayment.executeQuery().getLong(1));
            return null;
        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) con.close();
        }
    }
}
