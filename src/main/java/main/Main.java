package main;
import verarbeitung.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent kontoOberflaeche = FXMLLoader.load(getClass().getResource("/KontoOberflaeche.fxml"));

        //Parent kontoOberflaeche = kontoController.getKontoOberflaeche();
        primaryStage.setTitle("Uebung13");
        primaryStage.setScene(new Scene(kontoOberflaeche, 400, 300));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}