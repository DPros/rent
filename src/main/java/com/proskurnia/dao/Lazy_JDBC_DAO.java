package com.proskurnia.dao;

import java.sql.SQLException;
import java.util.Collection;

import com.proskurnia.VOs.Identified;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public abstract class Lazy_JDBC_DAO<T extends Identified<I>, I> implements DAO<T, I> {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public Collection<T> getAll() {
        return jdbcTemplate.query(getAllQuery(), getRowMapper());
    }

    public T getById(Object id) {
        return jdbcTemplate.queryForObject(getByIdQuery(), getRowMapper(), id);
    }

    public T create(T o) throws SQLException {
        jdbcTemplate.query(getStatementCreator(o, QueryType.INSERT), rs -> {
            o.setId((I) rs.getObject(1));
        });
        return o;
    }

    public void update(T o) throws SQLException {
        jdbcTemplate.update(getStatementCreator(o, QueryType.UPDATE));
    }

    public void delete(I id) throws SQLException {
        jdbcTemplate.update(getDeleteQuery(), id);
    }

//    protected abstract PreparedStatement getModifyPS(T o, QueryType insert) throws SQLException;

    protected abstract PreparedStatementCreator getStatementCreator(T o, QueryType queryType);

    protected abstract String getDeleteQuery();

    protected abstract String getByIdQuery();

    protected abstract String getAllQuery();

    protected abstract RowMapper<T> getRowMapper();
}