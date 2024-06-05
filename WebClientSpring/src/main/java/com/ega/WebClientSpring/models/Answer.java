/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ega.WebClientSpring.models;

import lombok.Builder;
import lombok.Data;

/**
 * Цей клас містить модель відповіді на будь-який запит
 * status - вказує чи є запит успішним.
 * descr - текстовий опис результату відповіді. Якщо status = false, тут повинне бути опис причини
 * result - результат запита. Зазвичай результат надається в форматі JSON 
 */
@Data
@Builder
public class Answer {
    private Boolean status;
    private String descr;
    private String result;
}
