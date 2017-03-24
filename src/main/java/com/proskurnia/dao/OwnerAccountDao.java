package com.proskurnia.dao;

import com.proskurnia.VOs.OwnerAccountVO;
import com.proskurnia.VOs.PersonVO;

import java.util.List;

/**
 * Created by D on 21.03.2017.
 */
public interface OwnerAccountDao extends DAO<OwnerAccountVO, Integer> {

    List<OwnerAccountVO> getByOwner(PersonVO p);
}
