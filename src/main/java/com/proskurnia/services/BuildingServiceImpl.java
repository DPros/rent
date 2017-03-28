package com.proskurnia.services;

import com.proskurnia.VOs.BuildingVO;
import com.proskurnia.VOs.PersonVO;
import com.proskurnia.dao.BuildingDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by D on 21.03.2017.
 */
@Service
public class BuildingServiceImpl extends GenericServiceImpl<BuildingVO, Integer> implements BuildingService {

    @Override
    public List<BuildingVO> getByOwnerId(int id) {
        return ((BuildingDao)dao).getByOwnerId(id);
    }
}
