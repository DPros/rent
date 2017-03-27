package com.proskurnia.dao;

import com.proskurnia.VOs.ServiceCompanyVO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by D on 25.03.2017.
 */
@Repository
public class ServiceCompanyDaoJdbc extends LazyJdbcDao<ServiceCompanyVO, Integer> implements ServiceCompanyDao {

    private final static String INSERT = "INSERT INTO service_companies(name,address,comment,type_id,email,phone1,phone2) VALUES (?,?,?,?,?,?,?) RETURNING company_id;";

    private final static String UPDATE = "UPDATE service_companies SET name=?,address=?,comment=?,type_id=?,email=?,phone1=?,phone2=? WHERE company_id=?;";

    private final static String DELETE = "DELETE FROM service_companies WHERE company_id=?;";

    private final static String GET_BY_ID = "SELECT * FROM service_companies WHERE company_id=?;";

    private final static String GET_BY_TYPE = "SELECT * FROM service_companies WHERE type_id=?;";

    @Override
    protected PreparedStatementCreator getStatementCreator(ServiceCompanyVO o, QueryType queryType) {
        return con -> {
            PreparedStatement ps = con.prepareStatement(queryType == QueryType.UPDATE ? UPDATE : INSERT);
            int index = 0;
            ps.setString(++index, o.getName());
            ps.setString(++index, o.getAddress());
            ps.setString(++index, o.getComment());
            ps.setInt(++index, o.getTypeId());
            ps.setString(++index, o.getEmail());
            ps.setString(++index, o.getPhone1());
            ps.setString(++index, o.getPhone2());
            if (queryType == QueryType.UPDATE) {
                ps.setInt(++index, o.getId());
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
    protected RowMapper<ServiceCompanyVO> getRowMapper() {
        return (rs, rowNum) -> new ServiceCompanyVO(
                rs.getInt("company_id"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("phone1"),
                rs.getString("phone2"),
                rs.getString("email"),
                rs.getInt("type_id"),
                rs.getString("comment")
        );
    }

    @Override
    public List<ServiceCompanyVO> getByType(int typeId) {
        return jdbcTemplate.query(GET_BY_TYPE, getRowMapper(), typeId);
    }
}
