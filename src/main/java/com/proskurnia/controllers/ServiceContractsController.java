package com.proskurnia.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proskurnia.VOs.ServiceCompanyVO;
import com.proskurnia.VOs.ServiceContractVO;
import com.proskurnia.services.BuildingService;
import com.proskurnia.services.ServiceCompanyService;
import com.proskurnia.services.ServiceContractService;
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
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by D on 22.03.2017.
 */
@Controller
@RequestMapping("/service-contracts")
public class ServiceContractsController {

    @Autowired
    ServiceContractService serviceContractService;

    @Autowired
    ServiceCompanyService serviceCompanyService;

    @Autowired
    BuildingService buildingService;

    @GetMapping
    public String getAll(Model model, @RequestParam(required = false) Integer buildingId) {
        model.addAttribute("list",
                buildingId != null ? serviceContractService.getByBuildingId(buildingId) :
                        serviceContractService.getAll());
        model.addAttribute("id", buildingId);
        return "service-contracts/list";
    }

    @GetMapping("/{id}")
    public String getBuilding(@PathVariable int id,
                              @RequestParam(required = false) Integer buildingId,
                              Model model) {
        if (id == 0) {
            ServiceContractVO object = new ServiceContractVO();
            object.setBuildingId(buildingId);
            model.addAttribute(object);
            model.addAttribute("buildings", buildingService.getAll());
            model.addAttribute("companies", serviceCompanyService.getAll());
        } else {
            ServiceContractVO object = serviceContractService.getById(id);
            model.addAttribute(object);
            ServiceCompanyVO company = new ServiceCompanyVO();
            company.setId(object.getServiceCompanyId());
            company.setName(object.getCompanyName());
            model.addAttribute("companies", new ServiceCompanyVO[]{company});
        }
        return "service-contracts/form";
    }

    @PostMapping("/end-contract/{id}")
    public String endContract(@PathVariable int id, @RequestParam long endDate, @RequestParam int buildingId) {
        serviceContractService.endContract(new Timestamp(endDate), id);
        return "redirect:/service-contracts?buildingId=" + buildingId;
    }

    @PostMapping("/save")
    public String save(@Valid ServiceContractVO object, BindingResult bindingResult, Model model) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "service-contracts/form";
        } else {
            try {
                if (object.getId() == 0) {
                    serviceContractService.create(object);
                } else {
                    serviceContractService.update(object);
                }
            } catch (SQLException e) {
                bindingResult.addError(new ObjectError("object", e.getLocalizedMessage()));
                return save(object, bindingResult, model);
            }
            return "redirect:/service-contracts?buildingId=" + object.getBuildingId();
        }
    }

    @GetMapping("/json")
    public void getAll(@RequestParam(required = false) Integer buildingId, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(buildingId == null ? serviceContractService.getAll() : serviceContractService.getByBuildingId(buildingId)));
    }

    private static ObjectMapper mapper = new ObjectMapper();

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
