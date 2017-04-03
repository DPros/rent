package com.proskurnia.services;

import com.proskurnia.VOs.RentingContractVO;

import java.util.List;

/**
 * Created by dmpr0116 on 24.03.2017.
 */
public interface RentingContractService extends GenericService<RentingContractVO, Integer> {

    List<RentingContractVO> getByTenantId(int id);
}
