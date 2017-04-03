package com.proskurnia.controllers;

import com.proskurnia.VOs.ApartmentVO;
import com.proskurnia.services.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by dmpr0116 on 30.03.2017.
 */
@RestController
@RequestMapping("/rest")
public class RestControlller {

    @Autowired
    ApartmentService apartmentService;


}
