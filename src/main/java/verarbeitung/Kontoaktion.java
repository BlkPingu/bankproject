package verarbeitung;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Tobias on 29/10/17.
 */
public class Kontoaktion implements Serializable{
    String beschreibung;
    double betrag;
    LocalDate datum;

    public Kontoaktion(String beschreibung, double betrag, LocalDate datum){
        this.beschreibung = beschreibung;
        this.betrag = betrag;
        this.datum = datum;
    }

    public double getBetrag() {
        return betrag;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

}
