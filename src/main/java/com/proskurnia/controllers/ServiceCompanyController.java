package com.proskurnia.controllers;

import com.proskurnia.VOs.ServiceCompanyVO;
import com.proskurnia.VOs.ServiceContractVO;
import com.proskurnia.services.ServiceCompanyService;
import com.proskurnia.services.ServiceContractService;
import com.proskurnia.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;

/**
 * Created by D on 22.03.2017.
 */
@Controller
@RequestMapping("/service-companies")
public class ServiceCompanyController {

    @Autowired
    ServiceCompanyService serviceCompanyService;

    @Autowired
    UtilsService utilsService;

    @GetMapping
    public String getAll(Model model, @RequestParam(required = false) Integer buildingId) {
        model.addAttribute("list", serviceCompanyService.getAll());
        return "service-companies/list";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable int id, Model model) {
        if (id == 0) {
            ServiceCompanyVO object = new ServiceCompanyVO();
            model.addAttribute(object);
            model.addAttribute("types", utilsService.getServiceCompanyTypes());
        } else {
            ServiceCompanyVO object = serviceCompanyService.getById(id);
            model.addAttribute(object);
        }
        return "service-contracts/form";
    }

    @PostMapping("/save")
    public String save(@Valid ServiceCompanyVO object, BindingResult bindingResult, Model model) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "service-contracts/form";
        } else {
            try {
                if (object.getId() == 0) {
                    serviceCompanyService.create(object);
                } else {
                    serviceCompanyService.update(object);
                }
            } catch (SQLException e) {
                bindingResult.addError(new ObjectError("object", e.getLocalizedMessage()));
                return save(object, bindingResult, model);
            }
            return "redirect:/service-companies";
        }
    }
}
