package com.proskurnia.dao;

import com.proskurnia.VOs.BankVO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;

/**
 * Created by D on 25.03.2017.
 */
public class BankDaoJdbc extends LazyJdbcDao<BankVO, Integer> implements BankDao {

    private final static String INSERT = "INSERT INTO banks(address,phone,email,name) VALUES(?,?,?,?) RETURNING bank_id;";

    private final static String UPDATE = "UPDATE banks SET address=?,phone=?,email=?,name=? WHERE bank_id=?;";

    private final static String DELETE = "DELETE FROM banks WHERE bank_id=?;";

    private final static String GET_ALL = "SELECT * FROM banks;";

    private final static String GET_BY_ID = "SELECT * FROM banks WHERE bank_id=?;";

    @Override
    protected PreparedStatementCreator getStatementCreator(BankVO o, QueryType queryType) {
        return con -> {
            PreparedStatement ps = con.prepareStatement(queryType == QueryType.UPDATE ? UPDATE : INSERT);
            int index = 0;
            ps.setString(++index, o.getAddress());
            ps.setString(++index, o.getPhone());
            ps.setString(++index, o.getEmail());
            ps.setString(++index, o.getName());
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
        return GET_ALL;
    }

    @Override
    protected RowMapper<BankVO> getRowMapper() {
        return (rs, rowNum) -> new BankVO(
                rs.getInt("bank_id"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("phone"),
                rs.getString("email")
        );
    }
}
