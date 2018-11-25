package com.proskurnia.services.impl;

import com.proskurnia.VOs.OwnerAccountVO;
import com.proskurnia.dao.OwnerAccountDao;
import com.proskurnia.services.OwnerAccountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by D on 21.03.2017.
 */
@Service
public class OwnerAccountServiceImpl extends GenericServiceImpl<OwnerAccountVO, String> implements OwnerAccountService {
    @Override
    public List<OwnerAccountVO> getByOwnerId(int id) {
        return ((OwnerAccountDao) dao).getByOwnerId(id);
    }

    @Override
    public List<OwnerAccountVO> getByAccountId(String id) {
        return ((OwnerAccountDao) dao).getByAccountId(id);
    }
}
