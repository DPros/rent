package com.proskurnia.services;

import com.proskurnia.VOs.BuildingVO;
import com.proskurnia.VOs.PersonVO;
import com.proskurnia.services.GenericService;

import java.util.List;

/**
 * Created by D on 21.03.2017.
 */
public interface BuildingService extends GenericService<BuildingVO, Integer> {

    List<BuildingVO> getByOwner(PersonVO p);
}
