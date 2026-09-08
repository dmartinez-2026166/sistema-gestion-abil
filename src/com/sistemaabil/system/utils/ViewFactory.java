package com.sistemaabil.system.utils;

import com.sistemaabil.system.ClasePrincipal;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;

public class ViewFactory {

    private final String PATH_VIEWS = "/com/sistemaabil/system/view";

    public Scene loadFileFXML(String nameFile, int width, int height) {
        String pathOfFile = PATH_VIEWS + nameFile;
        try {
            FXMLLoader loadFXML = new FXMLLoader();
            URL urlFile = ClasePrincipal.class.getResource(pathOfFile);
            loadFXML.setBuilderFactory(new JavaFXBuilderFactory());
            loadFXML.setLocation(urlFile);
            return new Scene(loadFXML.load(), width,  height);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    public void loadScene(String nameFile){
        Scene scene = null;
        try {
            switch (nameFile) {
                case  "login" -> scene = loadFileFXML("LoginView.fxml", 400, 500);
                case "register" -> {
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setTitle("REGISTRO DE USUARIO");
                    SceneManager.getInstanciaSceneManager().getStagePrincipal().setResizable(false);
                    scene = loadFileFXML("RegisterView.fxml",350,400);
                }
                default -> scene = loadFileFXML("LoginView.fxml", 0, 0);
            }
            SceneManager.getInstanciaSceneManager().changeScene(scene);
        } catch (NullPointerException e) {
            System.out.println("Error load scene");
            //alert
        }
    }
    
    public void viewRegister(){
        loadScene("register");
    }
    public void viewLogin() {
        loadScene("login");
    }
}
