package com.proskurnia.services.impl;

import com.proskurnia.dao.UtilsDao;
import com.proskurnia.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by D on 16.03.2017.
 */
@Service
public class UtilsServiceImpl implements UtilsService {

    @Autowired
    UtilsDao utilsDao;

    public Map<Integer, String> getPersonTitles() {
        return utilsDao.getPersonTitles();
    }

    public int createPersonTitle(String title) throws Exception {
        if (title == null) throw new IllegalArgumentException("null person title to save");
        return utilsDao.createTitle(title);
    }

    @Override
    public void updateTitle(String newTitle, int key) throws SQLException {
        utilsDao.updateTitle(newTitle, key);
    }

    @Override
    public void deleteTitle(int key) throws SQLException {
        utilsDao.deleteTitle(key);
    }

    @Override
    public Map<Integer, String> getServiceCompanyTypes() {
        return utilsDao.getServiceCompanyTypes();
    }

    @Override
    public int createServiceCompanyType(String value) throws Exception {
        return utilsDao.createServiceCompanyType(value);
    }

    @Override
    public void updateServiceCompanyType(String value, int key) throws SQLException {
        utilsDao.updateServiceCompanyType(value, key);
    }

    @Override
    public void deleteServiceCompanyType(int key) throws SQLException {
        utilsDao.deleteServiceCompanyType(key);
    }
}
