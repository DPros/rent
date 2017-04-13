package com.proskurnia.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proskurnia.VOs.ApartmentVO;
import com.proskurnia.services.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by D on 22.03.2017.
 */
@Controller
@RequestMapping("/apartments")
public class ApartmentController implements Serializable{

    @Autowired
    ApartmentService apartmentService;

    @GetMapping
    public String getAll(Model model, @RequestParam(required = false) Integer buildingId) {
        model.addAttribute("list", buildingId == null ? apartmentService.getAll() : apartmentService.getByBuildingId(buildingId));
        model.addAttribute("id", buildingId);
        return "apartments/list";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable int id,
                              @RequestParam(required = false) Integer buildingId,
                              @RequestParam(required = false) String address, Model model) {
        if (id == 0) {
            ApartmentVO object = new ApartmentVO();
            object.setBuildingId(buildingId);
            object.setAddress(address);
            model.addAttribute(object);
        } else {
            model.addAttribute(apartmentService.getById(id));
        }
        return "apartments/form";
    }

    @PostMapping("/save")
    public String save(@Valid ApartmentVO object, BindingResult bindingResult, Model model) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "apartments/form";
        } else {
            try {
                if (object.getId() == 0) {
                    apartmentService.create(object);
                } else {
                    apartmentService.update(object);
                }
            } catch (SQLException e) {
                bindingResult.addError(new ObjectError("object", e.getLocalizedMessage()));
                return save(object, bindingResult, model);
            }
            return "redirect:/apartments?buildingId=" + object.getBuildingId();
        }
    }

    @GetMapping("/empty-json")
    public void getEmpty(@RequestParam(required = false) Integer buildingId, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(buildingId == null ? apartmentService.getAll() : apartmentService.getByBuildingId(buildingId)));
    }

    @GetMapping("/json")
    public void getAll(@RequestParam(required = false) Integer buildingId, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(buildingId == null ? apartmentService.getAll() : apartmentService.getByBuildingId(buildingId)));
    }

    private static ObjectMapper mapper = new ObjectMapper();
}
