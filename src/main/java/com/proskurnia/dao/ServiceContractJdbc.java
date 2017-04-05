package com.proskurnia.dao;

import com.proskurnia.VOs.ServiceContractVO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by D on 25.03.2017.
 */
@Repository
public class ServiceContractJdbc extends LazyJdbcDao<ServiceContractVO, Integer> implements ServiceContractDao {

    private static final String INSERT = "INSERT INTO service_contracts(comment,login,password,company_id,building_id,start_date) VALUES (?,?,?,?,?,?) RETURNING contract_id;";

    private static final String UPDATE = "UPDATE service_contracts SET comment=?,login=?,password=? WHERE contract_id=?;";

    private static final String END_CONTRACT = "UPDATE service_contracts SET end_date=? WHERE contract_id=?;";

    private final static String DELETE = "DELETE FROM service_contracts WHERE contract_id=?;";

    private final static String GET_ALL = "SELECT contract_id,service_contracts.comment,login,password,start_date,end_date,company_id,name,buildings.address,buildings.building_id,type_name FROM service_contracts NATURAL JOIN service_companies NATURAL JOIN service_company_types JOIN buildings ON service_contracts.building_id=buildings.building_id";

    private final static String GET_BY_ID = "SELECT contract_id,service_contracts.comment,login,password,start_date,end_date,company_id,name,buildings.address,buildings.building_id,type_name FROM service_contracts NATURAL JOIN service_companies NATURAL JOIN service_company_types JOIN buildings ON service_contracts.building_id=buildings.building_id WHERE contract_id=?;";

    private final static String GET_BY_BUILDING_ID = "SELECT contract_id,service_contracts.comment,login,password,start_date,end_date,company_id,name,buildings.address,buildings.building_id,type_name FROM service_contracts NATURAL JOIN service_companies NATURAL JOIN service_company_types JOIN buildings ON service_contracts.building_id=buildings.building_id WHERE buildings.building_id=? AND end_date IS NULL;";

    @Override
    protected PreparedStatementCreator getStatementCreator(ServiceContractVO o, QueryType queryType) {
        return con -> {
            PreparedStatement ps = con.prepareStatement(queryType == QueryType.UPDATE ? UPDATE : INSERT);
            int index = 0;
            ps.setString(++index, o.getComment());
            ps.setString(++index, o.getAccountLogin());
            ps.setString(++index, o.getAccountPassword());
            if (queryType == queryType.UPDATE) {
                ps.setInt(++index, o.getId());
            } else {
                ps.setInt(++index, o.getServiceCompanyId());
                ps.setInt(++index, o.getBuildingId());
                ps.setTimestamp(++index, o.getStartDate());
            }
            return ps;
        };
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE;
    }

    @Override
    protected String getByIdQuery() {
        return GET_BY_ID;
    }

    @Override
    protected String getAllQuery() {
        return GET_ALL;
    }

    @Override
    protected RowMapper<ServiceContractVO> getRowMapper() {
        return (rs, rowNum) -> new ServiceContractVO(
                rs.getInt("contract_id"),
                rs.getTimestamp("start_date"),
                rs.getTimestamp("end_date"),
                rs.getInt("company_id"),
                rs.getString("comment"),
                rs.getString("login"),
                rs.getString("password"),
                rs.getInt("building_id"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("type_name")
        );
    }

    @Override
    public List<ServiceContractVO> getByBuildingId(int id) {
        return jdbcTemplate.query(GET_BY_BUILDING_ID, getRowMapper(), id);
    }

    @Override
    public void endContract(int id, Timestamp timestamp) {
        jdbcTemplate.update(END_CONTRACT, timestamp, id);
    }
}
