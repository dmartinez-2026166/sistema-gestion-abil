package com.sistemaabil.system;

import com.sistemaabil.system.utils.SceneManager;
import com.sistemaabil.system.utils.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClasePrincipal extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stageRoot) {
        SceneManager.getInstanciaSceneManager().setStagePrincipal(stageRoot);
        ViewFactory viewFacto = new ViewFactory();
        viewFacto.viewLogin();
    }
}
