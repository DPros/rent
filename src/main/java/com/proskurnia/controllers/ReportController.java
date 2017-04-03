package com.proskurnia.controllers;

import com.proskurnia.dao.MoneyFlowJdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dmpr0116 on 03.04.2017.
 */
@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    MoneyFlowJdbcUtils moneyFlowJdbcUtils;

    @RequestMapping("contract/{id}")
    public String contractReport(Model model, @PathVariable int id){
        model.addAttribute("payments", moneyFlowJdbcUtils.getRentingContractReport(id));
        return "reports/contract";
    }

    @RequestMapping("building/{id}")
    public String buildingReport(Model model, @PathVariable int id){
        model.addAttribute("payments", moneyFlowJdbcUtils.getBuildingReport(id));
        return "reports/building";
    }

    @RequestMapping("account/{id}")
    public String accountReport(Model model, @PathVariable String id){
        model.addAttribute("payments", moneyFlowJdbcUtils.getOwnerAccountReport(id));
        return "reports/account";
    }
}
