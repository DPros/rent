package com.proskurnia.dao;

import com.proskurnia.VOs.BuildingVO;
import com.proskurnia.VOs.PersonVO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by D on 21.03.2017.
 */
@Repository
public class BuildingDaoJdbc extends LazyJdbcDao<BuildingVO, Integer> implements BuildingDao {

    private final static String EMPTY_APARTMENTS_COUNT_FOR_BUILDING = "(SELECT COUNT(*) FROM apartments WHERE building_id=building.building_id AND NOT EXISTS (SELECT * FROM renting_contracts WHERE apartment_id=apartments.apartment.id AND actual_end_date IS NULL AND start_date < current_timestamp AND expected_end_date > current_timestamp)) AS empty_apartments";

    private final static String INSERT = "INSERT INTO buildings (address,acquisition_date,construction_date,date_of_sale,comment,account_id,manageable) VALUES(?,?,?,?,?,?,?) RETURNING building_id;";

    private final static String UPDATE = "UPDATE account SET address=?, acquisition_date=?, construction_date=?, date_of_sale=?, comment=?,account_id=?,manageable=? WHERE building_id=?;";

    private final static String SELECT_ALL = "SELECT *, " + EMPTY_APARTMENTS_COUNT_FOR_BUILDING + "FROM buildings";

    private final static String DELETE = "DELETE FROM buildings WHERE building_id=?;";

    private final static String SELECT_BY_ID = "SELECT *, " + EMPTY_APARTMENTS_COUNT_FOR_BUILDING + " FROM buildings WHERE building_id=?;";

    private final static String SELECT_BY_OWNER = "SELECT *, " + EMPTY_APARTMENTS_COUNT_FOR_BUILDING + " FROM buildings NATURAL JOIN accounts WHERE owner_id=?;";

    @Override
    public List<BuildingVO> getByOwner(PersonVO p) {
        return jdbcTemplate.query(SELECT_BY_OWNER, getRowMapper());
    }

    @Override
    protected PreparedStatementCreator getStatementCreator(BuildingVO o, QueryType queryType) {
        return con -> {
            PreparedStatement ps = con.prepareStatement(queryType == QueryType.INSERT ? INSERT : UPDATE);
            int index = 0;
            ps.setString(++index, o.getAddress());
            ps.setTimestamp(++index, o.getAcquisitionDate());
            ps.setTimestamp(++index, o.getConstructionDate());
            ps.setTimestamp(++index, o.getDateOfSale());
            ps.setString(++index, o.getComment());
            ps.setInt(++index, o.getOwnerAccountId());
            ps.setBoolean(++index, o.isManageable());
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
    protected RowMapper<BuildingVO> getRowMapper() {
        return (rs, rowNum) -> new BuildingVO(
                rs.getInt("building_id"),
                rs.getString("address"),
                rs.getTimestamp("acquisition_date"),
                rs.getTimestamp("construction_date"),
                rs.getTimestamp("date_of_sale"),
                rs.getBoolean("manageable"),
                rs.getString("comment"),
                rs.getInt("account_id"),
                rs.getInt("empty_apartments")
        );
    }
}