package com.proskurnia.services;

import com.proskurnia.VOs.BuildingVO;
import com.proskurnia.VOs.PersonVO;
import com.proskurnia.dao.BuildingService;

import java.util.List;

/**
 * Created by D on 21.03.2017.
 */
public class BuildingServiceImpl extends GenericServiceImpl<BuildingVO, Integer> implements BuildingService {

    @Override
    public List<BuildingVO> getByOwner(PersonVO p) {
        return null;
    }
}
