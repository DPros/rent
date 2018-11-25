package com.proskurnia.dao.jdbc;

import com.proskurnia.VOs.CreditPaymentVO;
import com.proskurnia.dao.CreditPaymentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
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
        return moneyFlowJdbcUtils.create(o);
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
        return null;
    }

    public List<CreditPaymentVO> getByContractId(int contractId) {
        return jdbcTemplate.query(GET_BY_CONTRACT_ID, getRowMapper(), contractId);
    }
}
