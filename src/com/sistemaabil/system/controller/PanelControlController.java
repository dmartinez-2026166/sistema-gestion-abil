package com.sistemaabil.system.controller;

import com.sistemaabil.system.model.Propiedad;
import com.sistemaabil.system.repository.PropiedadRepository;
import com.sistemaabil.system.utils.Sesion;
import com.sistemaabil.system.utils.ViewFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private TableColumn<Propiedad, String> colEstado;

    private final PropiedadRepository propiedadRepository = new PropiedadRepository();
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
        cargarPropiedades("Disponible");
    }

    @FXML
    public void onFiltroVendidas(ActionEvent event) {
        cargarPropiedades("Vendido");
    }

    @FXML
    public void onFiltroAlquiladas(ActionEvent event) {
        cargarPropiedades("Alquilado");
    }

    @FXML
    public void onBuscar(ActionEvent event) {
        String termino = txtBuscar.getText() == null ? "" : txtBuscar.getText().trim();
        List<Propiedad> encontradas = propiedadRepository.buscarPorCodigoODireccion(termino);
        propiedades.setAll(encontradas);
        mostrarMensaje(encontradas.isEmpty() ? "No se encontraron propiedades." : "");
    }

    @FXML
    public void onNuevaPropiedad(ActionEvent event) {
        Sesion.setPropiedadEnEdicion(null);
        new ViewFactory().viewRegister();
    }

    @FXML
    public void onVer(ActionEvent event) {
        Propiedad seleccionada = tblPropiedades.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarMensaje("Selecciona una propiedad de la tabla primero.");
            return;
        }

        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Detalle de propiedad");
        alerta.setHeaderText(seleccionada.getCodigoInterno());
        alerta.setContentText(
                "Dirección: " + seleccionada.getDireccion() + "\n"
                + "Tipo: " + seleccionada.getTipoPropiedad() + "\n"
                + "Área: " + seleccionada.getArea() + " m²\n"
                + "Precio: " + seleccionada.getPrecio() + "\n"
                + "Estado: " + seleccionada.getEstadoPropiedad()
        );
        alerta.showAndWait();
    }

    @FXML
    public void onEditar(ActionEvent event) {
        Propiedad seleccionada = tblPropiedades.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarMensaje("Selecciona una propiedad de la tabla primero.");
            return;
        }

        Sesion.setPropiedadEnEdicion(seleccionada);
        new ViewFactory().viewRegister();
    }

    @FXML
    public void onEliminar(ActionEvent event) {
        Propiedad seleccionada = tblPropiedades.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarMensaje("Selecciona una propiedad de la tabla primero.");
            return;
        }

        Alert confirmacion = new Alert(AlertType.CONFIRMATION,
                "¿Eliminar la propiedad " + seleccionada.getCodigoInterno() + "?");
        confirmacion.showAndWait();

        if (confirmacion.getResult() == ButtonType.OK) {
            boolean exito = propiedadRepository.eliminar(seleccionada.getIdPropiedad());
            mostrarMensaje(exito ? "" : "No se pudo eliminar la propiedad.");
            cargarPropiedades(null);
        }
    }

    @FXML
    public void onCerrarSesion(ActionEvent event) {
        Sesion.cerrarSesion();
        new ViewFactory().viewLogin();
    }

    private void cargarPropiedades(String estado) {
        propiedades.setAll(propiedadRepository.listarPorEstado(estado));
        mostrarMensaje("");
    }

    private void mostrarMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }
}
