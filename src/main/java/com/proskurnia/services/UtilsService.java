package com.proskurnia.services;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by D on 17.03.2017.
 */
public interface UtilsService {

    Map<Integer, String> getPersonTitles();

    int createPersonTitle(String value) throws Exception;

    void updateTitle(String value, int key) throws SQLException;

    void deleteTitle(int key) throws SQLException;

    Map<Integer, String> getServiceCompanyTypes();

    int createServiceCompanyType(String value) throws Exception;

    void updateServiceCompanyType(String value, int key) throws SQLException;

    void deleteServiceCompanyType(int key) throws SQLException;
}
