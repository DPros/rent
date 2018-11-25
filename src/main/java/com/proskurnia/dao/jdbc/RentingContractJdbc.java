package com.proskurnia.dao.jdbc;

import com.proskurnia.VOs.RentingContractVO;
import com.proskurnia.dao.RentingContractDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
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

    private final static String END_CONTRACT = "UPDATE renting_contracts SET actual_end_date=? WHERE contract_id=?;";

    private final static String DELETE = "DELETE FROM renting_contracts WHERE contract_id=?;";

    private final static String SELECT_BY_TENANT_ID = "SELECT *,(SELECT amount FROM credit_payments WHERE contract_id=renting_contracts.contract_id AND deposit=TRUE) FROM renting_contracts NATURAL JOIN apartments NATURAL JOIN buildings JOIN persons ON renting_contracts.tenant_id=persons.person_id WHERE tenant_id=?;";

    private final static String SELECT_BY_ID = "SELECT * FROM renting_contracts NATURAL JOIN apartments NATURAL JOIN buildings JOIN persons ON renting_contracts.tenant_id=persons.person_id WHERE contract_id=?;";

    private final static String GET_ACTIVE = "SELECT * FROM renting_contracts NATURAL JOIN apartments NATURAL JOIN buildings JOIN persons ON renting_contracts.tenant_id=persons.person_id WHERE actual_end_date IS NULL;";

    private final static String SELECT_BY_BUILDING_ID = "SELECT *,(SELECT amount FROM credit_payments WHERE contract_id=renting_contracts.contract_id AND deposit=TRUE) FROM renting_contracts NATURAL JOIN apartments NATURAL JOIN buildings JOIN persons ON renting_contracts.tenant_id=persons.person_id WHERE building_id=?;";

    private final static String UPDATE = "UPDATE renting_contracts SET estimated_fees=? WHERE contract_id=?";

    @Override
    protected PreparedStatementCreator getStatementCreator(RentingContractVO o, QueryType queryType) {
        return con -> {
            PreparedStatement ps = con.prepareStatement(UPDATE);
            int index = 0;
            ps.setBigDecimal(++index, o.getEstimatedFees());
            ps.setInt(++index, o.getId());
            return ps;
        };
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
                rs.getBigDecimal("estimated_fees"),
                rs.getTimestamp("start_date"),
                rs.getTimestamp("expected_end_date"),
                rs.getTimestamp("actual_end_date"),
                rs.getInt("tenant_id"),
                rs.getInt("apartment_id"),
                rs.getString("name"),
                rs.getInt("building_id"),
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
    @Transactional
    public void returnDeposit(int id, BigDecimal amount, Timestamp timestamp) throws SQLException {
        moneyFlowJdbcUtils.returnDeposit(id, amount, timestamp);
    }

    @Override
    public List<RentingContractVO> getActive() {
        return jdbcTemplate.query(GET_ACTIVE, getRowMapper());
    }

    @Override
    public List<RentingContractVO> getByBuildingId(Integer id) {
        return jdbcTemplate.query(SELECT_BY_BUILDING_ID, getRowMapper(), id);
    }
}
