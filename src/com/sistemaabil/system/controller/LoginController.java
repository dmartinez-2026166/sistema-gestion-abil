package com.sistemaabil.system.controller;

import com.sistemaabil.system.service.LoginStatus;
import com.sistemaabil.system.service.UsuarioService;
import com.sistemaabil.system.utils.ViewFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController implements Initializable {

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private Label lblError;


    @FXML
    private Hyperlink lnkRegistro;

    private final UsuarioService usuarioService = new UsuarioService();
    private final ViewFactory viewFactory = new ViewFactory();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblError.setText("");
    }

    @FXML
    public void onLogin(ActionEvent event) {
        LoginStatus resultado = usuarioService.iniciarSesion(txtCorreo.getText(), pwdPassword.getText());

        switch (resultado) {
            case EMPTY_FIELDS -> mostrarError("Ingresa tu correo y tu clave.");
            case INVALID_CREDENTIALS -> mostrarError("Correo o clave incorrectos.");
            case UNKNOWN_ROLE -> mostrarError("El usuario tiene un rol desconocido.");
            case LOGIN_OK -> {
                mostrarError("");
                viewFactory.viewInicio(usuarioService.getRolActual());
            }
        }
    }

    @FXML
    public void onRegister(MouseEvent event) {
        viewFactory.viewRegisterUser();
    }

    private void mostrarError(String mensaje) {
        lblError.setText(mensaje);
    }
}