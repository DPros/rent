package com.proskurnia.dao;

import com.proskurnia.VOs.EquipmentVO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

/**
 * Created by D on 25.03.2017.
 */
@Repository
public class EquipmentDaoJdbc extends LazyJdbcDao<EquipmentVO, String> implements EquipmentDao {

    private final static String INSERT = "INSERT INTO equipment(wifi_name,wifi_password,serial_number,service_contract_id) VALUES(?,?,?,?);";

    private final static String UPDATE = "UPDATE equipment SET wifi_name=?,wifi_password=? WHERE serial_number=?;";

    private final static String DELETE = "DELETE FROM equipment WHERE serial_number=?;";

    private final static String GET_BY_ID = "SELECT * FROM equipment WHERE serial_number=?;";

    @Override
    protected PreparedStatementCreator getStatementCreator(EquipmentVO o, QueryType queryType) {
        return con -> {
            PreparedStatement ps = con.prepareStatement(queryType == QueryType.UPDATE ? UPDATE : INSERT);
            int index = 0;
            ps.setString(++index, o.getWifiName());
            ps.setString(++index, o.getWifiPassword());
            ps.setString(++index, o.getId());
            if(queryType == QueryType.INSERT){
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
        return null;
    }

    @Override
    protected RowMapper<EquipmentVO> getRowMapper() {
        return null;
    }
}