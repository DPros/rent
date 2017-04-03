package com.proskurnia.controllers;

import com.proskurnia.VOs.BankVO;
import com.proskurnia.services.BankService;
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
 * Created by dmpr0116 on 29.03.2017.
 */
@Controller
@RequestMapping("banks")
public class BankController {

    @Autowired
    BankService bankService;

    @GetMapping
    public String all(Model model) {
        model.addAttribute("list", bankService.getAll());
        return "banks/list";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable int id, Model model) {
        if (id == 0) {
            model.addAttribute(new BankVO());
        } else {
            model.addAttribute(bankService.getById(id));
        }
        return "banks/form";
    }

    @PostMapping("/save")
    public String save(@Valid BankVO object, BindingResult bindingResult, Model model) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "banks/form";
        } else {
            try {
                if (object.getId() == 0) {
                    bankService.create(object);
                } else {
                    bankService.update(object);
                }
            } catch (SQLException e) {
                bindingResult.addError(new ObjectError("object", e.getLocalizedMessage()));
                return save(object, bindingResult, model);
            }
            return "redirect:/banks";
        }
    }
}
