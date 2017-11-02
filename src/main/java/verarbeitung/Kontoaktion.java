package verarbeitung;

import java.time.LocalDate;

/**
 * Created by Tobias on 29/10/17.
 */
public class Kontoaktion{
    String beschreibung;
    double betrag;
    LocalDate datum;

    public Kontoaktion(String beschreibung, double betrag, LocalDate datum){
        this.beschreibung = beschreibung;
        this.betrag = betrag;
        this.datum = datum;
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
