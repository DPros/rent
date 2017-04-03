package com.proskurnia.services;

import com.proskurnia.VOs.RentingContractVO;
import com.proskurnia.dao.RentingContractDao;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by dmpr0116 on 24.03.2017.
 */
@Service
public class RentingContractServiceImpl extends GenericServiceImpl<RentingContractVO, Integer> implements RentingContractService {
    @Override
    public List<RentingContractVO> getByTenantId(int id) {
        return ((RentingContractDao)dao).getByTenantId(id);
    }

    @Override
    public void endContract(Timestamp date, int id) {
        ((RentingContractDao)dao).endContract(date, id);
    }

    @Override
    public void returnDeposit(int id) {
        ((RentingContractDao)dao).returnDeposit(id);
    }
}
