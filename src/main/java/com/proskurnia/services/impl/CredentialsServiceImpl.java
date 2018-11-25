package com.proskurnia.services.impl;

import com.proskurnia.dao.CredentialsDao;
import com.proskurnia.services.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by D on 08.04.2017.
 */
@Service
public class CredentialsServiceImpl implements CredentialsService {

    @Autowired
    CredentialsDao credentialsDao;

    @Override
    public void changeUsername(String username, String newUsername) {
        credentialsDao.changeUsername(username, newUsername);
    }

    @Override
    public void changePassword(String username, String password) {
        credentialsDao.changePassword(username, password);
    }
}
