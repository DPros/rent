package com.proskurnia.dao;

import com.proskurnia.VOs.CreditPaymentVO;
import com.proskurnia.VOs.DebitPaymentVO;
import com.proskurnia.VOs.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    private final static String REPORT_BY_ACCOUNT = "" +
            "SELECT p.id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,service_company_types.type_name AS description " +
            "FROM debit_payments p " +
            "JOIN service_contracts c ON c.contract_id=p.reason_id " +
            "JOIN buildings ON c.building_id=buildings.building_id " +
            "JOIN service_companies ON c.company_id=service_companies.company_id " +
            "JOIN service_company_types types ON types.type_id=service_companies.type " +
            "WHERE p.account_number=? AND type=0 " +
            "UNION " +
            "SELECT p.id,p.date,p.amount,p.comment,p.account_number,p,type,buildings.address,'' description " +
            "FROM debit_payments p " +
            "JOIN buildings ON buildings.building_id=p.reason_id " +
            "WHERE p.account_number=? AND type=1 " +
            "UNION " +
            "SELECT p.id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,apartments.number AS description " +
            "FROM debit_payments p JOIN apartments ON p.reason_id=apartments.apartment_id " +
            "JOIN buildings ON building.building_id=apartments.building_id " +
            "WHERE p.account_number=? AND type=2 " +
            "UNION " +
            "SELECT p.id,p.date,p.amount,p.comment,p.account_number,100 AS type,building.address, '' AS description " +
            "FROM credit_payments NATURAL JOIN apartments NATURAL JOIN buildings" +
            "WHERE p.account_number=? AND confirmed=true " +
            "ORDER BY date;";

    @Transactional
    public CreditPaymentVO createCreditPayment(CreditPaymentVO payment) throws SQLException {
        Connection con = null;
        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            if (payment.isConfirmed()) {
                if (payment.isDeposit()) {
                    PreparedStatement updateTenantDeposit = con.prepareStatement(UPDATE_RENTING_CONTRACT_DEPOSIT);
                    updateTenantDeposit.setBigDecimal(1, payment.getAmount().negate());
                    updateTenantDeposit.setInt(2, payment.getContractId());
                    updateTenantDeposit.execute();
                }
                PreparedStatement updateOwnerBalance = con.prepareStatement(UPDATE_OWNER_ACCOUNT_BY_RENTING_CONTRACT_ID);
                updateOwnerBalance.setBigDecimal(1, payment.getAmount());
                updateOwnerBalance.setInt(2, payment.getContractId());
                updateOwnerBalance.execute();
            } else {
                PreparedStatement updateTenantBalance = con.prepareStatement(UPDATE_RENTING_CONTRACT_BALANCE);
                updateTenantBalance.setBigDecimal(1, payment.getAmount().negate());
                updateTenantBalance.setInt(2, payment.getContractId());
                updateTenantBalance.execute();
            }
            PreparedStatement createPayment = con.prepareStatement(INSERT_CREDIT);
            int index = 0;
            createPayment.setTimestamp(++index, payment.getDate());
            createPayment.setBigDecimal(++index, payment.getAmount());
            createPayment.setString(++index, payment.getComment());
            createPayment.setBoolean(++index, payment.isDeposit());
            createPayment.setBoolean(++index, payment.isConfirmed());
            createPayment.setInt(++index, payment.getContractId());
            createPayment.setInt(++index, payment.getContractId());
            createPayment.setString(++index, payment.getAccountNumber());
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
            PreparedStatement updateOwnerBalance = con.prepareStatement(payment.getType() == DebitPaymentVO.PaymentType.ServicePayment ? UPDATE_OWNER_ACCOUNT_BY_SERVICE_CONTRACT_ID : payment.getType() == DebitPaymentVO.PaymentType.ApartmentPayment ? UPDATE_OWNER_ACCOUNT_BY_APARTMENT_ID : UPDATE_OWNER_ACCOUNT_BY_BUILDING_ID);
            updateOwnerBalance.setBigDecimal(1, payment.getAmount().negate());
            updateOwnerBalance.setInt(2, payment.getReasonId());
            updateOwnerBalance.execute();
            PreparedStatement createPayment = con.prepareStatement(INSERT_DEBIT);
            int index = 0;
            createPayment.setTimestamp(++index, payment.getDate());
            createPayment.setBigDecimal(++index, payment.getAmount().negate());
            createPayment.setString(++index, payment.getComment());
            createPayment.setInt(++index, payment.getType().getVal());
            createPayment.setInt(++index, payment.getReasonId());
            createPayment.setInt(++index, payment.getType().getVal());
            createPayment.setInt(++index, payment.getReasonId());
            createPayment.setInt(++index, payment.getReasonId());
            createPayment.setInt(++index, payment.getReasonId());
            createPayment.setString(++index, payment.getAccountNumber());
            payment.setId(createPayment.executeQuery().getLong(1));
            return payment;
        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) con.close();
        }
    }

    public RowMapper<Payment> getRowMapper() {
        return (rs, rowNum) -> rs.getInt("type") > 10 ?
                new CreditPaymentVO(
                        rs.getLong("payment_id"),
                        rs.getTimestamp("date"),
                        rs.getBigDecimal("amount"),
                        rs.getString("comment"),
                        false,
                        true,
                        rs.getInt("reason_id"),
                        rs.getString("account_number"),
                        rs.getString("address")
                ) :
                new DebitPaymentVO(
                        rs.getLong("payment_id"),
                        rs.getTimestamp("date"),
                        rs.getBigDecimal("amount"),
                        rs.getString("comment"),
                        Payment.PaymentType.valueOf(rs.getInt("type")),
                        rs.getInt("reason_id"),
                        rs.getString("description"),
                        rs.getString("account_number"),
                        rs.getString("address")
                );
    }
}
