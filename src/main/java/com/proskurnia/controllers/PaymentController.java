package com.proskurnia.controllers;

import com.proskurnia.VOs.CreditPaymentVO;
import com.proskurnia.VOs.DebitPaymentVO;
import com.proskurnia.VOs.EquipmentVO;
import com.proskurnia.services.PaymentService;
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
 * Created by D on 29.03.2017.
 */
@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @RequestMapping("debit/{id}")
    public String debit(@PathVariable int reasonId, @RequestParam String address, @RequestParam String description, Model model) {
        DebitPaymentVO object = new DebitPaymentVO();
        object.setReasonId(reasonId);
        object.setBuildingAddress(address);
        object.setDescription(description);
        model.addAttribute(object);
        return "payments/debit-form";
    }

    @PostMapping("/save-debit")
    public String saveDebit(@Valid DebitPaymentVO object, BindingResult bindingResult, Model model) throws SQLException {
        if (bindingResult.hasErrors()) {
            return "payments/credit-form";
        } else {
            try {
                paymentService.create(object);
            } catch (SQLException e) {
                bindingResult.addError(new ObjectError("object", e.getLocalizedMessage()));
                return saveDebit(object, bindingResult, model);
            }
            return "redirect:/accounts/" + object.getAccountNumber();
        }
    }

    @RequestMapping("credit/{id}")
    public String credit(@PathVariable int id, @RequestParam String address, @RequestParam String roomNumber, Model model) {
        CreditPaymentVO object = new CreditPaymentVO();
        object.setContractId(id);
        object.setBuildingAddress(address);
        model.addAttribute("room", roomNumber);
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

}
