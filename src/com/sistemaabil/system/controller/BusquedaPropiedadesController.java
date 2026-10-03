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

public class BusquedaPropiedadesController implements Initializable {

    @FXML
    private TextField txtBusqueda;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnNuevaPropiedad;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Label lblMensaje;

    @FXML
    private TableView<Propiedad> tblResultados;

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
    private final ObservableList<Propiedad> resultados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoInterno"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoPropiedad"));
        colArea.setCellValueFactory(new PropertyValueFactory<>("area"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoPropiedad"));
        tblResultados.setItems(resultados);

        boolean puedeRegistrar = usuarioService.puedeRegistrarPropiedades();
        btnNuevaPropiedad.setVisible(puedeRegistrar);
        btnNuevaPropiedad.setManaged(puedeRegistrar);

        // arranca vacia; solo se llena cuando el usuario busca algo
        lblMensaje.setText("Escribe un código o dirección y presiona BUSCAR.");
    }

    @FXML
    public void onBuscar(ActionEvent event) {
        String termino = txtBusqueda.getText();

        if (termino == null || termino.isBlank()) {
            resultados.clear();
            lblMensaje.setText("Escribe un código o dirección para buscar.");
            return;
        }

        List<Propiedad> encontradas = propiedadService.buscar(termino);
        resultados.setAll(encontradas);
        lblMensaje.setText(encontradas.isEmpty() ? "No se encontraron propiedades." : "");
    }

    @FXML
    public void onNuevaPropiedad(ActionEvent event) {
        propiedadService.finalizarEdicion();
        viewFactory.viewRegister();
    }

    @FXML
    public void onCerrarSesion(ActionEvent event) {
        usuarioService.cerrarSesion();
        viewFactory.viewLogin();
    }
}
