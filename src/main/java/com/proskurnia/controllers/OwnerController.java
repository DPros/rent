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
public class OwnerController {

    @Autowired
    PersonService ownerService;

    @Autowired
    UtilsService utilsService;

    @RequestMapping
    public String allOwners(Model model) {
        model.addAttribute("owners", ownerService.getAllOwners());
        return "owners/list";
    }

    @GetMapping("/{ownerId}")
    public String editOwner(Model model, @PathVariable int ownerId) {
        model.addAttribute("titles", utilsService.getPersonTitles());
        if (ownerId == 0) {
            model.addAttribute(new PersonVO());
        } else {
            model.addAttribute(ownerService.getById(ownerId));
        }
        return "owners/form";
    }

    @PostMapping("/save")
    public String save(@Valid PersonVO object, BindingResult bindingResult, Model model) throws SQLException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("titles", utilsService.getPersonTitles());
            return "owners/form";
        } else {
            try {
                if (object.getId() == 0) {
                    object.setIsOwner(true);
                    ownerService.create(object);
                } else {
                    ownerService.update(object);
                }
            } catch (SQLException e) {
                bindingResult.addError(new ObjectError("object", e.getLocalizedMessage()));
                return save(object, bindingResult, model);
            }
            return "redirect:/owners/";
        }
    }
}
