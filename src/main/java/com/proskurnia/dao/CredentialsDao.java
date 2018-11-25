package com.proskurnia.dao;

import com.proskurnia.services.impl.UserDetailsServiceImpl;

/**
 * Created by D on 08.04.2017.
 */
public interface CredentialsDao {
    UserDetailsServiceImpl.User getUser(String username);
    void changeUsername(String username, String newUsername);
    void changePassword(String username, String password);
}
