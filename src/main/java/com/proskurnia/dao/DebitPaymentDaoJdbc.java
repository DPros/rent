package com.proskurnia.dao;

import com.proskurnia.VOs.DebitPaymentVO;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

/**
 * Created by D on 26.03.2017.
 */
public class DebitPaymentDaoJdbc extends LazyJdbcDao<DebitPaymentVO, Long> implements DebitPaymentDao {

    final static String DELETE = "DELETE FROM debit_payments WHERE payment_id=?;";

    final static String SELECT_BY_CONTRACT_ID = "" +
            "SELECT p.id,p.date,p.amount,p.comment,buildings.address,service_company_types.type_name description " +
            "FROM debit_payments p " +
            "JOIN service_contracts c ON c.contract_id=p.reason_id " +
            "JOIN buildings ON c.building_id=buildings.building_id " +
            "JOIN service_companies ON c.company_id=service_companies.company_id " +
            "JOIN service_company_types types ON types.type_id=service_companies.type " +
            "WHERE contract_id=? AND type=0 " +
            "UNION " +
            "SELECT p.id,p.date,p.amount,p.comment,buildings.address,'' description " +
            "FROM debit_payments p " +
            "JOIN buildings ON buildings.building_id=p.reason_id " +
            "WHERE contract_id=? AND type=1 " +
            "UNION " +
            "SELECT p.id,p.date,p.amount,p.comment,buildings.address,apartments.number description " +
            "FROM debit_payments p JOIN apartments ON p.reason_id=apartments.apartment_id " +
            "JOIN buildings ON building.building_id=apartments.building_id " +
            "WHERE contract_id=? AND type=0 ";

    @Override
    protected PreparedStatementCreator getStatementCreator(DebitPaymentVO o, QueryType queryType) {
        return null;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE;
    }

    @Override
    protected String getByIdQuery() {
        return null;
    }

    @Override
    protected String getAllQuery() {
        return null;
    }

    @Override
    protected RowMapper<DebitPaymentVO> getRowMapper() {
        return (rs, rowNum) -> new DebitPaymentVO(
                rs.getLong("payment_id"),
                rs.getTimestamp("date"),
                rs.getBigDecimal("amount"),
                rs.getString("comment"),
                DebitPaymentVO.DebitPaymentType.valueOf(rs.getInt("type")),
                rs.getInt("reason_id"),
                rs.getString("description")
        );
    }
}
