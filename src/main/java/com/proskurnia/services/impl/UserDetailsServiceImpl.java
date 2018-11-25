package com.proskurnia.services.impl;

import com.proskurnia.dao.CredentialsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by D on 08.04.2017.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    CredentialsDao credentialsDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return credentialsDao.getUser(username);
    }

    public static class User implements UserDetails {
        private String username;
        private String password;
        private Collection<Role> roles = new ArrayList<>(1);

        public User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            roles.add(new Role(role));
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return roles;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        public class Role implements GrantedAuthority {
            private String role;

            public Role(String role) {
                this.role = role;
            }

            @Override
            public String getAuthority() {
                return role;
            }
        }
    }
}
