package com.proskurnia.dao;

import com.proskurnia.VOs.PersonVO;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by D on 14.03.2017.
 */
@Repository
public interface PersonDao extends DAO<PersonVO, Integer>{

    Collection<PersonVO> getTenants();

    Collection<PersonVO> getOwners();
}
