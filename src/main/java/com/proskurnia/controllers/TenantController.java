package com.proskurnia.controllers;

import com.proskurnia.VOs.PersonVO;
import com.proskurnia.services.PersonService;
import com.proskurnia.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.sql.SQLException;

/**
 * Created by D on 13.03.2017.
 */
@Controller
@RequestMapping("/owners")
public class TenantController {

    @Autowired
    PersonService personService;

    @Autowired
    UtilsService utilsService;

    @RequestMapping
    public String allOwners(Model model) {
        model.addAttribute("list", personService.getAllTenants());
        return "tenants/list";
    }

    @GetMapping("/{id}")
    public String editOwner(Model model, @PathVariable int id) {
        model.addAttribute("titles", utilsService.getPersonTitles());
        if (id == 0) {
            model.addAttribute("object", new PersonVO());
        } else {
            model.addAttribute("object", personService.getById(id));
        }
        return "tenants/form";
    }

    @PostMapping("/save")
    public String save(@Valid PersonVO object, BindingResult bindingResult, Model model) throws SQLException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("titles", utilsService.getPersonTitles());
            return "tenants/form";
        } else {
            try {
                if (object.getId() == 0) {
                    personService.create(object);
                } else {
                    personService.update(object);
                }
            } catch (SQLException e) {
                bindingResult.addError(new ObjectError("object", e.getLocalizedMessage()));
                return save(object, bindingResult, model);
            }
            return "redirect:/tenants/" + object.getId();
        }
    }
}
