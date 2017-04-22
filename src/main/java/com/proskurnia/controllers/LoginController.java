package com.proskurnia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by D on 08.04.2017.
 */
@Controller
public class LoginController {

    @Autowired
    LocaleResolver localeResolver;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam(required = false) String lang) {
        if (lang != null) {
            localeResolver.setLocale(request, response, new Locale(lang));
        }
        return "login";
    }
}
