package com.proskurnia.dao;

import com.proskurnia.VOs.Identified;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class Eager_JDBC_DAO<T extends Identified<I>, I> implements DAO<T, I> {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    protected Map<I, T> repository = null;

    @PostConstruct
    protected void init() {
        List<T> list = jdbcTemplate.query(getAllQuery(), getRowMapper());
        repository = new HashMap<>(list.size());
        for (T o : list) {
            repository.put(o.getId(), o);
        }
    }

    public void reload() {
        init();
    }

    public Collection<T> getAll() {
        return repository.values();
    }

    public T create(T o) throws SQLException {
        jdbcTemplate.query(getStatementCreator(o, QueryType.INSERT), rs -> {
            o.setId((I) rs.getObject(1));
        });
        repository.put(o.getId(), o);
        return o;
    }

    public void update(T o) throws SQLException {
        jdbcTemplate.update(getStatementCreator(o, QueryType.UPDATE));
        repository.put(o.getId(), o);
    }

    public void delete(I id) throws SQLException {
        jdbcTemplate.update(getDeleteQuery(), id);
        repository.remove(id);
    }

//	protected abstract PreparedStatement getModifyPS(T o, QueryType insert, Connection connection) throws SQLException;

    protected abstract String getDeleteQuery();

    protected abstract RowMapper<T> getRowMapper();

    protected abstract String getAllQuery();

    protected abstract PreparedStatementCreator getStatementCreator(T o, QueryType queryType);

    public T getById(Object id) {
        return repository.get(id);
    }
}