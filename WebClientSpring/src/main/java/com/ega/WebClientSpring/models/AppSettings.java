/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ega.WebClientSpring.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author parallels
 */
@Data
public class AppSettings {
    private String url;
    
    public AppSettings() {
        LoadSettings();
    }

    private void SaveSettings() {
        try {
          FileWriter settingsFile = new FileWriter("webclient.settings");
          settingsFile.write("webservice.url=http://localhost:8080/api/v1/persons");
          settingsFile.close();
          System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
    
    }
    
    private void LoadSettings() {
        try {
             File settingsFile = new File("webclient.settings");
             Scanner myReader = new Scanner(settingsFile);
             while (myReader.hasNextLine()) {
               String param = myReader.nextLine();
               System.out.println(param);
                if(param.strip().toLowerCase().startsWith("webservice.url=")){
                    int delim = param.indexOf("=");
                    if (delim > 0){
                        url = param.substring(delim+1);
                    } 
                }
             }
             myReader.close();
           } catch (FileNotFoundException e) {
               SaveSettings();
             System.out.println("An error occurred.");
             e.printStackTrace();
           }
  }
    
 
    
}
