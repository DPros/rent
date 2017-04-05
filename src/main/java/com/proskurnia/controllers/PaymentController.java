package com.proskurnia.controllers;

import com.proskurnia.VOs.CreditPaymentVO;
import com.proskurnia.VOs.DebitPaymentVO;
import com.proskurnia.VOs.EquipmentVO;
import com.proskurnia.VOs.Payment;
import com.proskurnia.services.BuildingService;
import com.proskurnia.services.PaymentService;
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
 * Created by D on 29.03.2017.
 */
@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    RentingContractService rentingContractService;

    @Autowired
    BuildingService buildingService;

    @RequestMapping("/debit/{buildingId}")
    public String debit(@PathVariable int buildingId, @RequestParam(required = false) Integer type, Model model) {
        DebitPaymentVO object = new DebitPaymentVO();
        if (buildingId != 0) {
            model.addAttribute("buildingId", buildingId);
        }
        if (type != null) {
            object.setType(Payment.PaymentType.valueOf(type));
        }
        model.addAttribute(object);
        model.addAttribute("buildings", buildingService.getAll());
        return "payments/debit-form";
    }

    @PostMapping("/save-debit")
    public String saveDebit(@Valid DebitPaymentVO object, BindingResult bindingResult, @RequestParam(required = false) Integer selectedBuildingId, @RequestParam(required = false) Integer reasonId, Model model) throws SQLException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("buildings", buildingService.getAll());
            model.addAttribute("buildingId", selectedBuildingId);
            model.addAttribute("reasonId", reasonId);
            return "payments/debit-form";
        } else {
            try {
                paymentService.create(object);
            } catch (SQLException e) {
                bindingResult.addError(new ObjectError("object", e.getLocalizedMessage()));
                return saveDebit(object, bindingResult, selectedBuildingId, reasonId, model);
            }
            return "redirect:/accounts/" + object.getAccountNumber();
        }
    }

    @RequestMapping("/credit/{id}")
    public String credit(@PathVariable int id, @RequestParam(required = false) String address, @RequestParam(required = false) String roomNumber, Model model) {
        CreditPaymentVO object = new CreditPaymentVO();
        if (id == 0) {
            model.addAttribute("contracts", rentingContractService.getActiveContracts());
        } else {
            object.setContractId(id);
            object.setBuildingAddress(address);
            model.addAttribute("room", roomNumber);
        }
        model.addAttribute(object);
        return "payments/credit-form";
    }

    @PostMapping("/save-credit")
    public String saveCredit(@Valid CreditPaymentVO object, BindingResult bindingResult, Model model) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "payments/credit-form";
        } else {
            try {
                paymentService.create(object);
            } catch (SQLException e) {
                bindingResult.addError(new ObjectError("object", e.getLocalizedMessage()));
                return saveCredit(object, bindingResult, model);
            }
            return "redirect:/renting-contracts/" + object.getContractId();
        }
    }

    @InitBinder
    protected void timestampBinder(WebDataBinder binder) {
        binder.registerCustomEditor(java.sql.Timestamp.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(text.isEmpty() ? null : new Timestamp(Long.parseLong(text)));
            }
        });
    }
}
