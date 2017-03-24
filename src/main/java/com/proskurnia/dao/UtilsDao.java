package com.proskurnia.dao;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by D on 17.03.2017.
 */
public interface UtilsDao {

    Map<Integer, String> getPersonTitles();

    int createTitle(String value) throws Exception;

    void deleteTitle(int key) throws SQLException;

    void updateTitle(String value, int key) throws SQLException;

    Map<Integer, String> getServiceCompanyTypes();

    int createServiceCompanyType(String value) throws Exception;

    void deleteServiceCompanyType(int key) throws SQLException;

    void updateServiceCompanyType(String value, int key) throws SQLException;
}
