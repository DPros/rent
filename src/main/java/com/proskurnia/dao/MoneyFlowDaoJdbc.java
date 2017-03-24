package com.proskurnia.dao;

import com.proskurnia.VOs.CreditPaymentVO;
import com.proskurnia.VOs.DebitPaymentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by dmpr0116 on 24.03.2017.
 */
@Repository
public class MoneyFlowDaoJdbc implements MoneyFlowDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CreditPaymentDao creditPaymentDao = new CreditPaymentDao();
    private DebitPaymentDao debitPaymentDao = new DebitPaymentDao();

    final static String FILTER_START_DATE = " AND date=>?";
    final static String FILTER_END_DATE = " AND date<=?";

    private final static String UPDATE_RENTING_CONTRACT_BALANCE = "UPDATE renting_contracts SET balance=?+balance WHERE contract_id=?;";
    private final static String UPDATE_RENTING_CONTRACT_DEPOSIT = "UPDATE renting_contracts SET deposit=?+balance WHERE contract_id=?;";

    @Override
    @Transactional
    public CreditPaymentVO createCreditPayment(CreditPaymentVO payment) throws SQLException {
        Connection con = null;
        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            PreparedStatement updateBalance = payment.isDeposit() ? con.prepareStatement(UPDATE_RENTING_CONTRACT_DEPOSIT) : con.prepareStatement(UPDATE_RENTING_CONTRACT_BALANCE);
            updateBalance.setBigDecimal(1, payment.isDeposit() || !payment.isConfirmed() ? payment.getAmount().negate() : BigDecimal.ZERO);
            updateBalance.setInt(2, payment.getContractId());
            PreparedStatement createPayment = con.prepareStatement(creditPaymentDao.INSERT);
            int index = 0;
            createPayment.setTimestamp(++index, payment.getDate());
            createPayment.setBigDecimal(++index, payment.getAmount());
            createPayment.setString(++index, payment.getComment());
            createPayment.setBoolean(++index, payment.isDeposit());
            createPayment.setBoolean(++index, payment.isConfirmed());
            createPayment.setInt(++index, payment.getContractId());
            payment.setId(createPayment.executeQuery().getLong("payment_id"));
            con.commit();
            return payment;
        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) con.close();
        }
    }

    @Override
    public DebitPaymentVO createDebitPayment(DebitPaymentVO payment) {
        Connection con = null;
        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            PreparedStatement updateBalance = payment.isDeposit() ? con.prepareStatement(UPDATE_RENTING_CONTRACT_DEPOSIT) : con.prepareStatement(UPDATE_RENTING_CONTRACT_BALANCE);
            updateBalance.setBigDecimal(1, payment.isDeposit() || !payment.isConfirmed() ? payment.getAmount().negate() : BigDecimal.ZERO);
            updateBalance.setInt(2, payment.getContractId());
            PreparedStatement create = con.prepareStatement(creditPaymentDao.INSERT);

            return null;
        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) con.close();
        }
    }

    private class CreditPaymentDao extends Lazy_JDBC_DAO<CreditPaymentVO, Long> {

        final static String SELECT_BY_CONTRACT_ID = "SELECT * FROM credit_payments WHERE contract_id=?";

        final static String INSERT = "INSERT INTO credit_payments (date,amount,comment,deposit,confirmed,contract_id) VALUES(?,?,?,?,?,?) RETURNING payment_id;";

        public List<CreditPaymentVO> getByContractId(int contractId, Timestamp from, Timestamp till) {
            return from != null ? till != null ?
                    jdbcTemplate.query(SELECT_BY_CONTRACT_ID + FILTER_START_DATE + FILTER_END_DATE, getRowMapper(), contractId, from, till) :
                    jdbcTemplate.query(SELECT_BY_CONTRACT_ID + FILTER_START_DATE, getRowMapper(), contractId, from) :
                    till != null ? jdbcTemplate.query(SELECT_BY_CONTRACT_ID + FILTER_END_DATE, getRowMapper(), contractId, from) :
                            jdbcTemplate.query(SELECT_BY_CONTRACT_ID, getRowMapper(), contractId);
        }

        @Override
        protected PreparedStatementCreator getStatementCreator(CreditPaymentVO o, QueryType queryType) {
            return null;
        }

        @Override
        protected String getDeleteQuery() {
            return null;
        }

        @Override
        protected String getByIdQuery() {
            return null;
        }

        @Override
        protected String getAllQuery() {
            return null;
        }

        @Override
        protected RowMapper<CreditPaymentVO> getRowMapper() {
            return null;
        }
    }

    private class DebitPaymentDao extends Lazy_JDBC_DAO<DebitPaymentVO, Long> {

        final static String SELECT_BY_CONTRACT_ID = "" +
                "SELECT p.id,p.date,p.amount,p.comment,buildings.address,service_company_types.type_name description " +
                "FROM debit_payments p " +
                "JOIN service_contracts c ON c.contract_id=p.reason_id " +
                "JOIN buildings ON c.building_id=buildings.building_id " +
                "JOIN service_companies ON c.company_id=service_companies.company_id " +
                "JOIN service_company_types types ON types.type_id=service_companies.type " +
                "WHERE contract_id=? AND type=0 " +
                "UNION " +
                "SELECT p.id,p.date,p.amount,p.comment,buildings.address,'' description " +
                "FROM debit_payments p " +
                "JOIN buildings ON buildings.building_id=p.reason_id " +
                "WHERE contract_id=? AND type=1 " +
                "UNION " +
                "SELECT p.id,p.date,p.amount,p.comment,buildings.address,apartments.number description " +
                "FROM debit_payments p JOIN apartments ON p.reason_id=apartments.apartment_id " +
                "JOIN buildings ON building.building_id=apartments.building_id " +
                "WHERE contract_id=? AND type=0";

        final static String INSERT = "INSERT INTO debit_payments (date,amount,comment,type,reason_id) VALUES(?,?,?,?,?);";

        @Override
        protected PreparedStatementCreator getStatementCreator(DebitPaymentVO o, QueryType queryType) {
            return null;
        }

        @Override
        protected String getDeleteQuery() {
            return null;
        }

        @Override
        protected String getByIdQuery() {
            return null;
        }

        @Override
        protected String getAllQuery() {
            return null;
        }

        @Override
        protected RowMapper<DebitPaymentVO> getRowMapper() {
            return null;
        }
    }
}
