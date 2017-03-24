package com.proskurnia.services;

import com.proskurnia.VOs.ApartmentVO;

import java.util.List;

/**
 * Created by D on 22.03.2017.
 */
public interface ApartmentService extends GenericService<ApartmentVO, Integer> {

    List<ApartmentVO> getByBuildingId(int buildingId);
}
