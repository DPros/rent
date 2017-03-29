package com.proskurnia.dao;

import com.proskurnia.VOs.ServiceContractVO;

import java.util.List;

/**
 * Created by D on 25.03.2017.
 */
public interface ServiceContractDao extends Dao<ServiceContractVO, Integer> {
    List<ServiceContractVO> getByBuildingId(int id);
}
