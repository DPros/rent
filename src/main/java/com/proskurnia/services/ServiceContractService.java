package com.proskurnia.services;

import com.proskurnia.VOs.ServiceContractVO;

import java.util.List;

/**
 * Created by D on 25.03.2017.
 */
public interface ServiceContractService extends GenericService<ServiceContractVO, Integer> {

    List<ServiceContractVO> getByBuildingId(int id);
}
