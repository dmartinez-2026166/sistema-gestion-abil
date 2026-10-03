package com.sistemaabil.system.controller;

import com.sistemaabil.system.model.EstadoPropiedad;
import com.sistemaabil.system.model.Propiedad;
import com.sistemaabil.system.service.PropiedadService;
import com.sistemaabil.system.service.UsuarioService;
import com.sistemaabil.system.utils.ViewFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PanelControlController implements Initializable {

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnFiltroTodas;

    @FXML
    private Button btnFiltroDisponibles;

    @FXML
    private Button btnFiltroVendidas;

    @FXML
    private Button btnFiltroAlquiladas;

    @FXML
    private Button btnNuevaPropiedad;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnVer;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TextField txtBuscar;

    @FXML
    private Label lblMensaje;

    @FXML
    private TableView<Propiedad> tblPropiedades;

    @FXML
    private TableColumn<Propiedad, String> colCodigo;

    @FXML
    private TableColumn<Propiedad, String> colDireccion;

    @FXML
    private TableColumn<Propiedad, String> colTipo;

    @FXML
    private TableColumn<Propiedad, Double> colArea;

    @FXML
    private TableColumn<Propiedad, Double> colPrecio;

    @FXML
    private TableColumn<Propiedad, EstadoPropiedad> colEstado;

    private final PropiedadService propiedadService = new PropiedadService();
    private final UsuarioService usuarioService = new UsuarioService();
    private final ViewFactory viewFactory = new ViewFactory();
    private final ObservableList<Propiedad> propiedades = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoInterno"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoPropiedad"));
        colArea.setCellValueFactory(new PropertyValueFactory<>("area"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoPropiedad"));
        tblPropiedades.setItems(propiedades);

        cargarPropiedades(null);
    }

    @FXML
    public void onFiltroTodas(ActionEvent event) {
        cargarPropiedades(null);
    }

    @FXML
    public void onFiltroDisponibles(ActionEvent event) {
        cargarPropiedades(EstadoPropiedad.DISPONIBLE);
    }

    @FXML
    public void onFiltroVendidas(ActionEvent event) {
        cargarPropiedades(EstadoPropiedad.VENDIDO);
    }

    @FXML
    public void onFiltroAlquiladas(ActionEvent event) {
        cargarPropiedades(EstadoPropiedad.ALQUILADO);
    }

    @FXML
    public void onBuscar(ActionEvent event) {
        List<Propiedad> encontradas = propiedadService.buscar(txtBuscar.getText());
        propiedades.setAll(encontradas);
        mostrarMensaje(encontradas.isEmpty() ? "No se encontraron propiedades." : "");
    }

    @FXML
    public void onNuevaPropiedad(ActionEvent event) {
        propiedadService.finalizarEdicion();
        viewFactory.viewRegister();
    }

    @FXML
    public void onVer(ActionEvent event) {
        Propiedad seleccionada = tblPropiedades.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarMensaje("Selecciona una propiedad de la tabla primero.");
            return;
        }

        viewFactory.mostrarDetallePropiedad(seleccionada);
    }

    @FXML
    public void onEditar(ActionEvent event) {
        Propiedad seleccionada = tblPropiedades.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarMensaje("Selecciona una propiedad de la tabla primero.");
            return;
        }

        propiedadService.iniciarEdicion(seleccionada);
        viewFactory.viewRegister();
    }

    @FXML
    public void onEliminar(ActionEvent event) {
        Propiedad seleccionada = tblPropiedades.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarMensaje("Selecciona una propiedad de la tabla primero.");
            return;
        }

        if (viewFactory.confirmar("¿Eliminar la propiedad " + seleccionada.getCodigoInterno() + "?")) {
            boolean exito = propiedadService.eliminar(seleccionada);
            cargarPropiedades(null);

            if (!exito) {
                mostrarMensaje("No se pudo eliminar la propiedad.");
            }
        }
    }

    @FXML
    public void onCerrarSesion(ActionEvent event) {
        usuarioService.cerrarSesion();
        viewFactory.viewLogin();
    }

    private void cargarPropiedades(EstadoPropiedad estado) {
        propiedades.setAll(propiedadService.listar(estado));
        mostrarMensaje("");
    }

    private void mostrarMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }
}
