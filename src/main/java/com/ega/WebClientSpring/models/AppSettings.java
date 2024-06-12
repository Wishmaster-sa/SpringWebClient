/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ega.WebClientSpring.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import lombok.Data;
import org.json.JSONObject;

/**
 *
 * @author parallels
 */
@Data
public class AppSettings {
    private String url;
    private HashMap<String,String> headers = new HashMap<>();
    
    public AppSettings() {
        LoadSettings();
    }

    private void SaveSettings() {
        try {
          FileWriter settingsFile = new FileWriter("webclient.settings");
          settingsFile.write(this.toJSON().toString());
          settingsFile.close();
          System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
          System.out.println("An error occurred.\n"+e.getMessage());
        }
    
    }
    
    private void LoadSettings(){
        String text = "";
        
        try {
            File settingsFile = new File("webclient.settings");
            Scanner myReader = new Scanner(settingsFile);
            while(myReader.hasNext()){
                text+=myReader.nextLine();
                //String line = myReader.nextLine();
                //text+=line;
                //System.out.println(line);
            }
            myReader.close();
           } catch (FileNotFoundException e) {
               //SaveSettings();
             System.out.println("An error occurred while load settings file: "+e.getMessage());
           }
        
        try{
            JSONObject json = new JSONObject(text);
            this.fromJSON(json);
        }catch(Exception ex){
             System.out.println("Text:\n"+text+"\n is not valid JSON!\nError:"+ex.getMessage());
        }
        
    }
    
    
 
    public JSONObject toJSON(){
        JSONObject ret = new JSONObject();
        ret.put("url", url);
        ret.put("headers", headers);
        
        return ret;
    }

    public AppSettings fromJSON(JSONObject json){
        //AppSettings ret = new AppSettings();
       
        this.setUrl(json.optString("url", ""));
        JSONObject headers = json.optJSONObject("headers");
        for(int i=0;i<headers.names().length();i++){
            String key = headers.names().getString(i);
            String value = headers.getString(key);
            this.headers.put(key, value);
            System.out.println("add HEADER: "+key+"="+value);
        }
        
        return this;
    }
}
