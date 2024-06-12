/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ega.WebClientSpring.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//Click https://projectlombok.org/features/ to view all features of lombok
import lombok.Builder;
import lombok.Data;
import org.json.JSONArray;
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
    private String patronymic;
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
        jsData.put("patronymic",      (getPatronymic()== null) ? "" : getPatronymic());
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
                .patronymic(jsData.optString("patronymic"))
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
    
    public static JSONArray listToJSON(List <Persona> personsList){
        JSONArray persons = new JSONArray(personsList);
        
        return persons;
    } 
    
    public static Answer isValid(Persona persona){
        Answer ret = Answer.builder().status(true).build();
        
        ret = isValidStr(persona.getFirstName(),"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-'абвгдеєжзійклмнопрстуфхцчшщиьюяАБВГДЕЄЖЗІЙКЛМНОПРСТУФХЦЧШЩИЬЮЯ",0);
        if(!ret.getStatus())return ret;
        
        ret = isValidStr(persona.getLastName(),"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-'абвгдеєжзійклмнопрстуфхцчшщиьюяАБВГДЕЄЖЗІЙКЛМНОПРСТУФХЦЧШЩИЬЮЯ",0);
        if(!ret.getStatus())return ret;

        ret = isValidStr(persona.getPatronymic(),"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-'абвгдеєжзійклмнопрстуфхцчшщиьюяАБВГДЕЄЖЗІЙКЛМНОПРСТУФХЦЧШЩИЬЮЯ",0);
        if(!ret.getStatus())return ret;
        
        
        ret = isValidStr(persona.getRnokpp(),"0123456789",10);
        if(!ret.getStatus())return ret;

        ret = isValidPasport(persona.getPasport());
        if(!ret.getStatus())return ret;

        ret = isValidUnzr(persona.getUnzr());
        
        return ret;
    }

    private static Answer isValidPasport(String checkStr){
        Answer ret = Answer.builder().status(true).build();

        if((checkStr==null)||(checkStr==""))return ret;
        
        switch(checkStr.length()){
            case 9-> {
                String allowedStr = "0123456789";

                for(int i=0;i < checkStr.length();i++){
                    if(!allowedStr.contains(checkStr.subSequence(i, i+1))){
                        ret.setStatus(false);
                        ret.setDescr(checkStr+" містить неприпустимі символи! '"+checkStr.subSequence(i, i+1)+"'");
                        return ret;
                    }
                }
            }
            case 8-> {
                String allowedStr = "abcdefghijklmnopqrstuvwxyz";

                for(int i=0;i < 2;i++){
                    if(!allowedStr.contains(checkStr.toLowerCase().subSequence(i, i+1))){
                        ret.setStatus(false);
                        ret.setDescr(checkStr+" містить неприпустимі символи! '"+checkStr.subSequence(i, i+1)+"'");
                        return ret;
                    }
                }
                
                allowedStr = "0123456789";
                
                for(int i=2;i < checkStr.length();i++){
                    if(!allowedStr.contains(checkStr.subSequence(i, i+1))){
                        ret.setStatus(false);
                        ret.setDescr(checkStr+" містить неприпустимі символи! '"+checkStr.subSequence(i, i+1)+"'");
                        return ret;
                    }
                }
            }
            default -> {
                ret.setStatus(false);
                ret.setDescr(checkStr+" містить неприпустиму кількість символів '"+checkStr.length()+"'");
            }
            
        }
        
        return ret;
    }
    
    private static Answer isValidUnzr(String checkStr){
        Answer ret = Answer.builder().status(true).build();

        if((checkStr==null)||(checkStr==""))return ret;
 
        switch(checkStr.length()){
            case 13-> {
                String allowedStr = "0123456789";

                for(int i=0;i < checkStr.length();i++){
                    if(!allowedStr.contains(checkStr.subSequence(i, i+1))){
                        ret.setStatus(false);
                        ret.setDescr(checkStr+" містить неприпустимі символи! '"+checkStr.subSequence(i, i+1)+"'");
                        return ret;
                    }
                }
            }
            case 14-> {
                String allowedStr = "0123456789";

                for(int i=0;i < 8;i++){
                    if(!allowedStr.contains(checkStr.toLowerCase().subSequence(i, i+1))){
                        ret.setStatus(false);
                        ret.setDescr(checkStr+" містить неприпустимі символи! '"+checkStr.subSequence(i, i+1)+"'");
                        return ret;
                    }
                }
                
                allowedStr = "0123456789";
                
                for(int i=9;i < checkStr.length();i++){
                    if(!allowedStr.contains(checkStr.subSequence(i, i+1))){
                        ret.setStatus(false);
                        ret.setDescr(checkStr+" містить неприпустимі символи! '"+checkStr.subSequence(i, i+1)+"'");
                        return ret;
                    }
                }
            }
            default -> {
                ret.setStatus(false);
                ret.setDescr(checkStr+" містить неприпустиму кількість символів '"+checkStr.length()+"'");
            }
            
        }
        
        return ret;
    }
    
    private static Answer isValidStr(String checkStr, String allowedStr, int maxChars){
        Answer ret = Answer.builder().status(true).build();

        if((checkStr==null)||(checkStr==""))return ret;
        
        if(maxChars == 0){
            maxChars = checkStr.length();
        }else{
            if(checkStr.length()!=maxChars){
                ret.setStatus(false);
                ret.setDescr(checkStr+" містить неприпустиму кількість символів '"+checkStr.length()+"' повинно бути "+maxChars);
                return ret;
            }
        }
        
        for(int i=0;i < maxChars;i++){
            if(!allowedStr.contains(checkStr.subSequence(i, i+1))){
                ret.setStatus(false);
                ret.setDescr(checkStr+" містить неприпустимі символи! '"+checkStr.subSequence(i, i+1)+"'");
                return ret;
            }
        }
                
        return ret;
    }
}



