package com.proskurnia.services.impl;

import com.proskurnia.VOs.ServiceContractVO;
import com.proskurnia.dao.ServiceContractDao;
import com.proskurnia.services.ServiceContractService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by D on 25.03.2017.
 */
@Service
public class ServiceContractServiceImpl extends GenericServiceImpl<ServiceContractVO, Integer, ServiceContractDao> implements ServiceContractService {
    @Override
    public List<ServiceContractVO> getByBuildingId(int id) {
        return dao.getByBuildingId(id);
    }

    @Override
    public void endContract(Timestamp timestamp, int id) {
        dao.endContract(id, timestamp);
    }
}
