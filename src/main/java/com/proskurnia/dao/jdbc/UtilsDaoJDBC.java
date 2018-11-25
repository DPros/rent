package com.proskurnia.dao.jdbc;

import com.proskurnia.dao.UtilsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by D on 16.03.2017.
 */
@Repository
public class UtilsDaoJDBC implements UtilsDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final static String GET_TITLES = "SELECT * FROM titles;";
    private final static String CREATE_TITLE = "INSERT INTO titles (title_name) values (?) RETURNING title_id;";
    private final static String DELETE_TITLE = "DELETE FROM titles WHERE title_id=?;";
    private final static String UPDATE_TITLE = "UPDATE titles SET title_name=? WHERE title_id=?;";
    private final static String GET_SERVICE_COMPANY_TYPES = "SELECT * FROM service_company_types;";
    private final static String CREATE_SERVICE_COMPANY_TYPE = "INSERT INTO service_company_types (type_name) values (?) RETURNING type_id;";
    private final static String DELETE_SERVICE_COMPANY_TYPE = "DELETE FROM service_company_types WHERE type_id=?;";
    private final static String UPDATE_SERVICE_COMPANY_TYPE = "UPDATE service_company_types SET type_name=? WHERE type_id=?;";

    public Map<Integer, String> getPersonTitles() {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(GET_TITLES);
        Map<Integer, String> res = new HashMap<>();
        while (rowSet.next()) {
            res.put(rowSet.getInt("title_id"), rowSet.getString("title_name"));
        }
        return res;
    }

    public int createTitle(String value) throws SQLException {
        return jdbcTemplate.queryForObject(CREATE_TITLE, int.class, value);
    }

    @Override
    public void deleteTitle(int key) throws SQLException {
        jdbcTemplate.update(DELETE_TITLE, key);
    }

    @Override
    public void updateTitle(String value, int key) throws SQLException {
        jdbcTemplate.update(UPDATE_TITLE, value, key);
    }

    @Override
    public Map<Integer, String> getServiceCompanyTypes() {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(GET_SERVICE_COMPANY_TYPES);
        Map<Integer, String> res = new HashMap<>();
        while (rowSet.next()) {
            res.put(rowSet.getInt("type_id"), rowSet.getString("type_name"));
        }
        return res;
    }

    @Override
    public int createServiceCompanyType(String value) throws Exception {
        return jdbcTemplate.queryForObject(CREATE_SERVICE_COMPANY_TYPE, int.class, value);
    }

    @Override
    public void deleteServiceCompanyType(int key) throws SQLException {
        jdbcTemplate.update(DELETE_SERVICE_COMPANY_TYPE, key);
    }

    @Override
    public void updateServiceCompanyType(String value, int key) throws SQLException {
        jdbcTemplate.update(UPDATE_SERVICE_COMPANY_TYPE, value, key);
    }
}
