/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ega.WebClientSpring;

import com.ega.WebClientSpring.models.AppSettings;
import java.io.FileNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *
 * @author parallels
 */
@Configuration
public class WebConfig {
    private final AppSettings appSettings;

    public WebConfig() throws FileNotFoundException {
        this.appSettings = new AppSettings();
    }

    @Bean
    public WebClient webClient() {

    // Create a WebClient bean for making reactive web requests
    
    WebClient webClient = WebClient.builder()
      .baseUrl(appSettings.getUrl()) // Set the base URL for the requests
      .defaultCookie("cookie-name", "cookie-value") // Set a default cookie for the requests
      .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // Set a default header for the requests
      .build();
    
    return webClient; // Return the configured WebClient bean
  }
}
