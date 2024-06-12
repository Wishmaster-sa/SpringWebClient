/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ega.WebClientSpring.interfaces;

import com.ega.WebClientSpring.models.Answer;
import com.ega.WebClientSpring.models.Persona;

/**
 *
 * @author parallels
 */
public interface WebClientSpringInterface {
   public Answer showAll();
   public Answer findPersona(String rnokpp);
   public Answer getRequest(String url);
   public Answer checkup(String rnokpp);
   public Answer checkPersona(String rnokpp);
   public Answer savePersona(Persona persona);
   public Answer deletePersona(String rnokpp);
    
}
