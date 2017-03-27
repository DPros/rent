package com.proskurnia.services;

import com.proskurnia.VOs.OwnerAccountVO;
import com.proskurnia.VOs.PersonVO;
import com.proskurnia.dao.OwnerAccountDao;

import java.util.List;

/**
 * Created by D on 21.03.2017.
 */
public class OwnerAccountServiceImpl extends GenericServiceImpl<OwnerAccountVO, String> implements OwnerAccountService {
    @Override
    public List<OwnerAccountVO> getByOwnerId(int id) {
        return ((OwnerAccountDao) dao).getByOwnerId(id);
    }
}
