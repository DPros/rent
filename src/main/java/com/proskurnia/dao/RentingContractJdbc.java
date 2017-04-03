package com.proskurnia.dao;

import com.proskurnia.VOs.RentingContractVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by dmpr0116 on 24.03.2017.
 */
@Repository
public class RentingContractJdbc extends LazyJdbcDao<RentingContractVO, Integer> implements RentingContractDao {

    @Autowired
    MoneyFlowJdbcUtils moneyFlowJdbcUtils;

    private final static String RETURN_DEPOSIT = "UPDATE renting_contracts SET deposit_returned=true WHERE contract_id=?;";

    private final static String END_CONTRACT = "UPDATE renting_contracts SET actual_end_date=? WHERE contract_id=?;";

    private final static String DELETE = "DELETE FROM renting_contracts WHERE contract_id=?;";

    private final static String SELECT_BY_TENANT_ID = "SELECT *,(SELECT amount FROM credit_payments WHERE contract_id=renting_contracts.contract_id AND deposit=TRUE) FROM renting_contracts NATURAL JOIN apartments NATURAL JOIN buildings JOIN persons ON renting_contracts.tenant_id=persons.person_id WHERE tenant_id=?;";

    private final static String SELECT_BY_ID = "SELECT * FROM renting_contracts NATURAL JOIN apartments NATURAL JOIN buildings JOIN persons ON renting_contracts.tenant_id=persons.person_id WHERE contract_id=?;";

    @Override
    protected PreparedStatementCreator getStatementCreator(RentingContractVO o, QueryType queryType) {
        return null;
    }

    @Override
    public RentingContractVO create(RentingContractVO o) throws SQLException {
        return moneyFlowJdbcUtils.create(o);
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE;
    }

    @Override
    protected String getByIdQuery() {
        return SELECT_BY_ID;
    }

    @Override
    protected String getAllQuery() {
        return null;
    }

    @Override
    protected RowMapper<RentingContractVO> getRowMapper() {
        return (rs, rowNum) -> new RentingContractVO(
                rs.getInt("contract_id"),
                rs.getBigDecimal("rent_price"),
                rs.getBigDecimal("deposit_amount"),
                rs.getBoolean("deposit_returned"),
                rs.getBigDecimal("balance"),
                rs.getBigDecimal("estimated_fees"),
                rs.getTimestamp("start_date"),
                rs.getTimestamp("expected_end_date"),
                rs.getTimestamp("actual_end_date"),
                rs.getInt("tenant_id"),
                rs.getInt("apartment_id"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("room_number")
        );
    }

    @Override
    public void endContract(Timestamp date, int contractId) {
        jdbcTemplate.update(END_CONTRACT, date, contractId);
    }

    @Override
    public List<RentingContractVO> getByTenantId(int tenantId) {
        return jdbcTemplate.query(SELECT_BY_TENANT_ID, getRowMapper(), tenantId);
    }

    @Override
    public void returnDeposit(int id) {
        throw new NotImplementedException();
    }
}
