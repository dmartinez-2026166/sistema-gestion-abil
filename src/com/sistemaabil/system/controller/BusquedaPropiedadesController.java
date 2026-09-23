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
    private TableColumn<Propiedad, String> colEstado;

    private final PropiedadRepository propiedadRepository = new PropiedadRepository();
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

        boolean esAgente = Sesion.getUsuarioActual() != null
                && "Agente Inmobiliario".equals(Sesion.getUsuarioActual().getRol());
        btnNuevaPropiedad.setVisible(esAgente);
        btnNuevaPropiedad.setManaged(esAgente);

        lblMensaje.setText("Escribe un código o dirección y presiona BUSCAR.");
    }

    @FXML
    public void onBuscar(ActionEvent event) {
        String termino = txtBusqueda.getText() == null ? "" : txtBusqueda.getText().trim();

        if (termino.isBlank()) {
            resultados.clear();
            lblMensaje.setText("Escribe un código o dirección para buscar.");
            return;
        }

        buscar(termino);
    }

    @FXML
    public void onNuevaPropiedad(ActionEvent event) {
        Sesion.setPropiedadEnEdicion(null);
        new ViewFactory().viewRegister();
    }

    @FXML
    public void onCerrarSesion(ActionEvent event) {
        Sesion.cerrarSesion();
        new ViewFactory().viewLogin();
    }

    private void buscar(String termino) {
        List<Propiedad> encontradas = propiedadRepository.buscarPorCodigoODireccion(termino);
        resultados.setAll(encontradas);

        lblMensaje.setText(encontradas.isEmpty() ? "No se encontraron propiedades." : "");
    }
}
