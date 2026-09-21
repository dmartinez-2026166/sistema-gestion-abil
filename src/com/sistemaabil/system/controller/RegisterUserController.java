package com.sistemaabil.system.controller;

import com.sistemaabil.system.repository.UserRepository;
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
    private ComboBox<String> cmbRol;

    @FXML
    private Label lblMensaje;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnCancelar;

    private final UserRepository userRepository = new UserRepository();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbRol.setItems(FXCollections.observableArrayList(
                "Administrador", "Supervisor", "Agente Inmobiliario", "Cliente"
        ));
        lblMensaje.setText("");
    }

    @FXML
    public void onRegistrar(ActionEvent event) {
        String usuario = txtUsuario.getText() == null ? "" : txtUsuario.getText().trim();
        String correo = txtCorreo.getText() == null ? "" : txtCorreo.getText().trim();
        String clave = pwdClave.getText() == null ? "" : pwdClave.getText();
        String confirmar = pwdConfirmarClave.getText() == null ? "" : pwdConfirmarClave.getText();
        String rol = cmbRol.getValue();

        if (usuario.isBlank() || correo.isBlank() || clave.isBlank() || rol == null) {
            mostrarMensaje("Completa todos los campos.");
            return;
        }

        if (!clave.equals(confirmar)) {
            mostrarMensaje("Las claves no coinciden.");
            return;
        }

        if (clave.length() < 6) {
            mostrarMensaje("La clave debe tener al menos 6 caracteres.");
            return;
        }

        boolean creado = userRepository.crearUsuario(usuario, clave, correo, rol);

        if (!creado) {
            mostrarMensaje("No se pudo crear el usuario. ¿El correo ya está registrado?");
            return;
        }

        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewLogin();
    }

    @FXML
    public void onCancelar(ActionEvent event) {
        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewLogin();
    }

    private void mostrarMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }
}
