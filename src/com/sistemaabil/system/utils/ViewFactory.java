package com.sistemaabil.system.utils;

import com.sistemaabil.system.ClasePrincipal;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;

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
                case "propiedades" -> {
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setTitle("LISTADO DE PROPIEDADES");
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setResizable(true);
                    scene = loadFileFXML("PropiedadesView.fxml", 850, 500);
                }
                case "register" -> {
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setTitle("REGISTRO DE USUARIO");
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setResizable(false);
                    scene = loadFileFXML("RegisterView.fxml", 350, 400);
                }
                default -> scene = loadFileFXML("LoginView.fxml", 400, 500);
            }

            SceneManager.getInstanciaSceneManager().changeScene(scene);
        } catch (NullPointerException e) {
            System.out.println("Error al cargar la escena: " + nameFile);
        }
    }

    public void viewPropiedades() {
        loadScene("propiedades");
    }

    public void viewRegister() {
        loadScene("register");
    }

    public void viewLogin() {
        loadScene("login");
    }
}
