package com.proskurnia.services.impl;

import com.proskurnia.VOs.BuildingVO;
import com.proskurnia.dao.BuildingDao;
import com.proskurnia.services.BuildingService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by D on 21.03.2017.
 */
@Service
public class BuildingServiceImpl extends GenericServiceImpl<BuildingVO, Integer> implements BuildingService {

    @Override
    public List<BuildingVO> getByOwnerId(int id) {
        return ((BuildingDao)dao).getByOwnerId(id);
    }

    @Override
    public Map<Integer, String> getBuildingsWithEmptyApartments(Timestamp startDate) {
        return ((BuildingDao)dao).getBuildingsWithEmptyApartments(startDate);
    }
}
