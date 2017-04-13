package com.proskurnia.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
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
            object.setStartDate(new Timestamp(System.currentTimeMillis()));
            model.addAttribute(object);
            model.addAttribute("buildings", buildingService.getBuildingsWithEmptyApartments(object.getStartDate()));
        } else {
            RentingContractVO object = rentingContractService.getById(id);
            model.addAttribute(object);
        }
        return "renting-contracts/form";
    }

    @PostMapping("/end-contract/{id}")
    public String endContract(@PathVariable int id, @RequestParam long actualEndDate) {
        rentingContractService.endContract(new Timestamp(actualEndDate), id);
        return "redirect:/renting-contracts/" + id;
    }

    @PostMapping("/return-deposit/{id}")
    public String returnDeposit(@PathVariable int id, @RequestParam BigDecimal deposit, @RequestParam long returnDepositDate) {
        try {
            rentingContractService.returnDeposit(id, deposit, new Timestamp(returnDepositDate));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/renting-contracts/" + id;
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

    @GetMapping("/json")
    public void getAll(@RequestParam(required = false) Integer buildingId, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(buildingId == null ? rentingContractService.getAll() : rentingContractService.getByBuildingId(buildingId)));
    }

    private static ObjectMapper mapper = new ObjectMapper();
}
