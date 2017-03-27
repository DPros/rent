package com.proskurnia.controllers;

import com.proskurnia.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by D on 16.03.2017.
 */
@Controller
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    UtilsService utilsService;

    @RequestMapping
    public String render(Model model) {
        model.addAttribute("titles", utilsService.getPersonTitles());
        model.addAttribute("serviceCompanyTypes", utilsService.getServiceCompanyTypes());
        return "settings/all";
    }

    @PostMapping("/save-title")
    public void addTitle(@RequestParam String value, @RequestParam int key, HttpServletResponse response) throws Exception {
        response.setContentType("text/html");
        if (key > 0) {
            utilsService.updateTitle(value, key);
        } else {
            key = utilsService.createPersonTitle(value);
        }
        response.getWriter().write(key);
    }

    @PostMapping("/delete-title")
    public void deleteTitle(@RequestParam int key, HttpServletResponse response) throws Exception {
        utilsService.deleteTitle(key);
        response.getWriter().write(key);
    }

    @PostMapping("/save-service-company-type")
    public void addServiceCompanyType(@RequestParam String value, @RequestParam int key, HttpServletResponse response) throws Exception {
        response.setContentType("text/html");
        if (key > 0) {
            utilsService.updateServiceCompanyType(value, key);
        } else {
            key = utilsService.createServiceCompanyType(value);
        }
        response.getWriter().write(key);
    }

    @PostMapping("/delete-service-company-type")
    public void deleteServiceCompanyType(@RequestParam int key, HttpServletResponse response) throws Exception {
        utilsService.deleteServiceCompanyType(key);
        response.getWriter().write(key);
    }
}
