package com.budgetpartner.APP.controller;


import com.budgetpartner.APP.entity.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MiembroController {

    @GetMapping
    public Miembro getMiembroByUsuario (int userId){
         return new Miembro();

    }

}
