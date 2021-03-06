package com.proskurnia.services;

import com.proskurnia.VOs.OwnerAccountVO;
import com.proskurnia.VOs.PersonVO;

import java.util.List;

/**
 * Created by D on 21.03.2017.
 */
public interface OwnerAccountService extends GenericService<OwnerAccountVO, String> {

    List<OwnerAccountVO> getByOwnerId(int id);

    List<OwnerAccountVO> getByAccountId(String id);
}
