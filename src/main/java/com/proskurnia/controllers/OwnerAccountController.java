package com.proskurnia.controllers;

import com.proskurnia.VOs.OwnerAccountVO;
import com.proskurnia.services.BankService;
import com.proskurnia.services.OwnerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.sql.SQLException;

/**
 * Created by dmpr0116 on 27.03.2017.
 */
@Controller
@RequestMapping("/accounts")
public class OwnerAccountController {

    @Autowired
    OwnerAccountService ownerAccountService;

    @Autowired
    BankService bankService;

    @RequestMapping
    public String byOwner(Model model, @RequestParam(required = false) Integer ownerId) {
        model.addAttribute("list", ownerId == null ? ownerAccountService.getAll() : ownerAccountService.getByOwnerId(ownerId));
        model.addAttribute("id", ownerId);
        return "owner-accounts/list";
    }

    @RequestMapping("/{accountNumber}")
    public String byId(Model model, @PathVariable String accountNumber, @RequestParam(required = false) Integer ownerId) {
        if (ownerId != null) {
            OwnerAccountVO object = new OwnerAccountVO();
            object.setOwnerId(ownerId);
            model.addAttribute("isNew", "true");
            model.addAttribute(object);
            model.addAttribute("banks", bankService.getAll());
        } else {
            model.addAttribute(ownerAccountService.getById(accountNumber));
        }
        return "owner-accounts/form";
    }

    @PostMapping("/save")
    public String save(@Valid OwnerAccountVO object, BindingResult bindingResult, @RequestParam boolean isNew, Model model) throws SQLException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isNew", "true");
            model.addAttribute("banks", bankService.getAll());
            return "owner-accounts/form";
        } else {
            try {
                if (isNew) {
                    ownerAccountService.create(object);
                } else {
                    ownerAccountService.update(object);
                }
            } catch (SQLException e) {
                bindingResult.addError(new ObjectError("object", e.getLocalizedMessage()));
                return save(object, bindingResult, isNew, model);
            }
            return "redirect:/accounts?ownerId=" + object.getOwnerId();
        }
    }
}
