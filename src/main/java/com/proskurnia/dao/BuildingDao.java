package com.proskurnia.dao;

import com.proskurnia.VOs.BuildingVO;
import com.proskurnia.VOs.PersonVO;

import java.util.List;

/**
 * Created by D on 21.03.2017.
 */
public interface BuildingDao extends Dao<BuildingVO, Integer> {

    List<BuildingVO> getByOwnerId(int id);
}
