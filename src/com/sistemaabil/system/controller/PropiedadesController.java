package com.sistemaabil.system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PropiedadesController {

    @FXML
    private TableView<PropiedadFila> tblPropiedades;

    @FXML
    private TableColumn<PropiedadFila, String> colCodigo;

    @FXML
    private TableColumn<PropiedadFila, String> colDireccion;

    @FXML
    private TableColumn<PropiedadFila, String> colTipo;

    @FXML
    private TableColumn<PropiedadFila, String> colArea;

    @FXML
    private TableColumn<PropiedadFila, String> colPrecio;

    @FXML
    private TableColumn<PropiedadFila, String> colEstado;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnCerrar;

    private final ObservableList<PropiedadFila> propiedades =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarDatosEjemplo();
    }
    
    @FXML
private void agregarPropiedad() {
    System.out.println("Botón Agregar presionado");
}

@FXML
private void editarPropiedad() {
    System.out.println("Botón Editar presionado");
}

@FXML
private void eliminarPropiedad() {
    System.out.println("Botón Eliminar presionado");
}

    private void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colArea.setCellValueFactory(new PropertyValueFactory<>("area"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tblPropiedades.setItems(propiedades);
    }

    private void cargarDatosEjemplo() {
        propiedades.clear();
        propiedades.add(new PropiedadFila("P001", "Zona 10, Guatemala", "Casa", "150", "Q500,000", "Disponible"));
        propiedades.add(new PropiedadFila("P002", "Zona 11, Guatemala", "Apartamento", "85", "Q350,000", "Disponible"));
        propiedades.add(new PropiedadFila("P003", "Mixco, Guatemala", "Terreno", "300", "Q250,000", "Vendido"));
    }

    @FXML
    private void actualizarTabla(ActionEvent event) {
        cargarDatosEjemplo();
    }

    @FXML
    private void cerrar(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    public static class PropiedadFila {
        private final String codigo;
        private final String direccion;
        private final String tipo;
        private final String area;
        private final String precio;
        private final String estado;

        public PropiedadFila(String codigo, String direccion, String tipo,
                             String area, String precio, String estado) {
            this.codigo = codigo;
            this.direccion = direccion;
            this.tipo = tipo;
            this.area = area;
            this.precio = precio;
            this.estado = estado;
        }

        public String getCodigo() { return codigo; }
        public String getDireccion() { return direccion; }
        public String getTipo() { return tipo; }
        public String getArea() { return area; }
        public String getPrecio() { return precio; }
        public String getEstado() { return estado; }
    }
}
