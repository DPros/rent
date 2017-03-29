package com.proskurnia.services;

import com.proskurnia.VOs.EquipmentVO;

import java.util.List;

/**
 * Created by D on 25.03.2017.
 */
public interface EquipmentService extends GenericService<EquipmentVO, String> {

    List<EquipmentVO> getByBuildingId(int id);

    List<EquipmentVO> getByServiceContractId(int id);
}
