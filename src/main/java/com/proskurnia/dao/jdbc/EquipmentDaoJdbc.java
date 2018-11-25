package com.proskurnia.dao.jdbc;

import com.proskurnia.VOs.EquipmentVO;
import com.proskurnia.dao.EquipmentDao;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by D on 25.03.2017.
 */
@Repository
public class EquipmentDaoJdbc extends LazyJdbcDao<EquipmentVO, String> implements EquipmentDao {

    private final static String INSERT = "INSERT INTO equipment(wifi_name,wifi_password,serial_number,service_contract_id) VALUES(?,?,?,?);";

    private final static String UPDATE = "UPDATE equipment SET wifi_name=?,wifi_password=? WHERE serial_number=?;";

    private final static String DELETE = "DELETE FROM equipment WHERE serial_number=?;";

    private final static String GET_BY_ID = "SELECT serial_number,wifi_name,wifi_password,service_contract_id,building_id,address FROM equipment NATURAL JOIN service_contracts NATURAL JOIN buildings WHERE serial_number=?;";

    private final static String GET_BY_BUILDING_ID = "SELECT serial_number,wifi_name,wifi_password,service_contract_id,building_id,address FROM equipment NATURAL JOIN service_contracts NATURAL JOIN buildings WHERE building_id=?;";

    private final static String GET_BY_SERVICE_CONTRACT_ID = "SELECT serial_number,wifi_name,wifi_password,service_contract_id,building_id,address FROM equipment NATURAL JOIN service_contracts NATURAL JOIN buildings WHERE service_contract_id=?;";

    private final static String GET_ALL = "SELECT serial_number,wifi_name,wifi_password,service_contract_id,building_id,address FROM equipment NATURAL JOIN service_contracts NATURAL JOIN buildings;";

    @Override
    protected PreparedStatementCreator getStatementCreator(EquipmentVO o, QueryType queryType) {
        return con -> {
            PreparedStatement ps = con.prepareStatement(queryType == QueryType.UPDATE ? UPDATE : INSERT);
            int index = 0;
            ps.setString(++index, o.getWifiName());
            ps.setString(++index, o.getWifiPassword());
            ps.setString(++index, o.getId());
            if (queryType == QueryType.INSERT) {
                ps.setInt(++index, o.getServiceContractId());
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
        return GET_BY_ID;
    }

    @Override
    protected String getAllQuery() {
        return GET_ALL;
    }

    @Override
    protected RowMapper<EquipmentVO> getRowMapper() {
        return (rs, rowNum) -> new EquipmentVO(
                rs.getString("serial_number"),
                rs.getString("wifi_name"),
                rs.getString("wifi_password"),
                rs.getInt("service_contract_id"),
                rs.getInt("building_id"),
                rs.getString("address")
        );
    }

    @Override
    public List<EquipmentVO> getByBuildingId(int id) {
        return jdbcTemplate.query(GET_BY_BUILDING_ID, getRowMapper(), id);
    }

    @Override
    public List<EquipmentVO> getByServiceContractId(int id) {
        return jdbcTemplate.query(GET_BY_SERVICE_CONTRACT_ID, getRowMapper(), id);
    }
}