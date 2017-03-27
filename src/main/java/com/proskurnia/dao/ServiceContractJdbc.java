package com.proskurnia.dao;

import com.proskurnia.VOs.ServiceContractVO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;

/**
 * Created by D on 25.03.2017.
 */
public class ServiceContractJdbc extends LazyJdbcDao<ServiceContractVO, Integer> implements ServiceContractDao {

    private static final String INSERT = "INSERT INTO service_contracts(comment,login,password,company_id,building_id,start_date,end_date) VALUES (?,?,?,?,?,?,?);";

    private static final String UPDATE = "UPDATE service_contracts SET comment=?,login=?,password=? WHERE contract_id=?;";

    private final static String DELETE = "DELETE FROM service_contracts WHERE contract_id=?;";

    private final static String GET_BY_ID = "SELECT FROM service_contracts WHERE contract_id=?;";

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
        return null;
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
                rs.getInt("building_id")
        );
    }
}