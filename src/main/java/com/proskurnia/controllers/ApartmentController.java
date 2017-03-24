package com.proskurnia.controllers;

import com.proskurnia.VOs.ApartmentVO;
import com.proskurnia.VOs.BuildingVO;
import com.proskurnia.dao.BuildingService;
import com.proskurnia.services.ApartmentService;
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
 * Created by D on 22.03.2017.
 */
@Controller
@RequestMapping("/buildings")
public class ApartmentController {

    @Autowired
    BuildingService buildingService;

    @Autowired
    ApartmentService apartmentService;

    @GetMapping("/")
    public String getAllBuildings(Model model) {
        model.addAttribute("buildings", buildingService.getAll());
        return "buildings/all";
    }

    @GetMapping("/{buildingId}")
    public String getBuilding(@PathVariable int buildingId, Model model) {
        if (buildingId == 0) {
            model.addAttribute("building", new BuildingVO());
        } else {
            model.addAttribute("building", buildingService.getById(buildingId));
        }
        return "buildings/form";
    }

    @PostMapping("/save")
    public String saveBuilding(@Valid BuildingVO building, BindingResult bindingResult, Model model) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "buildings/form";
        } else {
            try {
                if (building.getId() == 0) {
                    buildingService.create(building);
                } else {
                    buildingService.update(building);
                }
            } catch (SQLException e) {
                bindingResult.addError(new ObjectError("building", e.getLocalizedMessage()));
                return saveBuilding(building, bindingResult, model);
            }
            return "redirect:/buildings/" + building.getId() + "/";
        }
    }

    @GetMapping("/{buildingId}/")
    public String getApartmentsByBuilding(Model model, @PathVariable int buildingId) {
        model.addAttribute("apartments", apartmentService.getByBuildingId(buildingId));
        return "apartments/byBuilding";
    }

    @GetMapping("/{buildingId}/{apartmentId}")
    public String getBuilding(@PathVariable int buildingId, @PathVariable int apartmentId, Model model) {
        if (apartmentId == 0) {
            model.addAttribute("apartment", new ApartmentVO());
        } else {
            model.addAttribute("apartment", apartmentService.getById(apartmentId));
        }
        return "buildings/form";
    }

    @PostMapping("/save-apartment")
    public String saveApartment(@Valid ApartmentVO apartment, BindingResult bindingResult, Model model) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "apartments/form";
        } else {
            try {
                if (apartment.getId() == 0) {
                    apartmentService.create(apartment);
                } else {
                    apartmentService.update(apartment);
                }
            } catch (SQLException e) {
                bindingResult.addError(new ObjectError("apartment", e.getLocalizedMessage()));
                return saveApartment(apartment, bindingResult, model);
            }
            return "redirect:/buildings/" + apartment.getBuildingId() + "/";
        }
    }
}
