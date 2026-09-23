package com.sistemaabil.system.controller;

import com.sistemaabil.system.model.Usuario;
import com.sistemaabil.system.repository.UserRepository;
import com.sistemaabil.system.utils.Sesion;
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

    @FXML
    private PasswordField pwdPassword2;

    @FXML
    private TextField txtCorreo2;

    private final UserRepository userRepository = new UserRepository();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (lblError != null) {
            lblError.setText("");
        }
    }

    @FXML
    public void onLogin(ActionEvent event) {
        String correo = txtCorreo.getText() == null ? "" : txtCorreo.getText().trim();
        String clave = pwdPassword.getText() == null ? "" : pwdPassword.getText();

        if (correo.isBlank() || clave.isBlank()) {
            mostrarError("Ingresa tu correo y tu clave.");
            return;
        }

        Usuario usuario = userRepository.autenticar(correo, clave);

        if (usuario == null) {
            mostrarError("Correo o clave incorrectos.");
            return;
        }

        mostrarError("");
        Sesion.setUsuarioActual(usuario);
        ViewFactory viewFacto = new ViewFactory();

        switch (usuario.getRol()) {
            case "Administrador" -> viewFacto.viewPanel();
            case "Agente Inmobiliario", "Supervisor", "Cliente" -> viewFacto.viewBusquedaPropiedades();
            default -> mostrarError("Rol desconocido: " + usuario.getRol());
        }
    }

    @FXML
    public void onRegister(MouseEvent event) {
        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewRegisterUser();
    }

    private void mostrarError(String mensaje) {
        if (lblError != null) {
            lblError.setText(mensaje);
        }
    }
}