package com.sistemaabil.system.controller;

import com.sistemaabil.system.utils.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {
    @FXML
    private Button btnIniciarSesion;

    @FXML
    private Label lblCorreo;

    @FXML
    private Label lblPassword;

    @FXML
    private Hyperlink lnkRegistro;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private TextField txtCorreo;
    
    @FXML
    public void onRegister(MouseEvent event) {
        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewRegister();
    }
}
