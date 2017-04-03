package com.proskurnia.dao;

import com.proskurnia.VOs.CreditPaymentVO;
import com.proskurnia.VOs.DebitPaymentVO;
import com.proskurnia.VOs.Payment;
import com.proskurnia.VOs.RentingContractVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
            "(SELECT apartment_id FROM renting_contracts WHERE contract_id=?)))) RETURNING payment_id,account_number;";

    final static String INSERT_DEBIT = "INSERT INTO debit_payments (date,amount,comment,type,reason_id,account_number) " +
            "VALUES(?,?,?,?,?,(CASE ? WHEN 0 THEN (SELECT account_number FROM buildings " +
            "WHERE building_id IN (SELECT building_id FROM service_contracts WHERE contract_id=?)) " +
            "WHEN 1 THEN (SELECT account_number FROM buildings WHERE building_id=?) " +
            "WHEN 2 THEN (SELECT account_number FROM buildings WHERE building_id IN " +
            "(SELECT building_id FROM apartments WHERE apartment_id=?)) " +
            "WHEN 3 THEN (SELECT account_number FROM buildings WHERE building_id IN " +
            "(SELECT building_id FROM apartments WHERE apartment_id IN " +
            "(SELECT apartment_id FROM renting_contracts WHERE contract_id=?)))END)) RETURNING payment_id,account_number;";

    private final static String INSERT_RENTING_CONTRACT = "INSERT INTO renting_contracts(rent_price,estimated_fees,start_date,expected_end_date,tenant_id,apartment_id) VALUES(?,?,?,?,?,?) RETURNING contract_id;";

    private final static String UPDATE_RENTING_CONTRACT_BALANCE = "UPDATE renting_contracts SET balance=?+balance WHERE contract_id=?;";

    private final static String UPDATE_OWNER_ACCOUNT_BY_BUILDING_ID = "UPDATE accounts SET balance=?+balance WHERE account_number IN" +
            "(SELECT account_number FROM buildings WHERE building_id=?);";

    private final static String UPDATE_OWNER_ACCOUNT_BALANCE_BY_ACCOUNT_NUMBER = "UPDATE accounts SET balance=?+balance WHERE account_number=?;";

    private final static String UPDATE_OWNER_ACCOUNT_BY_APARTMENT_ID = "UPDATE accounts SET balance=?+balance WHERE account_number IN" +
            "(SELECT account_number FROM buildings WHERE building_id IN " +
            "(SELECT building_id FROM apartments WHERE apartment_id=?));";

    private final static String UPDATE_OWNER_ACCOUNT_BY_SERVICE_CONTRACT_ID = "UPDATE accounts SET balance=?+balance WHERE account_number IN" +
            "(SELECT account_number FROM buildings WHERE building_id IN " +
            "(SELECT building_id FROM service_contracts WHERE contract_id=?));";

    private final static String UPDATE_OWNER_ACCOUNT_BALANCE_BY_RENTING_CONTRACT_ID = "UPDATE accounts SET balance=?+balance WHERE account_number IN" +
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
            "SELECT p.id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,apartments.number AS description " +
            "FROM debit_payments p JOIN renting_contracts ON p.reason_id=renting_contracts.contract_id " +
            "JOIN apartments ON renting_contracts.apartment_id=apartments.apartment_id " +
            "JOIN buildings ON building.building_id=apartments.building_id " +
            "WHERE p.account_number=? AND type=3 " +
            "UNION " +
            "SELECT p.id,p.date,p.amount,p.comment,p.account_number,100 AS type,building.address, '' AS description " +
            "FROM credit_payments NATURAL JOIN apartments NATURAL JOIN buildings" +
            "WHERE p.account_number=? AND confirmed=true " +
            "ORDER BY buildings.building_id, date;";

    private final static String REPORT_BY_BUILDING = "" +
            "SELECT p.id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,service_company_types.type_name AS description " +
            "FROM debit_payments p " +
            "JOIN service_contracts c ON c.contract_id=p.reason_id " +
            "JOIN buildings ON c.building_id=buildings.building_id " +
            "JOIN service_companies ON c.company_id=service_companies.company_id " +
            "JOIN service_company_types types ON types.type_id=service_companies.type " +
            "WHERE buildings.building_id=? AND type=0 " +
            "UNION " +
            "SELECT p.id,p.date,p.amount,p.comment,p.account_number,p,type,buildings.address,'' description " +
            "FROM debit_payments p " +
            "JOIN buildings ON buildings.building_id=p.reason_id " +
            "WHERE buildings.building_id=? AND type=1 " +
            "UNION " +
            "SELECT p.id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,apartments.number AS description " +
            "FROM debit_payments p JOIN apartments ON p.reason_id=apartments.apartment_id " +
            "JOIN buildings ON building.building_id=apartments.building_id " +
            "WHERE buildings.building_id=? AND type=2 " +
            "UNION " +
            "SELECT p.id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,apartments.number AS description " +
            "FROM debit_payments p JOIN renting_contracts ON p.reason_id=renting_contracts.contract_id " +
            "JOIN apartments ON renting_contracts.apartment_id=apartments.apartment_id " +
            "JOIN buildings ON building.building_id=apartments.building_id " +
            "WHERE buildings.building_id=? AND type=3 " +
            "UNION " +
            "SELECT p.id,p.date,p.amount,p.comment,p.account_number,100 AS type,building.address, '' AS description " +
            "FROM credit_payments NATURAL JOIN apartments NATURAL JOIN buildings" +
            "WHERE buildings.building_id=? AND confirmed=true " +
            "ORDER BY date;";

    private final static String REPORT_BY_RENTING_CONTRACT = "" +
            "SELECT p.id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,apartments.number AS description " +
            "FROM debit_payments p JOIN renting_contracts ON p.reason_id=renting_contracts.contract_id " +
            "JOIN apartments ON renting_contracts.apartment_id=apartments.apartment_id " +
            "JOIN buildings ON building.building_id=apartments.building_id " +
            "WHERE type=3 AND renting_contracts.contract_id=? " +
            "UNION " +
            "SELECT p.id,p.date,p.amount,p.comment,p.account_number,100 AS type,building.address, '' AS description " +
            "FROM credit_payments NATURAL JOIN apartments NATURAL JOIN buildings" +
            "WHERE renting_contracts.contract_id=? AND confirmed=true " +
            "ORDER BY date;";

    @Transactional
    public CreditPaymentVO create(CreditPaymentVO payment) throws SQLException {
        Connection con = null;
        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            doCreateCreditPayment(con, payment);
            if (payment.isConfirmed()) {
                doUpdateOwnerBalance(con, payment.getAmount(), payment.getAccountNumber());
            } else {
                doUpdateTenantBalance(con, payment.getAmount().negate(), payment.getContractId());
            }
            con.commit();
            return payment;
        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) con.close();
        }
    }

    @Transactional
    public DebitPaymentVO create(DebitPaymentVO payment) throws SQLException {
        Connection con = null;
        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            doCreateDebitPayment(con, payment);
            doUpdateOwnerBalance(con, payment.getAmount().negate(), payment.getAccountNumber());
            return payment;
        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) con.close();
        }
    }

    @Transactional
    public RentingContractVO create(RentingContractVO contract) throws SQLException {
        Connection con = null;
        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            CreditPaymentVO rent = new CreditPaymentVO(0, contract.getStartDate(), contract.getRentPrice().add(contract.getEstimatedFees()), null, false, true, contract.getId(), null, null);
            CreditPaymentVO deposit = new CreditPaymentVO(0, contract.getStartDate(), contract.getDeposit(), null, true, true, contract.getId(), null, null);
            doCreateCreditPayment(con, rent);
            doCreateCreditPayment(con, deposit);
            doUpdateOwnerBalance(con, rent.getAmount().add(deposit.getAmount()), deposit.getAccountNumber());
            return contract;
        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) con.close();
        }
    }

    public List<Payment> getRentingContractReport(int id){
        return jdbcTemplate.query(REPORT_BY_RENTING_CONTRACT, getRowMapper(), id, id);
    }

    public List<Payment> getBuildingReport(int id){
        return jdbcTemplate.query(REPORT_BY_BUILDING, getRowMapper(), id, id, id, id, id);
    }

    public List<Payment> getOwnerAccountReport(int id){
        return jdbcTemplate.query(REPORT_BY_ACCOUNT, getRowMapper(), id, id, id, id, id);
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

    private void doCreateCreditPayment(Connection con, CreditPaymentVO payment) throws SQLException {
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
        ResultSet res = createPayment.executeQuery();
        payment.setId(res.getLong("payment_id"));
        payment.setAccountNumber(res.getString("account_number"));
    }

    private void doCreateDebitPayment(Connection con, DebitPaymentVO payment) throws SQLException {
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
        ResultSet res = createPayment.executeQuery();
        payment.setId(res.getLong("payment_id"));
        payment.setAccountNumber(res.getString("account_number"));
    }

    private void doUpdateOwnerBalance(Connection con, BigDecimal amount, String accountNumber) throws SQLException {
        PreparedStatement updateOwnerBalance = con.prepareStatement(UPDATE_OWNER_ACCOUNT_BALANCE_BY_ACCOUNT_NUMBER);
        updateOwnerBalance.setBigDecimal(1, amount);
        updateOwnerBalance.setString(2, accountNumber);
        updateOwnerBalance.execute();
    }

    private void doUpdateTenantBalance(Connection con, BigDecimal amount, int contractId) throws SQLException {
        PreparedStatement updateTenantBalance = con.prepareStatement(UPDATE_RENTING_CONTRACT_BALANCE);
        updateTenantBalance.setBigDecimal(1, amount);
        updateTenantBalance.setInt(2, contractId);
        updateTenantBalance.execute();
    }
}
