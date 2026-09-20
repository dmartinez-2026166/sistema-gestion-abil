package com.sistemaabil.system.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import com.sistemaabil.system.utils.ViewFactory;

/**
 *
 * @author diego
 */
public class LoginController implements Initializable {
    
         @Override
         public void initialize(URL url, ResourceBundle rb) {
        
         }
         
        @FXML
        public void onRegister(MouseEvent event){
        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewRegister();
    }

}
