/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ega.WebClientSpring.services;

import java.time.LocalDate;

/**
 *
 * @author parallels
 */
public abstract class ExTools {
    
    public LocalDate getDateFromString(String date){
        if(date.isEmpty()){
            return LocalDate.ofYearDay(0, 1);
        }else
        {
            return LocalDate.parse(date);
        }
        
    }
    
}
