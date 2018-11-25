package com.proskurnia.dao.jdbc;

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
import java.sql.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by dmpr0116 on 24.03.2017.
 */
@Repository
public class MoneyFlowJdbcUtils {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static String UPDATE_RENTING_CONTRACT_RETURN_DEPOSIT = "UPDATE renting_contracts SET deposit_amount=?,deposit_returned=TRUE WHERE contract_id=?";

    private final static String DELETE_CREDIT = "DELETE FROM credit_payments WHERE payment_id=? RETURNING amount,account_number;";
    private final static String DELETE_DEBIT = "DELETE FROM debit_payments WHERE payment_id=? RETURNING amount,account_number;";

    private final static String INSERT_CREDIT = "INSERT INTO credit_payments (date,amount,comment,deposit,contract_id,account_number) " +
            "VALUES(?,?,?,?,?,(SELECT account_number FROM buildings WHERE building_id IN " +
            "(SELECT building_id FROM apartments WHERE apartment_id IN " +
            "(SELECT apartment_id FROM renting_contracts WHERE contract_id=?)))) RETURNING payment_id,account_number;";

    private final static String INSERT_DEBIT = "INSERT INTO debit_payments (date,amount,comment,type,reason_id,account_number) " +
            "VALUES(?,?,?,?,?,(CASE ? WHEN 0 THEN (SELECT account_number FROM buildings " +
            "WHERE building_id IN (SELECT building_id FROM service_contracts WHERE contract_id=?)) " +
            "WHEN 1 THEN (SELECT account_number FROM buildings WHERE building_id=?) " +
            "WHEN 2 THEN (SELECT account_number FROM buildings WHERE building_id IN " +
            "(SELECT building_id FROM apartments WHERE apartment_id=?)) " +
            "WHEN 3 THEN (SELECT account_number FROM buildings WHERE building_id IN " +
            "(SELECT building_id FROM apartments WHERE apartment_id IN " +
            "(SELECT apartment_id FROM renting_contracts WHERE contract_id=?)))END)) RETURNING payment_id,account_number;";

    private final static String INSERT_RENTING_CONTRACT = "INSERT INTO renting_contracts(rent_price,estimated_fees,deposit_amount,start_date,expected_end_date,tenant_id,apartment_id) VALUES(?,?,?,?,?,?,?) RETURNING contract_id;";

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

    private final static String GET_ALL = "" +
            "SELECT p.reason_id,p.payment_id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,types.type_name AS description " +
            "FROM debit_payments p " +
            "JOIN service_contracts c ON c.contract_id=p.reason_id " +
            "JOIN buildings ON c.building_id=buildings.building_id " +
            "JOIN service_companies ON c.company_id=service_companies.company_id " +
            "JOIN service_company_types types ON types.type_id=service_companies.type_id " +
            "WHERE type=0 " +
            "UNION " +
            "SELECT p.reason_id,p.payment_id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,'' description " +
            "FROM debit_payments p " +
            "JOIN buildings ON buildings.building_id=p.reason_id " +
            "WHERE type=1 " +
            "UNION " +
            "SELECT p.reason_id,p.payment_id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,apartments.room_number AS description " +
            "FROM debit_payments p JOIN apartments ON p.reason_id=apartments.apartment_id " +
            "JOIN buildings ON buildings.building_id=apartments.building_id " +
            "WHERE type=2 " +
            "UNION " +
            "SELECT p.reason_id,p.payment_id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,apartments.room_number AS description " +
            "FROM debit_payments p JOIN renting_contracts ON p.reason_id=renting_contracts.contract_id " +
            "JOIN apartments ON renting_contracts.apartment_id=apartments.apartment_id " +
            "JOIN buildings ON buildings.building_id=apartments.building_id " +
            "WHERE type=3 " +
            "UNION " +
            "SELECT p.contract_id AS reason_id,p.payment_id,p.date,p.amount,p.comment,p.account_number,100 AS type,buildings.address,apartments.room_number AS description " +
            "FROM credit_payments p NATURAL JOIN renting_contracts NATURAL JOIN apartments JOIN buildings ON apartments.building_id=buildings.building_id " +
            "ORDER BY date;";

