package com.proskurnia.dao;

import com.proskurnia.VOs.RentingContractVO;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by dmpr0116 on 24.03.2017.
 */
public interface RentingContractDao extends Dao<RentingContractVO, Integer> {

    void endContract(Timestamp date, int contractId);

    List<RentingContractVO> getByTenantId(int tenantId);
}