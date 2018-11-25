package com.proskurnia.dao.jdbc;

import com.proskurnia.VOs.OwnerAccountVO;
import com.proskurnia.dao.OwnerAccountDao;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by D on 21.03.2017.
 */
@Repository
public class OwnerAccountDaoJdbc extends LazyJdbcDao<OwnerAccountVO, String> implements OwnerAccountDao {

    private final static String INSERT = "INSERT INTO accounts (balance,bank_id,owner_id,account_number) VALUES(?,?,?,?) RETURNING account_number;";

    private final static String SELECT_ALL = "SELECT * FROM accounts NATURAL JOIN banks;";

    private final static String DELETE = "DELETE FROM accounts WHERE account_number=?;";

    private final static String SELECT_BY_ID = "SELECT * FROM accounts NATURAL JOIN banks WHERE account_number=?;";

    private final static String SELECT_BY_OWNER_ID = "SELECT * FROM accounts NATURAL JOIN banks WHERE owner_id=?;";

    private final static String SELECT_ALL_ACCOUNT_OF_OWNER_BY_ACCOUNT_ID = "SELECT * FROM accounts NATURAL JOIN banks WHERE owner_id IN (SELECT owner_id FROM accounts WHERE account_number=?);";

    @Override
    protected PreparedStatementCreator getStatementCreator(OwnerAccountVO o, QueryType queryType) {
        return con -> {
            PreparedStatement ps = con.prepareStatement(INSERT);
            int index = 0;
            ps.setBigDecimal(++index, o.getBalance());
            ps.setInt(++index, o.getBankId());
            ps.setInt(++index, o.getOwnerId());
            ps.setString(++index, o.getId());
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
                rs.getString("account_number"),
                rs.getInt("bank_id"),
                rs.getBigDecimal("balance"),
                rs.getInt("owner_id"),
                rs.getString("name")
        );
    }

    @Override
    public List<OwnerAccountVO> getByOwnerId(int id) {
        return jdbcTemplate.query(SELECT_BY_OWNER_ID, getRowMapper(), id);
    }

    @Override
    public List<OwnerAccountVO> getByAccountId(String id) {
        return jdbcTemplate.query(SELECT_ALL_ACCOUNT_OF_OWNER_BY_ACCOUNT_ID, getRowMapper(), id);
    }
}
