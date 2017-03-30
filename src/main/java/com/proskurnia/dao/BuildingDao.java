package com.proskurnia.dao;

import com.proskurnia.VOs.BuildingVO;

import java.util.List;
import java.util.Map;

/**
 * Created by D on 21.03.2017.
 */
public interface BuildingDao extends Dao<BuildingVO, Integer> {

    List<BuildingVO> getByOwnerId(int id);

    Map<Integer, String> getBuildingsWithEmptyApartments();
}
