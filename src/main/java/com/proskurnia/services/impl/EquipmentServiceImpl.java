package com.proskurnia.services.impl;

import com.proskurnia.VOs.EquipmentVO;
import com.proskurnia.dao.EquipmentDao;
import com.proskurnia.services.EquipmentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by D on 25.03.2017.
 */
@Service
public class EquipmentServiceImpl extends GenericServiceImpl<EquipmentVO, String, EquipmentDao> implements EquipmentService {
    @Override
    public List<EquipmentVO> getByBuildingId(int id) {
        return dao.getByBuildingId(id);
    }

    @Override
    public List<EquipmentVO> getByServiceContractId(int id) {
        return dao.getByServiceContractId(id);
    }
}
