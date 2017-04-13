package com.proskurnia.services;

/**
 * Created by D on 08.04.2017.
 */
public interface CredentialsService {

    void changeUsername(String username, String newUsername);
    void changePassword(String username, String password);
}
