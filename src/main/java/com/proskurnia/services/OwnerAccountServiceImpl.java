package com.proskurnia.services;

import com.proskurnia.VOs.OwnerAccountVO;
import com.proskurnia.VOs.PersonVO;
import com.proskurnia.dao.OwnerAccountDao;

import java.util.List;

/**
 * Created by D on 21.03.2017.
 */
public class OwnerAccountServiceImpl extends GenericServiceImpl<OwnerAccountVO, Integer> implements OwnerAccountService {
    @Override
    public List<OwnerAccountVO> getByOwner(PersonVO p) {
        return ((OwnerAccountDao) dao).getByOwner(p);
    }
}
