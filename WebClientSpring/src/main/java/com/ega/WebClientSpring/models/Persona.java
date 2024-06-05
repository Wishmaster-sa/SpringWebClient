/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ega.WebClientSpring.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

//Click https://projectlombok.org/features/ to view all features of lombok
import lombok.Builder;
import lombok.Data;
import org.json.JSONObject;

/**
 *
 * @author sa
 */
//Ця анотація надає можливість автоматично формувати конструктор класу, та реалізує встановлення та отримання всіх членів класу.
@Data
// The @Builder annotation produces complex builder APIs for your classes.
// Builder lets you automatically produce the code required to have your class be instantiable with code
@Builder

//Анотація @Entity говорить SPRINGу що цю сутність треба включити до бази данних. Так як у нас анотація відноситься до всього класу - це означає що на базі класу буде створена таблиця в БД
//Докладніше https://www.baeldung.com/jpa-entities
//@Entity

//Задає назву таблиці. Якщо ця анотація не існує, то назва таблиці буде дублювати назву класа.
//@Table(name="Persona")
public class Persona {
    //Ця анотація вказує, що наступний член класу буде виконувати роль ідентіфікатора в БД    
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String pasport;
    private String unzr;
    private String rnokpp;
    private int age;
    private Boolean isChecked;
    private LocalDateTime CheckedRequest;
    
    
    public int getAge(){
        if(birthDate == null){
            return 0;
        }else return LocalDate.now().getYear() - birthDate.getYear();
    }
    
    public JSONObject toJSON(){
        JSONObject jsData=new JSONObject();
        //Persona persona = result.get(i);
        jsData.put("id",            (getId()==null) ? 0:getId());
        jsData.put("firstName",     (getFirstName()== null) ? "" : getFirstName());
        jsData.put("lastName",      (getLastName()== null) ? "" : getLastName());
        jsData.put("unzr",          (getUnzr()== null) ? "" : getUnzr());
        jsData.put("rnokpp",        (getRnokpp()== null) ? "" : getRnokpp());
        jsData.put("pasport",       (getPasport()== null) ? "" : getPasport());
        jsData.put("age",           (getAge()== 0) ? 0 : getAge());
        jsData.put("birthDate",     (getBirthDate()== null) ? "" : getBirthDate());
        jsData.put("isChecked",     (getIsChecked()== null) ? false : getIsChecked());
        jsData.put("CheckedRequest",(getCheckedRequest()== null) ? "" : getCheckedRequest());
        
        return jsData;
    }

    public Persona fromJSON(JSONObject jsData){
        Persona persona = Persona.builder()
                .id(jsData.optLong("id"))
                .firstName(jsData.optString("firstName"))
                .lastName(jsData.optString("lastName"))
                .birthDate(LocalDate.parse(jsData.optString("birthDate")))
                .age(jsData.optInt("age"))
                .rnokpp(jsData.optString("rnokpp"))
                .pasport(jsData.optString("pasport"))
                .unzr(jsData.optString("unzr"))
                .isChecked(jsData.optBoolean("isChecked"))
                .CheckedRequest(LocalDateTime.parse(jsData.optString("CheckedRequest")))
                .build();
                
        return persona;
    }
}

