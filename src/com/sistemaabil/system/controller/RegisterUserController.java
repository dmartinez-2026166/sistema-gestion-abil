package com.sistemaabil.system.controller;

import com.sistemaabil.system.model.Rol;
import com.sistemaabil.system.service.UserStatus;
import com.sistemaabil.system.service.UsuarioService;
import com.sistemaabil.system.utils.ViewFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterUserController implements Initializable {

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField pwdClave;

    @FXML
    private PasswordField pwdConfirmarClave;

    @FXML
    private ComboBox<Rol> cmbRol;

    @FXML
    private Label lblMensaje;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnCancelar;

    private final UsuarioService usuarioService = new UsuarioService();
    private final ViewFactory viewFactory = new ViewFactory();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbRol.setItems(FXCollections.observableArrayList(Rol.values()));
        lblMensaje.setText("");
    }

    @FXML
    public void onRegistrar(ActionEvent event) {
        UserStatus resultado = usuarioService.registrarUsuario(
                txtUsuario.getText(),
                txtCorreo.getText(),
                pwdClave.getText(),
                pwdConfirmarClave.getText(),
                cmbRol.getValue());

        switch (resultado) {
            case EMPTY_FIELDS -> mostrarMensaje("Completa todos los campos.");
            case PASSWORD_MISMATCH -> mostrarMensaje("Las claves no coinciden.");
            case PASSWORD_TOO_SHORT -> mostrarMensaje("La clave debe tener al menos 6 caracteres.");
            case USER_EXISTS -> mostrarMensaje("El correo ya está registrado.");
            case ERROR_USER_CREATE -> mostrarMensaje("No se pudo crear el usuario.");
            case USER_CREATED -> viewFactory.viewLogin();
        }
    }

    @FXML
    public void onCancelar(ActionEvent event) {
        viewFactory.viewLogin();
    }

    private void mostrarMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }
}
