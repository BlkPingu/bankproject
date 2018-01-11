package verarbeitung;

import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class KontoController {
    public Konto konto = new Girokonto();

    public Text nummer;
    public Text stand;
    public CheckBox gesperrt;
    public TextArea adresse;
    public Spinner betrag;
    public Button abheben;
    public Button einzahlen;

    public KontoController(){
        konto.getInhaber().setAdresse("Vom Lande 123");
        konto.einzahlen(1000);
    }

    @FXML
    public void initialize(){
        // konnte leider die Bindings aus fxml heraus nicht zum laufen bekommen
        nummer.setText(konto.getKontonummerFormatiert());
        stand.textProperty().bind(konto.getKontostandReadOnlyProperty().asString());
        konto.isKontoImMinus.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                stand.setFill(Color.RED);
            }
            else{
                stand.setFill(Color.BLACK);
            }
        });
        gesperrt.selectedProperty().bindBidirectional(konto.gesperrtProperty);
        adresse.textProperty().bindBidirectional(konto.getInhaber().getAdressStringProperty());
    }


    public void einzahlenClicked(MouseEvent mouseEvent) {
        try {
            konto.einzahlen((double)betrag.getValue());
        }
        catch(Exception ex){
            new Alert(Alert.AlertType.ERROR, "Das Einzahlen war nicht erfolgreich").showAndWait();
        }
    }


    public void abhebenClicked(MouseEvent mouseEvent) {

        try {
            if(!konto.abheben((double)betrag.getValue())){
                new Alert(Alert.AlertType.ERROR, "Das Abheben war nicht erfolgreich").showAndWait();
            }
        }
        catch (GesperrtException ex){
            new Alert(Alert.AlertType.ERROR, "Das Konto wurde gesperrt!").showAndWait();
        }
    }
}
