package com.proskurnia.services;

import com.proskurnia.VOs.PersonVO;
import com.proskurnia.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by D on 13.03.2017.
 */
@Service
public class PersonServiceImpl extends GenericServiceImpl<PersonVO, Integer> implements PersonService {

    @Autowired
    PersonDao dao;

    @Override
    public Collection<PersonVO> getAllOwners() {
        return dao.getOwners();
    }

    @Override
    public Collection<PersonVO> getAllTenants() {
        return dao.getTenants();
    }
}
