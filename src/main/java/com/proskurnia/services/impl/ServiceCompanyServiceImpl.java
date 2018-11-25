package com.proskurnia.services.impl;

import com.proskurnia.VOs.ServiceCompanyVO;
import com.proskurnia.dao.ServiceCompanyDao;
import com.proskurnia.dao.ServiceContractDao;
import com.proskurnia.services.ServiceCompanyService;
import org.springframework.stereotype.Service;

/**
 * Created by D on 25.03.2017.
 */
@Service
public class ServiceCompanyServiceImpl extends GenericServiceImpl<ServiceCompanyVO, Integer, ServiceCompanyDao> implements ServiceCompanyService {
}
