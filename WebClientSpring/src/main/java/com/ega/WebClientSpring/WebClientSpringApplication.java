package com.ega.WebClientSpring;

import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebClientSpringApplication {

public static void main(String[] args) {
    /*    
    SpringApplication app = new SpringApplication(WebClientSpringApplication.class);
        app.setDefaultProperties(Collections
          .singletonMap("server.port", "8050"));
        app.run(args);
     */   
    
      if (args.length > 0) {
            if(args[0].trim().matches("-gui")){
                MainWindowGUI guiClient = new MainWindowGUI();
                guiClient.setVisible(true);
            }else{ 
                MainWindowCL commandLineClient = new MainWindowCL();
                commandLineClient.run(args);
            }
        }else {
            MainWindowCL commandLineClient = new MainWindowCL();
            commandLineClient.run(args);
      }
    
    }
}
