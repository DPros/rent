package com.proskurnia.dao.jdbc;

import com.proskurnia.VOs.BuildingVO;
import com.proskurnia.dao.BuildingDao;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by D on 21.03.2017.
 */
@Repository
@Aspect
public class BuildingDaoJdbc extends EagerJdbcDao<BuildingVO, Integer> implements BuildingDao {

    private final static String EMPTY_APARTMENTS_COUNT_FOR_BUILDING = "(SELECT COUNT(*) FROM apartments WHERE building_id=b.building_id AND NOT EXISTS (SELECT 1 FROM renting_contracts WHERE apartment_id=apartments.apartment_id AND actual_end_date IS NULL AND start_date < current_timestamp AND expected_end_date > current_timestamp)) AS empty_apartments";

    private final static String INSERT = "INSERT INTO buildings (address,acquisition_date,construction_date,date_of_sale,comment,account_number,manageable) VALUES(?,?,?,?,?,?,?) RETURNING building_id;";

    private final static String UPDATE = "UPDATE account SET address=?, acquisition_date=?, construction_date=?, date_of_sale=?, comment=?,account_id=?,manageable=? WHERE building_id=?;";

    private final static String SELECT_WITH_EMPTY_APARTMENTS = "SELECT address,building_id " +
            "FROM buildings WHERE EXISTS " +
            "(SELECT 1 FROM apartments WHERE building_id=buildings.building_id AND " +
            "NOT EXISTS (SELECT * FROM renting_contracts WHERE apartment_id=apartments.apartment_id AND " +
            "actual_end_date IS NULL AND start_date < current_timestamp AND expected_end_date > current_timestamp)) " +
            "ORDER BY address;";

    private final static String SELECT_ALL = "SELECT " +
            "b.building_id,b.address,b.acquisition_date,b.construction_date,b.date_of_sale,b.manageable,b.comment,b.account_number,persons.person_id,persons.name, " + EMPTY_APARTMENTS_COUNT_FOR_BUILDING +
            " FROM buildings b NATURAL JOIN accounts JOIN persons ON persons.person_id=accounts.owner_id;";

    private final static String DELETE = "DELETE FROM buildings WHERE building_id=?;";

    private final static String SELECT_BY_ID = "SELECT " +
            "b.building_id,b.address,b.acquisition_date,b.construction_date,b.date_of_sale,b.manageable,b.comment,b.account_number,persons.person_id,persons.name, '-1' AS empty_apartments" +
            " FROM buildings b NATURAL JOIN accounts JOIN persons ON persons.person_id=accounts.owner_id WHERE building_id=?;";

    private final static String SELECT_BY_OWNER = "SELECT " +
            "b.building_id,b.address,b.acquisition_date,b.construction_date,b.date_of_sale,b.manageable,b.comment,b.account_number,persons.person_id,persons.name, " + EMPTY_APARTMENTS_COUNT_FOR_BUILDING +
            " FROM buildings b NATURAL JOIN accounts JOIN persons ON persons.person_id=accounts.owner_id WHERE owner_id=?;";

    @Override
    public List<BuildingVO> getByOwnerId(int id) {
        return jdbcTemplate.query(SELECT_BY_OWNER, getRowMapper(), id);
    }

    @Override
    public Map<Integer, String> getBuildingsWithEmptyApartments(Timestamp startDate) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(SELECT_WITH_EMPTY_APARTMENTS);
        Map<Integer, String> res = new LinkedHashMap<>();
        while (rowSet.next()) {
            res.put(rowSet.getInt("building_id"), rowSet.getString("address"));
        }
        return res;
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
            ps.setString(++index, o.getOwnerAccountId());
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

//    @Override
//    protected String getByIdQuery() {
//        return SELECT_BY_ID;
//    }

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
                rs.getString("account_number"),
                rs.getInt("empty_apartments"),
                rs.getString("name"),
                rs.getInt("person_id")
        );
    }

    @AfterReturning("(target(com.proskurnia.services.RentingContractService+) && " +
            "(execution(* create(..)) || execution(* endContract(..))))")
    public void update() {
        super.init();
    }

    @AfterReturning("(target(com.proskurnia.services.ApartmentService+) && " +
            "(execution(* create(..)) || execution(* delete(..))))")
    public void update2() {
        super.init();
    }
}
