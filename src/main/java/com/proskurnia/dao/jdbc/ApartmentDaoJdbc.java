package com.proskurnia.dao.jdbc;

import com.proskurnia.VOs.ApartmentVO;
import com.proskurnia.dao.ApartmentDao;
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

    private final static String SELECT_ALL = "SELECT a.apartment_id,a.room_number,a.size,a.building_id,buildings.address,c.contract_id,persons.person_id,persons.name " +
            "FROM apartments a NATURAL JOIN buildings NATURAL LEFT JOIN (SELECT * FROM renting_contracts WHERE actual_end_date IS NULL) c LEFT JOIN persons ON c.tenant_id=persons.person_id";

    private final static String DELETE = "DELETE FROM apartments WHERE apartment_id=?;";

    private final static String SELECT_EMPTY_BY_BUILDING = "SELECT a.apartment_id,a.room_number,a.size,a.building_id,buildings.address,0 contract_id,0 person_id,'' AS name FROM apartments a NATURAL JOIN buildings " +
            "WHERE building_id=? AND NOT EXISTS (SELECT 1 FROM renting_contracts WHERE apartment_id=a.apartment_id AND actual_end_date IS NULL);";

    private final static String SELECT_BY_BUILDING = SELECT_ALL + " WHERE building_id=? ORDER BY room_number;";

    private final static String SELECT_BY_ID = SELECT_ALL + " WHERE apartment_id=?;";

    @Override
    protected PreparedStatementCreator getStatementCreator(ApartmentVO o, QueryType queryType) {
        return con -> {
            PreparedStatement ps = con.prepareStatement(queryType == QueryType.INSERT ? INSERT : UPDATE);
            int index = 0;
            ps.setString(++index, o.getRoomNumber());
            ps.setDouble(++index, o.getSize());
            if (queryType == QueryType.UPDATE) {
                ps.setInt(++index, o.getId());
            } else {
                ps.setInt(++index, o.getBuildingId());
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
                rs.getInt("person_id"),
                rs.getString("name")
        );
    }

    @Override
    public List<ApartmentVO> getByBuildingId(int buildingId) {
        return jdbcTemplate.query(SELECT_BY_BUILDING, getRowMapper(), buildingId);
    }

    @Override
    public List<ApartmentVO> getEmptyByBuildingId(int buildingId) {
        return jdbcTemplate.query(SELECT_EMPTY_BY_BUILDING, getRowMapper(), buildingId);
    }
}
