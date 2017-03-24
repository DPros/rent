package com.proskurnia.services;

import com.proskurnia.VOs.PersonVO;

import java.util.Collection;

/**
 * Created by D on 13.03.2017.
 */
public interface PersonService extends GenericService<PersonVO, Integer> {

    Collection<PersonVO> getAllOwners();

    Collection<PersonVO> getAllTenants();
}
