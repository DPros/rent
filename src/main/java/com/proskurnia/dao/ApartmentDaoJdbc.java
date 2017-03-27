package com.proskurnia.dao;

import com.proskurnia.VOs.ApartmentVO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by D on 22.03.2017.
 */
@Repository
public class ApartmentDaoJdbc extends LazyJdbcDao<ApartmentVO, Integer> implements ApartmentDao {

    private final static String INSERT = "INSERT INTO apartments (roomNumber,size,building_id) VALUES(?,?,?) RETURNING apartment_id;";

    private final static String UPDATE = "UPDATE apartments SET roomNumber=?, size=? WHERE apartment_id=?;";

    private final static String SELECT_ALL = "SELECT * FROM apartments;";

    private final static String DELETE = "DELETE FROM apartments WHERE apartment_id=?;";

    private final static String SELECT_BY_BUILDING = "SELECT * FROM apartments WHERE building_id=?;";

    private final static String SELECT_BY_ID = "SELECT * FROM apartments WHERE apartment_id=?;";

    @Override
    protected PreparedStatementCreator getStatementCreator(ApartmentVO o, QueryType queryType) {
        return con -> {
            PreparedStatement ps = con.prepareStatement(queryType == QueryType.INSERT ? INSERT : UPDATE);
            int index = 0;
            ps.setString(++index, o.getRoomNumber());
            ps.setDouble(++index, o.getSize());
            ps.setInt(++index, o.getBuildingId());
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
    protected RowMapper<ApartmentVO> getRowMapper() {
        return (rs, rowNum) -> new ApartmentVO(
                rs.getInt("apartment_id"),
                rs.getString("room_number"),
                rs.getDouble("size"),
                rs.getInt("building_id")
        );
    }

    @Override
    public List<ApartmentVO> getByBuildingId(int buildingId) {
        return jdbcTemplate.query(SELECT_BY_BUILDING, getRowMapper());
    }
}
