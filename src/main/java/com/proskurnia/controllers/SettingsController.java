package com.proskurnia.controllers;

import com.proskurnia.services.CredentialsService;
import com.proskurnia.services.PaymentService;
import com.proskurnia.services.UserDetailsServiceImpl;
import com.proskurnia.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by D on 16.03.2017.
 */
@Controller
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    UtilsService utilsService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    CredentialsService credentialsService;

    @RequestMapping
    public String render(Model model) {
        model.addAttribute("titles", utilsService.getPersonTitles());
        model.addAttribute("serviceCompanyTypes", utilsService.getServiceCompanyTypes());
        return "settings/all";
    }

    @PostMapping("/save-title")
    public void addTitle(@RequestParam String value, @RequestParam int key, HttpServletResponse response) throws Exception {
        if (key > 0) {
            utilsService.updateTitle(value, key);
        } else {
            key = utilsService.createPersonTitle(value);
        }
        response.getWriter().write("" + key);
    }

    @PostMapping("/delete-title")
    public void deleteTitle(@RequestParam int key, HttpServletResponse response) throws Exception {
        utilsService.deleteTitle(key);
        response.getWriter().write(key);
    }

    @PostMapping("/save-service-company-type")
    public void addServiceCompanyType(@RequestParam String value, @RequestParam int key, HttpServletResponse response) throws Exception {
        if (key > 0) {
            utilsService.updateServiceCompanyType(value, key);
        } else {
            key = utilsService.createServiceCompanyType(value);
        }
        response.getWriter().write("" + key);
    }

    @PostMapping("/delete-service-company-type")
    public void deleteServiceCompanyType(@RequestParam int key, HttpServletResponse response) throws Exception {
        utilsService.deleteServiceCompanyType(key);
        response.getWriter().write("" + key);
    }

    @GetMapping("/payments")
    public String contractReport(Model model) {
        model.addAttribute("payments", paymentService.getAll());
        return "settings/payments";
    }

    @PostMapping("/delete-payment/{id}")
    public void deletePayment(HttpServletResponse response, Model model, @PathVariable long id, @RequestParam boolean credit) throws IOException, SQLException {
        paymentService.delete(id, credit);
        response.getWriter().write("done");
    }

    @PostMapping("/change-username")
    public void changeUsername(HttpServletResponse response, @RequestParam String newValue) throws IOException, SQLException {
        UserDetailsServiceImpl.User user = (UserDetailsServiceImpl.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        credentialsService.changeUsername(user.getUsername(), newValue);
        user.setUsername(newValue);
        response.getWriter().write("done");
    }

    @PostMapping("/change-password")
    public void changePassword(HttpServletResponse response, @RequestParam String newValue) throws IOException, SQLException {
        UserDetailsServiceImpl.User user = (UserDetailsServiceImpl.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        credentialsService.changePassword(user.getUsername(), newValue);
        response.getWriter().write("done");
    }
}
