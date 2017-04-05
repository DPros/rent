package com.proskurnia.services;

import com.proskurnia.VOs.RentingContractVO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by dmpr0116 on 24.03.2017.
 */
public interface RentingContractService extends GenericService<RentingContractVO, Integer> {

    List<RentingContractVO> getByTenantId(int id);

    List<RentingContractVO> getActiveContracts();

    void endContract(Timestamp date, int id);

    void returnDeposit(int id, BigDecimal amount, Timestamp timestamp) throws SQLException;

    List<RentingContractVO> getByBuildingId(Integer id);
}
