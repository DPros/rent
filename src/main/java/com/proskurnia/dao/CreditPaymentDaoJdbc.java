package com.proskurnia.dao;

import com.proskurnia.VOs.CreditPaymentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by D on 26.03.2017.
 */
@Repository
public class CreditPaymentDaoJdbc extends LazyJdbcDao<CreditPaymentVO, Long> implements CreditPaymentDao {

    private final static String DELETE = "DELETE FROM credit_payments WHERE payment_id=?;";

    private final static String GET_BY_CONTRACT_ID = "SELECT * FROM credit_payments WHERE payment_id=?;";

    @Autowired
    MoneyFlowJdbcUtils moneyFlowJdbcUtils;

    @Override
    public CreditPaymentVO create(CreditPaymentVO o) throws SQLException {
        return moneyFlowJdbcUtils.createCreditPayment(o);
    }

    @Override
    protected PreparedStatementCreator getStatementCreator(CreditPaymentVO o, QueryType queryType) {
        return null;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE;
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
        return (rs, rowNum) -> new CreditPaymentVO(
                rs.getLong("payment_id"),
                rs.getTimestamp("date"),
                rs.getBigDecimal("amount"),
                rs.getString("comment"),
                rs.getBoolean("deposit"),
                rs.getBoolean("confirmed"),
                rs.getInt("contract_id")
        );
    }

    public List<CreditPaymentVO> getByContractId(int contractId) {
        return jdbcTemplate.query(GET_BY_CONTRACT_ID, getRowMapper(), contractId);
    }
}
