package com.proskurnia.controllers;

import com.proskurnia.VOs.RentingContractVO;
import com.proskurnia.services.BuildingService;
import com.proskurnia.services.RentingContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by dmpr0116 on 30.03.2017.
 */
@Controller
@RequestMapping("/renting-contracts")
public class RentingContractController {

    @Autowired
    RentingContractService rentingContractService;

    @Autowired
    BuildingService buildingService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("list", rentingContractService.getAll());
        return "renting-contracts/list";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable int id,
                       @RequestParam(required = false) Integer tenantId,
                       @RequestParam(required = false) String tenantName,
                       Model model) {
        if (id == 0) {
            RentingContractVO object = new RentingContractVO();
            if (tenantId != null) {
                object.setTenantId(tenantId);
                object.setTenantName(tenantName);
            }
            model.addAttribute(object);
            model.addAttribute("buildings", buildingService.getBuildingsWithEmptyApartments());
        } else {
            RentingContractVO object = rentingContractService.getById(id);
            model.addAttribute(object);
        }
        return "renting-contracts/form";
    }

    @PostMapping("/end-contract")
    public String endContract(@RequestParam int id) {
//        rentingContractService.
        return "redirect:renting-contracts/" + id;
    }

    @PostMapping("/return-deposit")
    public String returnDeposit(@RequestParam int id) {

        return "redirect:renting-contracts/" + id;
    }

    @PostMapping("/save")
    public String save(@Valid RentingContractVO object, BindingResult bindingResult, Model model) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "renting-contracts/form";
        } else {
            try {
                if (object.getId() == 0) {
                    rentingContractService.create(object);
                } else {
                    rentingContractService.update(object);
                }
            } catch (SQLException e) {
                bindingResult.addError(new ObjectError("object", e.getLocalizedMessage()));
                return save(object, bindingResult, model);
            }
            return "redirect:/renting-contracts/" + object.getId();
        }
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(java.sql.Timestamp.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(text.isEmpty() ? null : new Timestamp(Long.parseLong(text)));
            }
        });
    }
}
