package com.proskurnia.dao.jdbc;

import com.proskurnia.dao.CredentialsDao;
import com.proskurnia.services.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

/**
 * Created by D on 08.04.2017.
 */
@Repository
public class CredentialsDaoJdbc implements CredentialsDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void changeUsername(String username, String newUsername) {
        jdbcTemplate.update("UPDATE users SET username=? WHERE username=?", newUsername, username);
    }

    @Override
    public void changePassword(String username, String password) {
        jdbcTemplate.update("UPDATE users SET password=? WHERE username=?", password, username);
    }

    @Override
    public UserDetailsServiceImpl.User getUser(String username) {
        try {
            SqlRowSet rs = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE username=?", username);
            rs.next();
            return new UserDetailsServiceImpl.User(username, rs.getString("password"), rs.getString("role"));
        } catch (Exception e) {
            return null;
        }
    }
}
