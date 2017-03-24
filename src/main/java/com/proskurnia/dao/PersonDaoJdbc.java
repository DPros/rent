package com.proskurnia.dao;

import com.proskurnia.VOs.PersonVO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by D on 14.03.2017.
 */
@Repository
public class PersonDaoJdbc extends Eager_JDBC_DAO<PersonVO, Integer> implements PersonDao {

    private final static String INSERT = "INSERT INTO persons(passport,name,address,caf,title_id,phone1,phone2,email1,email2,is_owner) VALUES(?,?,?,?,?,?,?,?) RETURNING person_id;";

    private final static String UPDATE = "UPDATE persons SET passport=?,name=?,address=?,caf=?,title_id=?,phone1=?,phone2=?,email1=?,email2=? WHERE person_id=?;";

    private final static String SELECT_ALL = "SELECT * FROM persons NATURAL JOIN titles;";

    private final static String DELETE = "DELETE FROM persons WHERE person_id=?;";

//    @Override
//    protected PreparedStatement getModifyPS(PersonVO o, QueryType queryType, Connection conn) throws SQLException {
//
//        PreparedStatement ps = jdbcTemplate.getDataSource().getConnection().prepareStatement(queryType == QueryType.INSERT ? INSERT : UPDATE);
//        int index = 0;
//        ps.setString(++index, o.getPassport());
//        ps.setString(++index, o.getName());
//        ps.setString(++index, o.getAddress());
//        ps.setString(++index, o.getCaf());
//        ps.setInt(++index, o.getTitleId());
//        ps.setArray(++index, jdbcTemplate.getDataSource().getConnection().createArrayOf("character varying", o.getPhones());
//        if (queryType == QueryType.UPDATE) {
//            ps.setInt(++index, o.getId());
//        } else {
//            ps.setBoolean(++index, o.isOwner());
//        }
//        for (String phone : o.getPhones()) {
//            ps.setString(++index, phone);
//        }
//        for (String email : o.getEmails()) {
//            ps.setString(++index, email);
//        }
//        return ps;
//    }

    @Override
    protected String getDeleteQuery() {
        return DELETE;
    }

    @Override
    protected RowMapper<PersonVO> getRowMapper() {
        return (rs, rowNum) ->
                new PersonVO(rs.getInt("person_id"),
                        rs.getString("passport"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("caf"),
                        rs.getString("title_name"),
                        rs.getInt("title_id"),
                        rs.getString("phone1"),
                        rs.getString("phone2"),
                        rs.getString("email1"),
                        rs.getString("email2"),
                        rs.getBoolean("is_owner")
                );
    }

    @Override
    protected String getAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected PreparedStatementCreator getStatementCreator(PersonVO o, QueryType queryType) {
        return con -> {
            PreparedStatement ps = con.prepareStatement(queryType == QueryType.INSERT ? INSERT : UPDATE);
            int index = 0;
            ps.setString(++index, o.getPassport());
            ps.setString(++index, o.getName());
            ps.setString(++index, o.getAddress());
            ps.setString(++index, o.getCaf());
            ps.setInt(++index, o.getTitleId());
            ps.setString(++index, o.getPhone1());
            ps.setString(++index, o.getPhone2());
            ps.setString(++index, o.getEmail1());
            ps.setString(++index, o.getEmail2());
            if (queryType == QueryType.UPDATE) {
                ps.setInt(++index, o.getId());
            } else {
                ps.setBoolean(++index, o.isOwner());
            }
            return ps;
        };
    }

    @Override
    public Collection<PersonVO> getTenants() {
        return repository.values().stream().filter(p -> !p.isOwner()).collect(Collectors.toList());
    }

    @Override
    public Collection<PersonVO> getOwners() {
        return repository.values().stream().filter(p -> p.isOwner()).collect(Collectors.toList());
    }
}
