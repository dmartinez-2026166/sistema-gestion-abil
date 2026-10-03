package com.sistemaabil.system.controller;

import com.sistemaabil.system.model.EstadoPropiedad;
import com.sistemaabil.system.model.Propiedad;
import com.sistemaabil.system.service.PropertyStatus;
import com.sistemaabil.system.service.PropiedadService;
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
    private ComboBox<EstadoPropiedad> cmbEstado;

    @FXML
    private Label lblMensaje;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    private final PropiedadService propiedadService = new PropiedadService();
    private final UsuarioService usuarioService = new UsuarioService();
    private final ViewFactory viewFactory = new ViewFactory();
    private Propiedad propiedadEnEdicion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbTipo.setItems(FXCollections.observableArrayList(propiedadService.getTipos()));
        cmbEstado.setItems(FXCollections.observableArrayList(EstadoPropiedad.values()));

        propiedadEnEdicion = propiedadService.getPropiedadEnEdicion();

        if (propiedadEnEdicion != null) {
            txtCodigo.setText(propiedadEnEdicion.getCodigoInterno());
            txtCodigo.setDisable(true); // el codigo interno no se cambia al editar
            txtDireccion.setText(propiedadEnEdicion.getDireccion());
            cmbTipo.setValue(propiedadEnEdicion.getTipoPropiedad());
            txtArea.setText(String.valueOf(propiedadEnEdicion.getArea()));
            txtPrecio.setText(String.valueOf(propiedadEnEdicion.getPrecio()));
            cmbEstado.setValue(propiedadEnEdicion.getEstadoPropiedad());
            btnGuardar.setText("ACTUALIZAR");
        }

        lblMensaje.setText("");
    }

    @FXML
    public void onGuardar(ActionEvent event) {
        String direccion = txtDireccion.getText();
        String tipo = cmbTipo.getValue();
        EstadoPropiedad estado = cmbEstado.getValue();
        Double area = leerNumero(txtArea);
        Double precio = leerNumero(txtPrecio);

        PropertyStatus resultado = (propiedadEnEdicion != null)
                ? propiedadService.actualizar(propiedadEnEdicion, direccion, tipo, area, precio, estado)
                : propiedadService.crear(txtCodigo.getText(), direccion, tipo, area, precio, estado);

        switch (resultado) {
            case EMPTY_FIELDS -> mostrarMensaje("Completa todos los campos.");
            case INVALID_VALUES -> mostrarMensaje("Área y precio deben ser números mayores que cero.");
            case ERROR_PROPERTY_CREATE -> mostrarMensaje("No se pudo guardar. ¿El código ya existe?");
            case ERROR_PROPERTY_UPDATE -> mostrarMensaje("No se pudo actualizar la propiedad.");
            case PROPERTY_CREATED, PROPERTY_UPDATED -> {
                propiedadService.finalizarEdicion();
                volverSegunRol();
            }
        }
    }

    @FXML
    public void onCancelar(ActionEvent event) {
        propiedadService.finalizarEdicion();
        volverSegunRol();
    }

    /**
     * Convierte el texto del campo a número (conversión de la vista al modelo).
     * Vacío -> null; texto que no es número -> NaN. Las reglas de validación las aplica el service.
     */
    private Double leerNumero(TextField campo) {
        String texto = campo.getText() == null ? "" : campo.getText().trim();

        if (texto.isEmpty()) {
            return null;
        }

        try {
            return Double.valueOf(texto);
        } catch (NumberFormatException nfe) {
            return Double.NaN;
        }
    }

    private void volverSegunRol() {
        viewFactory.viewInicio(usuarioService.getRolActual());
    }

    private void mostrarMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }
}
