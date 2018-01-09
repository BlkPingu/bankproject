package main;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import verarbeitung.GesperrtException;
import verarbeitung.Girokonto;
import verarbeitung.KontoOberflaeche;
import verarbeitung.Kunde;

import java.time.LocalDate;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage){
        Parent root = new KontoOberflaeche();
        Scene scene = new Scene(root,300,275);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bankprogramm");
        primaryStage.show();


        private Kunde kunde = new Kunde("hans", "franz" , "Landweg 1", LocalDate.now());
        private Girokonto konto = new Girokonto(kunde,000001111,500);


        protected void einzahlen(double betrag){
            this.konto.einzahlen(betrag);
    }

    protected void auszahlen(double betrag){
            boolean geklappt = false;
            try {
                geklappt = this.konto.abheben(betrag);
            }(catch GesperrtException e) {geklappt = false;}

    }




    }

    public static void main(String[] args) {
        launch(args);
    }
}
