package com.proskurnia.services.impl;

import com.proskurnia.VOs.ApartmentVO;
import com.proskurnia.dao.ApartmentDao;
import com.proskurnia.services.ApartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by D on 22.03.2017.
 */
@Service
public class ApartmentServiceImpl extends GenericServiceImpl<ApartmentVO, Integer, ApartmentDao> implements ApartmentService {

    @Override
    public List<ApartmentVO> getByBuildingId(int buildingId) {
        return dao.getByBuildingId(buildingId);
    }

    @Override
    public List<ApartmentVO> getEmptyByBuilding(int buildingId) {
        return dao.getEmptyByBuildingId(buildingId);
    }
}
