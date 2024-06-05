/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ega.WebClientSpring.controllers;

import com.ega.WebClientSpring.models.Answer;
import com.ega.WebClientSpring.services.WebClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author parallels
 */
@RestController
@AllArgsConstructor
@RequestMapping("persons")
public class WebClientController {
    private final WebClientService service;
    
    @GetMapping("/find/{rnokpp}")
    public Answer findPersona(@PathVariable String rnokpp){
        
        return service.findPersona(rnokpp);
        
    }
    
}
