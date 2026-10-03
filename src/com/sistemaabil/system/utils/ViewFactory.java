package com.sistemaabil.system.utils;

import com.sistemaabil.system.ClasePrincipal;
import com.sistemaabil.system.model.Propiedad;
import com.sistemaabil.system.model.Rol;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class ViewFactory {

    private final String PATH_VIEWS = "/com/sistemaabil/system/view/";

    public Scene loadFileFXML(String nameFile, int width, int height) {
        String pathOfFile = PATH_VIEWS + nameFile;
        try {
            FXMLLoader loadFXML = new FXMLLoader();
            URL urlFile = ClasePrincipal.class.getResource(pathOfFile);

            if (urlFile == null) {
                throw new IllegalArgumentException("No se encontró el archivo FXML: " + pathOfFile);
            }

            loadFXML.setBuilderFactory(new JavaFXBuilderFactory());
            loadFXML.setLocation(urlFile);
            return new Scene(loadFXML.load(), width, height);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo cargar el FXML: " + pathOfFile, e);
        }
    }

    public void loadScene(String nameFile) {
        Scene scene = null;

        try {
            switch (nameFile) {
                case "login" -> scene = loadFileFXML("LoginView.fxml", 400, 500);
                case "register" -> {
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setTitle("REGISTRO DE PROPIEDAD");
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setResizable(false);
                    scene = loadFileFXML("RegisterView.fxml", 350, 400);
                }
                case "registerUser" -> {
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setTitle("REGISTRO DE USUARIO");
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setResizable(false);
                    scene = loadFileFXML("RegisterUserView.fxml", 350, 420);
                }
                case "panel" -> {
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setTitle("PANEL DE CONTROL");
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setResizable(true);
                    scene = loadFileFXML("PanelControl.fxml", 700, 700);
                }
                case "busqueda" -> {
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setTitle("BUSCAR PROPIEDAD");
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setResizable(true);
                    scene = loadFileFXML("BusquedaPropiedadesView.fxml", 650, 500);
                }
                default -> scene = loadFileFXML("LoginView.fxml", 400, 500);
            }

            SceneManager.getInstanciaSceneManager().changeScene(scene);
        } catch (NullPointerException e) {
            System.out.println("Error al cargar la escena: " + nameFile);
        }
    }

    public void viewRegister() {
        loadScene("register");
    }

    public void viewRegisterUser() {
        loadScene("registerUser");
    }

    public void viewPanel() {
        loadScene("panel");
    }

    public void viewBusquedaPropiedades() {
        loadScene("busqueda");
    }

    public void viewLogin() {
        loadScene("login");
    }

    /** Pantalla de inicio según el rol: el administrador va al panel; los demás, a la búsqueda. */
    public void viewInicio(Rol rol) {
        if (rol == Rol.ADMINISTRADOR) {
            viewPanel();
        } else {
            viewBusquedaPropiedades();
        }
    }

    public void mostrarDetallePropiedad(Propiedad propiedad) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Detalle de propiedad");
        alerta.setHeaderText(propiedad.getCodigoInterno());
        alerta.setContentText(
                "Dirección: " + propiedad.getDireccion() + "\n"
                + "Tipo: " + propiedad.getTipoPropiedad() + "\n"
                + "Área: " + propiedad.getArea() + " m²\n"
                + "Precio: " + propiedad.getPrecio() + "\n"
                + "Estado: " + propiedad.getEstadoPropiedad()
        );
        alerta.showAndWait();
    }

    /** Muestra un diálogo de confirmación y devuelve true si el usuario aceptó. */
    public boolean confirmar(String mensaje) {
        Alert confirmacion = new Alert(AlertType.CONFIRMATION, mensaje);
        confirmacion.showAndWait();
        return confirmacion.getResult() == ButtonType.OK;
    }
}