    private final static String REPORT_BY_ACCOUNT = "" +
            "SELECT p.reason_id,p.payment_id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,types.type_name AS description " +
            "FROM debit_payments p " +
            "JOIN service_contracts c ON c.contract_id=p.reason_id " +
            "JOIN buildings ON c.building_id=buildings.building_id " +
            "JOIN service_companies ON c.company_id=service_companies.company_id " +
            "JOIN service_company_types types ON types.type_id=service_companies.type_id " +
            "WHERE p.account_number=? AND type=0 " +
            "UNION " +
            "SELECT p.reason_id,p.payment_id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,'' description " +
            "FROM debit_payments p " +
            "JOIN buildings ON buildings.building_id=p.reason_id " +
            "WHERE p.account_number=? AND type=1 " +
            "UNION " +
            "SELECT p.reason_id,p.payment_id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,apartments.room_number AS description " +
            "FROM debit_payments p JOIN apartments ON p.reason_id=apartments.apartment_id " +
            "JOIN buildings ON buildings.building_id=apartments.building_id " +
            "WHERE p.account_number=? AND type=2 " +
            "UNION " +
            "SELECT p.reason_id,p.payment_id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,apartments.room_number AS description " +
            "FROM debit_payments p JOIN renting_contracts ON p.reason_id=renting_contracts.contract_id " +
            "JOIN apartments ON renting_contracts.apartment_id=apartments.apartment_id " +
            "JOIN buildings ON buildings.building_id=apartments.building_id " +
            "WHERE p.account_number=? AND type=3 " +
            "UNION " +
            "SELECT p.contract_id AS reason_id,p.payment_id,p.date,p.amount,p.comment,p.account_number,100 AS type,buildings.address,apartments.room_number AS description " +
            "FROM credit_payments p NATURAL JOIN renting_contracts NATURAL JOIN apartments JOIN buildings ON apartments.building_id=buildings.building_id " +
            "WHERE p.account_number=? " +
            "ORDER BY address, date;";

    private final static String REPORT_BY_BUILDING = "" +
            "SELECT p.reason_id,p.payment_id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,types.type_name AS description " +
            "FROM debit_payments p " +
            "JOIN service_contracts c ON c.contract_id=p.reason_id " +
            "JOIN buildings ON c.building_id=buildings.building_id " +
            "JOIN service_companies ON c.company_id=service_companies.company_id " +
            "JOIN service_company_types types ON types.type_id=service_companies.type_id " +
            "WHERE buildings.building_id=? AND type=0 " +
            "UNION " +
            "SELECT p.reason_id,p.payment_id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,'' description " +
            "FROM debit_payments p " +
            "JOIN buildings ON buildings.building_id=p.reason_id " +
            "WHERE buildings.building_id=? AND type=1 " +
            "UNION " +
            "SELECT p.reason_id,p.payment_id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,apartments.room_number AS description " +
            "FROM debit_payments p JOIN apartments ON p.reason_id=apartments.apartment_id " +
            "JOIN buildings ON buildings.building_id=apartments.building_id " +
            "WHERE buildings.building_id=? AND type=2 " +
            "UNION " +
            "SELECT p.reason_id,p.payment_id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,apartments.room_number AS description " +
            "FROM debit_payments p JOIN renting_contracts ON p.reason_id=renting_contracts.contract_id " +
            "JOIN apartments ON renting_contracts.apartment_id=apartments.apartment_id " +
            "JOIN buildings ON buildings.building_id=apartments.building_id " +
            "WHERE buildings.building_id=? AND type=3 " +
            "UNION " +
            "SELECT p.contract_id AS reason_id,p.payment_id,p.date,p.amount,p.comment,p.account_number,100 AS type,buildings.address,apartments.room_number AS description " +
            "FROM credit_payments p NATURAL JOIN renting_contracts NATURAL JOIN apartments JOIN buildings ON apartments.building_id=buildings.building_id " +
            "WHERE buildings.building_id=? " +
            "ORDER BY date;";

    private final static String REPORT_BY_RENTING_CONTRACT = "" +
            "SELECT p.reason_id,p.payment_id,p.date,p.amount,p.comment,p.account_number,p.type,buildings.address,apartments.room_number AS description " +
            "FROM debit_payments p JOIN renting_contracts ON p.reason_id=renting_contracts.contract_id " +
            "JOIN apartments ON renting_contracts.apartment_id=apartments.apartment_id " +
            "JOIN buildings ON buildings.building_id=apartments.building_id " +
            "WHERE type=3 AND renting_contracts.contract_id=? " +
            "UNION " +
            "SELECT p.contract_id AS reason_id,p.payment_id,p.date,p.amount,p.comment,p.account_number,100 AS type,buildings.address,apartments.room_number AS description " +
            "FROM credit_payments p NATURAL JOIN renting_contracts NATURAL JOIN apartments JOIN buildings ON apartments.building_id=buildings.building_id " +
            "WHERE p.contract_id=? " +
            "ORDER BY date;";

