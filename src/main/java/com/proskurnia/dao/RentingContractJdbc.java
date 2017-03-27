package com.proskurnia.dao;

import com.proskurnia.VOs.RentingContractVO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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

    private final static String INSERT = "INSERT INTO renting_contracts(rent_price,deposit_amount,estimated_fees,start_date,expected_end_date,tenant_id,apartment_id) VALUES(?,?,?,?,?,?,?) RETURNING contract_id;";



    private final static String RETURN_DEPOSIT = "UPDATE renting_contracts SET deposit_returned=true WHERE contract_id=?;";

    private final static String END_CONTRACT = "UPDATE renting_contracts SET actual_end_date=? WHERE contract_id=?;";

    private final static String DELETE = "DELETE FROM renting_contracts WHERE contract_id=?;";

    private final static String SELECT_BY_TENANT_ID = "SELECT * FROM renting_contracts NATURAL JOIN apartments NATURAL JOIN buildings WHERE tenant_id=?;";

    private final static String SELECT_BY_ID = "SELECT * FROM renting_contracts NATURAL JOIN apartments NATURAL JOIN buildings WHERE contract_id=?;";

    @Override
    protected PreparedStatementCreator getStatementCreator(RentingContractVO o, QueryType queryType) {
        return con -> {
            PreparedStatement ps = con.prepareStatement(INSERT);
            int index = 0;
            ps.setBigDecimal(++index, o.getRentPrice());
            ps.setBigDecimal(++index, o.getDeposit());
            ps.setBigDecimal(++index, o.getEstimatedFees());
            ps.setTimestamp(++index, o.getStartDate());
            ps.setTimestamp(++index, o.getExpectedEndDate());
            ps.setInt(++index, o.getTenantId());
            ps.setInt(++index, o.getApartmentId());
            return ps;
        };
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
        return new RowMapper<RentingContractVO>() {
            @Override
            public RentingContractVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new RentingContractVO(
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
                        rs.getInt("apartment_id")
                );
            }
        };
    }

    @Override
    public void endContract(Timestamp date, int contractId) {
        jdbcTemplate.update(END_CONTRACT, date, contractId);
    }

    @Override
    public List<RentingContractVO> getByTenantId(int tenantId) {
        return jdbcTemplate.query(SELECT_BY_TENANT_ID, getRowMapper(), tenantId);
    }
}
