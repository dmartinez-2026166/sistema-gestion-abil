package com.sistemaabil.system.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertInformation {
    public AlertInformation() {
    
    }
    
    public void mostrarAlerta(String tipoAlerta, String mensaje, String titulo) {
        AlertType tipo = switch (tipoAlerta) {
            case "error"   -> AlertType.ERROR;
            case "confirm" -> AlertType.CONFIRMATION;
            case "warning" -> AlertType.WARNING;
            case "info"    -> AlertType.INFORMATION;
            default        -> AlertType.NONE;
        };
        Alert alerta = new Alert(tipo);
        alerta.setContentText(mensaje);
        alerta.setHeaderText(titulo);
        alerta.showAndWait();
    }
}
