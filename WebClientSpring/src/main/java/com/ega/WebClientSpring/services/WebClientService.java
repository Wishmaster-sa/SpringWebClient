/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ega.WebClientSpring.services;

import com.ega.WebClientSpring.interfaces.WebClientSpringInterface;
import com.ega.WebClientSpring.models.Answer;
import com.ega.WebClientSpring.models.Persona;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author parallels
 */
@Service
public class WebClientService implements WebClientSpringInterface{
    private final WebClient webClient;

    // Constructor-based dependency injection for WebClient
    @Autowired
    public WebClientService(WebClient webClient) {
        this.webClient = webClient;
  }

    // Method to retrieve an employee using a GET request
    @Override
    public Answer findPersona(String rnokpp) {
        Answer ans;
        Mono<Answer> personaMono;
        personaMono = webClient.get()
                //.uri("/find/{rnokpp}", "RALUMHK1") // Assuming you want to retrieve an employee with ID 1
                .uri("/find/"+rnokpp)
                .retrieve()
                .bodyToMono(Answer.class);

        ans = personaMono.block();
                
        return ans;
    }
    
    @Override
    public Answer showAll() {
        Answer ans = Answer.builder().status(Boolean.FALSE).descr("Unknown error").build();
        
        try{
            Mono<Answer> personaMono;
            personaMono = webClient.get()
                    .uri("/list")
                    .retrieve()
                    .bodyToMono(Answer.class);

            ans = personaMono.block();
        }catch (Exception ex){                    //якщо помилка
              ans.setDescr(ex.getMessage());        //надаємо опис помилки
          }
     
        return ans;
    }

    @Override
    public Answer getRequest(String url) {
        Answer ans = Answer.builder().status(Boolean.FALSE).descr("Unknown error").build();
        WebClient webCl = WebClient.builder()
            .baseUrl(url) // Set the base URL for the requests
            .defaultCookie("cookie-name", "cookie-value") // Set a default cookie for the requests
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // Set a default header for the requests
            .build();
        
        try{
            Mono<Answer> personaMono;
            personaMono = webCl.get()
                    .uri("")
                    .retrieve()
                    .bodyToMono(Answer.class);

            ans = personaMono.block();
        }catch (Exception ex){                    //якщо помилка
              ans.setDescr(ex.getMessage());        //надаємо опис помилки
          }
     
        return ans;
    }

    @Override
    public Answer savePersona(Persona persona) {
        Answer ans;
        
        Mono<Answer> personaMono;
        if(persona.getId()==0){ //ADD new persona
            personaMono = webClient.post()
                    .uri("/add")
                    .bodyValue(persona.toJSON().toString())
                    .retrieve()
                    .bodyToMono(Answer.class);
        }else{  //UPDATE persona
            personaMono = webClient.delete()
                    .uri("/delete/"+persona.getRnokpp())
                    //.bodyValue(persona.toJSON().toString())
                    .retrieve()
                    .bodyToMono(Answer.class);
            personaMono = webClient.post()
                    .uri("/add")
                    .bodyValue(persona.toJSON().toString())
                    .retrieve()
                    .bodyToMono(Answer.class);
        }
      
        ans = personaMono.block();
     
        return ans;
    }

    @Override
    public Answer deletePersona(String rnokpp) {
        Answer ans;
        
        Mono<Answer> personaMono;
        personaMono = webClient.delete()
                .uri("/delete/"+rnokpp)
                .retrieve()
                .bodyToMono(Answer.class);
      
        ans = personaMono.block();
     
        return ans;
    }
    
    @Override
    public Answer checkPersona(String rnokpp) {
        Answer ans;
        Mono<Answer> personaMono;
        personaMono = webClient.get()
                //.uri("/find/{rnokpp}", "RALUMHK1") // Assuming you want to retrieve an employee with ID 1
                .uri("/check/"+rnokpp)
                .retrieve()
                .bodyToMono(Answer.class);

        ans = personaMono.block();
        return ans;
    }

    @Override
    public Answer checkup(String rnokpp) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}