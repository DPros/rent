package com.proskurnia.dao;

import com.proskurnia.VOs.ServiceCompanyVO;

import java.util.List;

/**
 * Created by D on 25.03.2017.
 */
public interface ServiceCompanyDao extends Dao<ServiceCompanyVO, Integer> {

    List<ServiceCompanyVO> getByType(int typeId);
}
