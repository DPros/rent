package com.proskurnia.controllers;

import com.proskurnia.VOs.EquipmentVO;
import com.proskurnia.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;

/**
 * Created by dmpr0116 on 29.03.2017.
 */
@Controller
@RequestMapping("/equipments")
public class EquipmentController {

    @Autowired
    EquipmentService equipmentService;

    @GetMapping
    public String all(Model model, @RequestParam(required = false) Integer buildingId,
                      @RequestParam(required = false) Integer contractId) {
        model.addAttribute("list",
                buildingId != null ? equipmentService.getByBuildingId(buildingId) :
                        contractId != null ? equipmentService.getByServiceContractId(contractId) :
                                equipmentService.getAll());
        return "equipments/list";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable String id, @RequestParam(required = false) int contractId, Model model) {
        if (id.equals("0")) {
            EquipmentVO object = new EquipmentVO();
            object.setServiceContractId(contractId);
            model.addAttribute("object", object);
        } else {
            model.addAttribute("object", equipmentService.getById(id));
        }
        return "equipments/form";
    }

    @PostMapping("/save")
    public String save(@Valid EquipmentVO object, BindingResult bindingResult, Model model) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "equipments/form";
        } else {
            try {
                if (object.getBuildingId() == 0) {
                    equipmentService.create(object);
                } else {
                    equipmentService.update(object);
                }
            } catch (SQLException e) {
                bindingResult.addError(new ObjectError("object", e.getLocalizedMessage()));
                return save(object, bindingResult, model);
            }
            return "redirect:/equipments?contractId=" + object.getServiceContractId();
        }
    }
}
