package com.sistemaabil.system.controller;

import com.sistemaabil.system.model.Propiedad;
import com.sistemaabil.system.repository.PropiedadRepository;
import com.sistemaabil.system.utils.Sesion;
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
import javafx.scene.control.TextField;

public class RegisterController implements Initializable {

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private ComboBox<String> cmbTipo;

    @FXML
    private TextField txtArea;

    @FXML
    private TextField txtPrecio;

    @FXML
    private ComboBox<String> cmbEstado;

    @FXML
    private Label lblMensaje;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    private final PropiedadRepository propiedadRepository = new PropiedadRepository();
    private boolean modoEdicion = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbTipo.setItems(FXCollections.observableArrayList("Casa", "Apartamento", "Terreno", "Local"));
        cmbEstado.setItems(FXCollections.observableArrayList("Disponible", "Vendido", "Alquilado"));

        Propiedad propiedad = Sesion.getPropiedadEnEdicion();

        if (propiedad != null) {
            modoEdicion = true;
            txtCodigo.setText(propiedad.getCodigoInterno());
            txtCodigo.setDisable(true); // el codigo interno no se cambia al editar
            txtDireccion.setText(propiedad.getDireccion());
            cmbTipo.setValue(propiedad.getTipoPropiedad());
            txtArea.setText(String.valueOf(propiedad.getArea()));
            txtPrecio.setText(String.valueOf(propiedad.getPrecio()));
            cmbEstado.setValue(propiedad.getEstadoPropiedad());
            btnGuardar.setText("ACTUALIZAR");
        }

        if (lblMensaje != null) {
            lblMensaje.setText("");
        }
    }

    @FXML
    public void onGuardar(ActionEvent event) {
        String codigo = txtCodigo.getText() == null ? "" : txtCodigo.getText().trim();
        String direccion = txtDireccion.getText() == null ? "" : txtDireccion.getText().trim();
        String tipo = cmbTipo.getValue();
        String estado = cmbEstado.getValue();
        String areaTexto = txtArea.getText() == null ? "" : txtArea.getText().trim();
        String precioTexto = txtPrecio.getText() == null ? "" : txtPrecio.getText().trim();

        if (codigo.isBlank() || direccion.isBlank() || tipo == null || estado == null
                || areaTexto.isBlank() || precioTexto.isBlank()) {
            mostrarMensaje("Completa todos los campos.");
            return;
        }

        double area;
        double precio;

        try {
            area = Double.parseDouble(areaTexto);
            precio = Double.parseDouble(precioTexto);
        } catch (NumberFormatException nfe) {
            mostrarMensaje("Área y precio deben ser números.");
            return;
        }

        boolean exito;

        if (modoEdicion) {
            Propiedad propiedad = Sesion.getPropiedadEnEdicion();
            propiedad.setDireccion(direccion);
            propiedad.setTipoPropiedad(tipo);
            propiedad.setArea(area);
            propiedad.setPrecio(precio);
            propiedad.setEstadoPropiedad(estado);
            exito = propiedadRepository.actualizar(propiedad);
        } else {
            Propiedad propiedad = new Propiedad(0, codigo, direccion, precio, tipo, area, estado);
            exito = propiedadRepository.crear(propiedad);
        }

        if (!exito) {
            mostrarMensaje(modoEdicion
                    ? "No se pudo actualizar la propiedad."
                    : "No se pudo guardar. ¿El código ya existe?");
            return;
        }

        Sesion.setPropiedadEnEdicion(null);
        volverSegunRol();
    }

    @FXML
    public void onCancelar(ActionEvent event) {
        Sesion.setPropiedadEnEdicion(null);
        volverSegunRol();
    }

    private void volverSegunRol() {
        ViewFactory viewFacto = new ViewFactory();

        if (Sesion.getUsuarioActual() != null
                && "Administrador".equals(Sesion.getUsuarioActual().getRol())) {
            viewFacto.viewPanel();
        } else {
            viewFacto.viewBusquedaPropiedades();
        }
    }

    private void mostrarMensaje(String mensaje) {
        if (lblMensaje != null) {
            lblMensaje.setText(mensaje);
        }
    }
}
