/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ega.WebClientSpring;

import com.ega.WebClientSpring.interfaces.WebClientSpringInterface;
import com.ega.WebClientSpring.models.Answer;
import com.ega.WebClientSpring.models.AppSettings;
import com.ega.WebClientSpring.models.Persona;
import com.ega.WebClientSpring.services.WebClientService;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author parallels
 */
public class MainWindowCL {
    private boolean isWorking;
    //private WebClientService service;
    private final WebClientSpringInterface service;
    private WebConfig webClient; ;
    private AppSettings appSettings;
    
    public MainWindowCL(){
        this.isWorking = true;
        try {
            this.webClient = new WebConfig();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainWindowCL.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.service = new WebClientService(webClient.webClient());
        
        
    }
    
    public void run(String[] args){
        Scanner in = new Scanner(System.in);
        SendMessage("Welcome to web-client command line.\nType help to read manual");

        while (isWorking){
            sendMessage("command: ");        
            String command = in.nextLine();
            command = command.replace(" ", "");
            int argbegin = command.indexOf("=");
            if(argbegin>0){
                command.substring(argbegin+1);
                command = command.substring(0, argbegin);
            }
            command = command.toLowerCase();
            switch(command){
                case "help" -> DisplayCommands();
                case "startgui" -> StartGUI();
                case "list" -> ShowAllPersons();
                case "addpersona" -> AddPersonaIntoDatabase();
                case "updatepersona" -> UpdatePersona();
                case "findpersona" -> FindPersona();
                case "checkpersona" -> CheckPersona();
                case "checkuppersona" -> CheckPersona();
                case "deletepersona" -> StartGUI();
                case "exit" -> ExitProgramm();
                case "quit" -> ExitProgramm();
                default -> UnknownCommand(command);
            }
        
        }
    }

    public void ShowAllPersons(){
        Answer ans;
        
        ans = service.showAll();
        if(ans.getStatus()==true){
            JSONArray jsResult = new JSONArray(ans.getResult());
            for(int i=0;i<jsResult.length();i++){
                SendMessage("************************************************************");
                JSONObject jsPersona = jsResult.getJSONObject(i);
                SendMessage("id: "+jsPersona.optString("id"));
                SendMessage("First name: "+jsPersona.optString("firstName"));
                SendMessage("Last name: "+jsPersona.optString("lastName"));
                SendMessage("Birth date: "+jsPersona.optString("birthDate"));
                SendMessage("Age: "+jsPersona.optString("age"));
                SendMessage("RNOKPP: "+jsPersona.optString("rnokpp"));
                SendMessage("Pasport: "+jsPersona.optString("pasport"));
                SendMessage("UNZR: "+jsPersona.optString("unzr"));
                SendMessage("Is Checked: "+jsPersona.optString("isChecked"));
                SendMessage("Check Request: "+jsPersona.optString("CheckedRequest"));
                
            }
            
        }else{
            SendMessage("Answer:/n"+ans.toString());
        }
        
    }
    
    public void AddPersonaIntoDatabase(){
        Answer ans = Answer.builder()
                .status(false)
                .descr("Unknown error")
                .build();

        Persona persona;
        Scanner in = new Scanner(System.in);
        
        sendMessage("First name: ");        
        String firstName = in.nextLine();
        sendMessage("Last name: ");        
        String lastName = in.nextLine();
        sendMessage("Birth date YYYY-MM-DD: ");        
        LocalDate birthDate = getDateFromString(in.nextLine());        
        sendMessage("RNOKPP: ");
        String rnokpp = in.nextLine();
        if(rnokpp.isEmpty()){
            SendMessage("RNOKPP can't be empty!");
            AddPersonaIntoDatabase();
        }
        sendMessage("Pasport: ");        
        String pasport = in.nextLine();
        sendMessage("UNZR: ");        
        String unzr = in.nextLine();
      
        persona = Persona.builder()
                .id(Long.parseLong("0"))
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .rnokpp(rnokpp)
                .pasport(pasport)
                .unzr(unzr)
                .build();
        
        try{
            ans = service.savePersona(persona);
        }catch(Exception ex){
            SendMessage("Error: "+ex.getMessage());
            return;
        }
        
        SendMessage("Answer:\n"+ans.toString());
    }
    
    public void UpdatePersona(){
        Answer ans = Answer.builder()
                .status(false)
                .descr("Unknown error")
                .build();
        
        
        Scanner in = new Scanner(System.in);
        Persona persona;
        
        sendMessage("RNOKPP: ");
        String rnokpp = in.nextLine();
        if(rnokpp.isEmpty()){
            SendMessage("RNOKPP can't be empty!");
            return;
        }
        try{
            ans = service.findPersona(rnokpp);
        }catch(Exception ex){
            SendMessage("Error: "+ex.getMessage());
            return;
        }
        
        if((ans.getStatus()==false)||(ans.getResult()==null)){
            SendMessage("Error: "+ans.getDescr());
            return;
        }
        JSONObject jsPersona = new JSONObject(ans.getResult());
        SendMessage("All ok\n"+jsPersona.toString());

        sendMessage("Select which field you want to change? (firstName, lastName, birthDate, pasport, unzr): ");
        String key = in.nextLine();
        ArrayList<String> allowedField;
        allowedField = new ArrayList();
        allowedField.add("firstName");
        allowedField.add("lastName");
        allowedField.add("birthDate");
        allowedField.add("pasport");
        allowedField.add("unzr");
        
        if(!allowedField.contains(key)){
            SendMessage("Field "+key+" is not allowed!");
            return;
        }
        sendMessage("Current value: "+jsPersona.optString(key)+". New value is: ");
        String value = in.nextLine();

        if(key.contentEquals("birthDay")){
            
        }
        jsPersona.put(key, value);
        try{
            persona = Persona.builder().build();
            persona = persona.fromJSON(jsPersona);
            ans = service.savePersona(persona);
        }catch(Exception ex){
            SendMessage("Error: "+ex.getMessage());
            return;
        }
        SendMessage("Answer:\n"+ans.toString());
        
    }
    
    public void FindPersona(){
        Answer ans;
        Scanner in = new Scanner(System.in);
        
        sendMessage("RNOKPP: ");
        String rnokpp = in.nextLine();
        if(rnokpp.isEmpty()){
            SendMessage("RNOKPP can't be empty!");
            return;
        }
        
        
        try{
            ans = service.findPersona(rnokpp);
        }catch(Exception ex){
            SendMessage("Error: "+ex.getMessage());
            return;
        }
        SendMessage("Answer:\n"+ans.toString());
    }
    
    public void CheckPersona(){
        Answer ans;
        Scanner in = new Scanner(System.in);
        
        SendMessage("RNOKPP: ");
        String rnokpp = in.nextLine();
        if(rnokpp.isEmpty()){
            SendMessage("RNOKPP can't be empty!");
            return;
        }
        
        
        try{
            ans = service.checkPersona(rnokpp);
        }catch(Exception ex){
            SendMessage("Error: "+ex.getMessage());
            return;
        }
        SendMessage(""+ans.toString());
    }
    
    public void DeletePersona(){
        Answer ans;
        Scanner in = new Scanner(System.in);
        
        SendMessage("RNOKPP: ");
        String rnokpp = in.nextLine();
        if(rnokpp.isEmpty()){
            SendMessage("RNOKPP can't be empty!");
            return;
        }
        
        
        try{
            ans = service.deletePersona(rnokpp);
        }catch(Exception ex){
            SendMessage("Error: "+ex.getMessage());
            return;
        }
            ans = service.deletePersona(rnokpp);
        SendMessage("Answer:/n"+ans.toString());
    }
    
    
    public void StartGUI(){
        isWorking = false;
        MainWindowGUI guiClient = new MainWindowGUI();
        guiClient.setVisible(true);
    }
    
    public void DisplayCommands(){
            String help = "Available commands:\n\n"+
            "help - this help\n"+
            "start GUI\n"+
            "list\n"+
            "add persona\n"+
            "update persona\n"+
            "find persona\n"+
            "check persona\n"+
            //"check up persona\n"+
            "delete persona\n"+
            "exit\n";
            
            System.out.println(help);
    }
    
    public void SendMessage(String textToType){
        System.out.println(textToType);
    }
    public void sendMessage(String textToType){
        System.out.print(textToType);
    }
    
    public void UnknownCommand(String command){
        String[] answers = new String[3];
        
        answers[0] = "You don't have the rights to execute command: "+command+"\n";
        answers[1] = "Command "+command+" is unknown!\n";
        answers[2] = "*PROHIBITET* Directive #4\n";
        
        int select = new Random().nextInt(3);
        
        System.out.println(answers[select]);
        
    }
    
    public void ExitProgramm(){
        SendMessage("bye.");
        System.exit(0);
        
    }    

    
    public LocalDate getDateFromString(String date){
        LocalDate ret = LocalDate.ofYearDay(0, 1);
        if(date.isEmpty()){
            return ret;
        }else
        {
            try{
                ret = LocalDate.parse(date);
            }catch (Exception ex){
                SendMessage("Failed to convert String to Date!/n"+date+" is not acceptable string.\nMust be in YYYY-MM-DD format!\n");
            }
        }
        
        return ret;
    }
    
}
