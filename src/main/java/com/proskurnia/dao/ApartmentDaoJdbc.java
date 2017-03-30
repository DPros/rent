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

    private final static String INSERT = "INSERT INTO apartments (room_number,size,building_id) VALUES(?,?,?) RETURNING apartment_id;";

    private final static String UPDATE = "UPDATE apartments SET room_number=?, size=? WHERE apartment_id=?;";

    private final static String SELECT_ALL = "SELECT a.apartment_id,a.room_number,a.size,a.building_id,buildings.address,renting_contracts.contract_id,persons.name " +
            "FROM apartments a NATURAL JOIN buildings NATURAL LEFT JOIN renting_contracts LEFT JOIN persons ON renting_contracts.tenant_id=persons.person_id";

    private final static String DELETE = "DELETE FROM apartments WHERE apartment_id=?;";

    private final static String SELECT_BY_BUILDING = SELECT_ALL + " WHERE building_id=?;";

    private final static String SELECT_BY_ID = SELECT_ALL + " WHERE apartment_id=?;";

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
                rs.getInt("building_id"),
                rs.getString("address"),
                rs.getInt("contract_id"),
                rs.getString("name")
        );
    }

    @Override
    public List<ApartmentVO> getByBuildingId(int buildingId) {
        return jdbcTemplate.query(SELECT_BY_BUILDING, getRowMapper());
    }

    @Override
    public List<ApartmentVO> getEmptyByBuildingId(int buildingId) {
        return null;
    }
}
