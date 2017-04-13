package com.proskurnia.controllers;

import com.proskurnia.VOs.BuildingVO;
import com.proskurnia.VOs.OwnerVO;
import com.proskurnia.VOs.PersonVO;
import com.proskurnia.services.BuildingService;
import com.proskurnia.services.OwnerAccountService;
import com.proskurnia.services.PersonService;
import org.springframework.beans.PropertyValuesEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by dmpr0116 on 28.03.2017.
 */
@Controller
@RequestMapping("buildings")
public class BuildingController {

    @Autowired
    BuildingService buildingService;

    @Autowired
    OwnerAccountService ownerAccountService;

    @Autowired
    PersonService personService;

    @GetMapping
    public String all(Model model, @RequestParam(required = false) Integer ownerId) {
        model.addAttribute("list", ownerId != null ? buildingService.getByOwnerId(ownerId) : buildingService.getAll());
        if (ownerId == null) {
            model.addAttribute("list", buildingService.getAll());
        } else {
            model.addAttribute("id", ownerId);
            model.addAttribute("list", buildingService.getByOwnerId(ownerId));
        }
        return "buildings/list";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable int id,
                       @RequestParam(required = false) Integer ownerId, Model model) {
        if (id == 0) {
            PersonVO owner = personService.getById(ownerId);
            BuildingVO object = new BuildingVO();
            object.setOwnerId(owner.getId());
            object.setOwnerName(owner.getName());
            model.addAttribute(object);
            model.addAttribute("accounts", ownerAccountService.getByOwnerId(ownerId));
        } else {
            BuildingVO object = buildingService.getById(id);
            model.addAttribute(object);
            model.addAttribute("accounts", ownerAccountService.getByOwnerId(object.getOwnerId()));
        }
        return "buildings/form";
    }

    @PostMapping("/save")
    public String save(@Valid BuildingVO object, BindingResult bindingResult, Model model) throws SQLException {
         if (bindingResult.hasErrors()) {
            model.addAttribute("accounts", ownerAccountService.getByAccountId(object.getOwnerAccountId()));
            return "buildings/form";
        } else {
            try {
                if (object.getId() == 0) {
                    buildingService.create(object);
                } else {
                    buildingService.update(object);
                }
            } catch (SQLException e) {
                bindingResult.addError(new ObjectError("object", e.getLocalizedMessage()));
                return save(object, bindingResult, model);
            }
            return "redirect:/buildings?ownerId=" + object.getOwnerId();
        }
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(java.sql.Timestamp.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                super.setValue(value == null || value.isEmpty() ? null : Timestamp.valueOf(value));
            }
        });
    }
}
