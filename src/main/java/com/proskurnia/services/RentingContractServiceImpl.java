package com.proskurnia.services;

import com.proskurnia.VOs.RentingContractVO;
import com.proskurnia.dao.RentingContractDao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by dmpr0116 on 24.03.2017.
 */
@Service
public class RentingContractServiceImpl extends GenericServiceImpl<RentingContractVO, Integer> implements RentingContractService {
    @Override
    public List<RentingContractVO> getByTenantId(int id) {
        return ((RentingContractDao) dao).getByTenantId(id);
    }

    @Override
    public List<RentingContractVO> getActiveContracts() {
        return ((RentingContractDao) dao).getActive();
    }

    @Override
    public void endContract(Timestamp date, int id) {
        ((RentingContractDao) dao).endContract(date, id);
    }

    @Override
    public void returnDeposit(int id, BigDecimal amount, Timestamp timestamp) throws SQLException {
        ((RentingContractDao) dao).returnDeposit(id, amount, timestamp);
    }

    @Override
    public List<RentingContractVO> getByBuildingId(Integer id) {
        return ((RentingContractDao) dao).getByBuildingId(id);
    }
}
