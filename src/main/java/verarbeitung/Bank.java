package verarbeitung;

import java.util.List;

/**
 * Created by Tobias on 29/10/17.
 */
public class Bank {
    public long bankleitzahl;
    public double kontostand;

    /**
     * erstellt Bank und ihre Bankleitzahl als identifizierenden Schlüssel (ID)
     * @param bankleitzahl
     */
    public Bank(long bankleitzahl){
        this.bankleitzahl= bankleitzahl;
    }

    /**
     * Getter fuer Bankleitzahl von Bank
     * @return die Bankleitzahl
     */
    public long getBankleitzahl() {
        return bankleitzahl;
    }

    /**
     * Getter fuer Kontostand von Konto mit ID Nummer
     * @param nummer
     * @return Kontostand von Konto mit angegebener Nummer
     */
    public double getKontostand(long nummer) {
        return kontostand;
    }
    /**
     * gibt alle Kontoen zurueck
     * @return Liste mit allen Konten
     */
    public String getAlleKonten(){

    }

    /**
     * Erstellt:
     * -Girokonto fuer angebebenen Kunden (mit neuer einzigartiger ID)
     * -Speichert neues Girokonto in Kontenliste
     * @param inhaber
     * @return vergebene Kontonummer
     */
    public long girokontoErstellen (Kunde inhaber){

    }
    /**
     * Erstellt:
     * -Sparbuch fuer angebebenen Kunden (mit neuer einzigartiger ID)
     * -Speichert neues Sparbuch in Kontenliste
     * @param inhaber
     * @return vergebene Kontonummer
     */
    public long sparbuchErstellen(Kunde inhaber){

    }

    /**
     * gibt alle Kontonummern
     * @return
     */
    public List<Long> getAlleKontonummern(){

    }

    /**
     * Hebt Betrag betrag von Konto mit Kontonummer von ab
     * @param von
     * @param betrag
     * @return true/false stellvertretend für Erfolg/Misserfolg
     */
    public boolean geldAbheben(long von, double betrag){

    }

    /**
     * Zahlt Betrag betrag auf Konto mit Kontonummer auf ein
     * @param auf
     * @param betrag
     */
    public void getEinzahlen(long auf, double betrag){

    }

    /**
     * Loescht Konto von Konto mit Kontonummer nummer
     * @param nummer
     * @return true/false fuer Erfolg/Misserfolg
     */
    public boolean kontoLoeschen(long nummer){

    }
    /**
     * Ueberweist einen Betrag betrag von Konto mit Kontonummer vonKontonr nach Konto mit Kontonummer nachKontonr
     * versehen mit einem Verwendungszweck als String verwendungszweck
     * @param vonKontonr
     * @param nachKontonr
     * @param betrag
     * @param verwendungszweck
     * @return true/false fuer Erfolg oder Misserfolg
     */
    public boolean geldUeberweisen(long vonKontonr, long nachKontonr,double betrag, String verwendungszweck){

    }


}
