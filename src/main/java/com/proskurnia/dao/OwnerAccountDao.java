package com.proskurnia.dao;

import com.proskurnia.VOs.OwnerAccountVO;

import java.util.List;

/**
 * Created by D on 21.03.2017.
 */
public interface OwnerAccountDao extends Dao<OwnerAccountVO, String> {

    List<OwnerAccountVO> getByOwnerId(int id);
}
