package com.proskurnia.services;

import com.proskurnia.VOs.ApartmentVO;
import com.proskurnia.dao.ApartmentDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by D on 22.03.2017.
 */
@Service
public class ApartmentServiceImpl extends GenericServiceImpl<ApartmentVO, Integer> implements ApartmentService {

    @Override
    public List<ApartmentVO> getByBuildingId(int buildingId) {
        return ((ApartmentDao)dao).getByBuildingId(buildingId);
    }

    @Override
    public List<ApartmentVO> getEmptyByBuilding(int buildingId) {
        return ((ApartmentDao)dao).getEmptyByBuildingId(buildingId);
    }
}
