package com.proskurnia.services;

import com.proskurnia.VOs.ServiceContractVO;
import com.proskurnia.dao.ServiceContractDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by D on 25.03.2017.
 */
@Service
public class ServiceContractServiceImpl extends GenericServiceImpl<ServiceContractVO, Integer> implements ServiceContractService {
    @Override
    public List<ServiceContractVO> getByBuildingId(int id) {
        return ((ServiceContractDao)dao).getByBuildingId(id);
    }
}
