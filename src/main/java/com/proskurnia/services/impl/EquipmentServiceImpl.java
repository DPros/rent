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
public class EquipmentServiceImpl extends GenericServiceImpl<EquipmentVO, String> implements EquipmentService {
    @Override
    public List<EquipmentVO> getByBuildingId(int id) {
        return ((EquipmentDao)dao).getByBuildingId(id);
    }

    @Override
    public List<EquipmentVO> getByServiceContractId(int id) {
        return ((EquipmentDao)dao).getByServiceContractId(id);
    }
}