    @Transactional
    public CreditPaymentVO create(CreditPaymentVO payment) throws SQLException {
        Connection con = null;
        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            doCreateCreditPayment(con, payment);
            doUpdateOwnerBalance(con, payment.getAmount(), payment.getAccountNumber());
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
    public void returnDeposit(int id, BigDecimal amount, Timestamp timestamp) throws SQLException {
        Connection con = null;
        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            DebitPaymentVO returnDeposit = new DebitPaymentVO();
            returnDeposit.setReasonId(id);
            returnDeposit.setType(Payment.PaymentType.ReturnCreditPayment);
            returnDeposit.setAmount(amount);
            returnDeposit.setDate(timestamp);
            doCreateDebitPayment(con, returnDeposit);
            doUpdateOwnerBalance(con, amount.negate(), returnDeposit.getAccountNumber());
            PreparedStatement updateContract = con.prepareStatement(UPDATE_RENTING_CONTRACT_RETURN_DEPOSIT);
            updateContract.setBigDecimal(1, amount);
            updateContract.setInt(2, id);
            updateContract.execute();
            con.commit();
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
            doCreateRentingContract(con, contract);
            CreditPaymentVO rent = new CreditPaymentVO(0, contract.getStartDate(), contract.getRentPrice().add(contract.getEstimatedFees()), null, false, contract.getId(), null, null, null);
            CreditPaymentVO deposit = new CreditPaymentVO(0, contract.getStartDate(), contract.getDeposit(), null, true, contract.getId(), null, null, null);
            doCreateCreditPayment(con, rent);
            doCreateCreditPayment(con, deposit);
            doUpdateOwnerBalance(con, rent.getAmount().add(deposit.getAmount()), deposit.getAccountNumber());
            con.commit();
            return contract;
        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) con.close();
        }
    }

    @Transactional
    public void delete(long id, boolean credit) throws SQLException {
        Connection con = null;
        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            PreparedStatement delete = con.prepareStatement(credit ? DELETE_CREDIT : DELETE_DEBIT);
            delete.setLong(1, id);
            ResultSet rs = delete.executeQuery();
            rs.next();
            BigDecimal amount = rs.getBigDecimal("amount");
            String account = rs.getString("account_number");
            doUpdateOwnerBalance(con, credit ? amount.negate() : amount, account);
            con.commit();
        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) con.close();
        }
    }

    public List<Payment> getRentingContractReport(int id) {
        return jdbcTemplate.query(REPORT_BY_RENTING_CONTRACT, getRowMapper(), id, id);
    }

    public List<Payment> getBuildingReport(int id) {
        return jdbcTemplate.query(REPORT_BY_BUILDING, getRowMapper(), id, id, id, id, id);
    }

    public List<Payment> getOwnerAccountReport(String id) {
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
                        rs.getInt("reason_id"),
                        rs.getString("account_number"),
                        rs.getString("address"),
                        rs.getString("description")
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

    private void doCreateRentingContract(Connection con, RentingContractVO contract) throws SQLException {
        PreparedStatement ps = con.prepareCall(INSERT_RENTING_CONTRACT);
        int index = 0;
        ps.setBigDecimal(++index, contract.getRentPrice());
        ps.setBigDecimal(++index, contract.getEstimatedFees());
        ps.setBigDecimal(++index, contract.getDeposit());
        ps.setTimestamp(++index, contract.getStartDate());
        ps.setTimestamp(++index, contract.getExpectedEndDate());
        ps.setInt(++index, contract.getTenantId());
        ps.setInt(++index, contract.getApartmentId());
        ResultSet rs = ps.executeQuery();
        rs.next();
        contract.setId(rs.getInt("contract_id"));
    }

    private void doCreateCreditPayment(Connection con, CreditPaymentVO payment) throws SQLException {
        PreparedStatement createPayment = con.prepareStatement(INSERT_CREDIT);
        int index = 0;
        createPayment.setTimestamp(++index, payment.getDate());
        createPayment.setBigDecimal(++index, payment.getAmount());
        createPayment.setString(++index, payment.getComment());
        createPayment.setBoolean(++index, payment.isDeposit());
        createPayment.setInt(++index, payment.getContractId());
        createPayment.setInt(++index, payment.getContractId());
        ResultSet res = createPayment.executeQuery();
        res.next();
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
        createPayment.setInt(++index, payment.getReasonId());
        ResultSet res = createPayment.executeQuery();
        res.next();
        payment.setId(res.getLong("payment_id"));
        payment.setAccountNumber(res.getString("account_number"));
    }

    private void doUpdateOwnerBalance(Connection con, BigDecimal amount, String accountNumber) throws SQLException {
        PreparedStatement updateOwnerBalance = con.prepareStatement(UPDATE_OWNER_ACCOUNT_BALANCE_BY_ACCOUNT_NUMBER);
        updateOwnerBalance.setBigDecimal(1, amount);
        updateOwnerBalance.setString(2, accountNumber);
        updateOwnerBalance.execute();
    }

    public Collection<Payment> getAll() {
        return jdbcTemplate.query(GET_ALL, getRowMapper());
    }
}
