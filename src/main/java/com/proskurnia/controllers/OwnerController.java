package com.proskurnia.controllers;

import com.proskurnia.VOs.PersonVO;
import com.proskurnia.services.PersonService;
import com.proskurnia.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping("/")
    public String allOwners(Model model) {
        model.addAttribute("owners", ownerService.getAllOwners());
        return "owners/all";
    }

    @GetMapping("/{ownerId}")
    public String editOwner(Model model, @PathVariable int ownerId) {
        model.addAttribute("titles", utilsService.getPersonTitles());
        if (ownerId == 0) {
            model.addAttribute("personVO", new PersonVO());
        } else {
            model.addAttribute("personVO", ownerService.getById(ownerId));
        }
        return "owners/form";
    }

    @PostMapping("/save")
    public String save(@Valid PersonVO personVO, BindingResult bindingResult, Model model) throws SQLException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("titles", utilsService.getPersonTitles());
            return "owners/form";
        } else {
            try {
                if (personVO.getId() == 0) {
                    personVO.setOwner(true);
                    ownerService.create(personVO);
                } else {
                    ownerService.update(personVO);
                }
            } catch (SQLException e) {
                bindingResult.addError(new ObjectError("personVO", e.getLocalizedMessage()));
                return save(personVO, bindingResult, model);
            }
            return "redirect:/owners/";
        }
    }
}
