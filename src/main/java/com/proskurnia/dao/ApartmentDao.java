package com.proskurnia.dao;

import com.proskurnia.VOs.ApartmentVO;

import java.util.List;

/**
 * Created by D on 22.03.2017.
 */
public interface ApartmentDao extends Dao<ApartmentVO, Integer> {

    List<ApartmentVO> getByBuildingId(int buildingId);
}
