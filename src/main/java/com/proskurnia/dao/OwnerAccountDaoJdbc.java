package com.proskurnia.dao;

import com.proskurnia.VOs.OwnerAccountVO;
import com.proskurnia.VOs.PersonVO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by D on 21.03.2017.
 */
public class OwnerAccountDaoJdbc extends Lazy_JDBC_DAO<OwnerAccountVO, Integer> implements OwnerAccountDao {

    private final static String INSERT = "INSERT INTO accounts (number,balance,bank_id,owner_id) VALUES(?,?,?,?) RETURNING account_id;";

    private final static String UPDATE = "UPDATE account SET number=?, balance=?, bank_id=?, owner_id=? WHERE account_id=?;";

    private final static String SELECT_ALL = "SELECT * FROM accounts NATURAL JOIN banks;";

    private final static String DELETE = "DELETE FROM accounts WHERE account_id=?;";

    private final static String SELECT_BY_ID = "SELECT * FROM accounts NATURAL JOIN banks WHERE account_id=?;";

    private final static String SELECT_BY_OWNER = "SELECT * FROM accounts NATURAL JOIN banks WHERE owner_id=?;";

    @Override
    protected PreparedStatementCreator getStatementCreator(OwnerAccountVO o, QueryType queryType) {
        return con -> {
            PreparedStatement ps = con.prepareStatement(queryType == QueryType.INSERT ? INSERT : UPDATE);
            int index = 0;
            ps.setString(++index, o.getNumber());
            ps.setBigDecimal(++index, o.getBalance());
            ps.setInt(++index, o.getBankId());
            ps.setInt(++index, o.getOwnerId());
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
        return SELECT_BY_ID;
    }

    @Override
    protected String getAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected RowMapper<OwnerAccountVO> getRowMapper() {
        return (rs, rowNum) -> new OwnerAccountVO(
                rs.getInt("account_id"),
                rs.getString("number"),
                rs.getInt("bank_id"),
                rs.getBigDecimal("balance"),
                rs.getInt("owner_id")
        );
    }

    @Override
    public List<OwnerAccountVO> getByOwner(PersonVO p) {
        return jdbcTemplate.query(SELECT_BY_OWNER, new Object[]{p.getId()}, getRowMapper());
    }
}
