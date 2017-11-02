package verarbeitung;

import java.util.*;

/**
 * Created by Tobias on 29/10/17.
 */
public class Bank {
    public long bankleitzahl;
    long aktuelleKontonummer=10000000;
    public HashMap<Long, Konto> konten = new HashMap<>();

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
        return this.bankleitzahl;
    }

    /**
     * Getter fuer Kontostand von Konto mit ID Nummer
     * @param nummer
     * @return Kontostand von Konto mit angegebener Nummer
     */
    public double getKontostand(long nummer) {
        return konten.get(nummer).getKontostand();
    }
    /**
     * gibt alle Kontoen zurueck
     * @return Liste mit allen Konten
     */
    public String getAlleKonten(){
        return konten.keySet().toString();
    }

    public long neueKontonummer() {
        List<Long> kontonummern = new ArrayList<Long>(konten.keySet());
        aktuelleKontonummer++;
        while (kontonummern.contains(aktuelleKontonummer)){
            aktuelleKontonummer++;

                if(aktuelleKontonummer>=99999999){
                    aktuelleKontonummer=10000000;
                }
        }
        return aktuelleKontonummer;
    }
    /**
     * Erstellt:
     * -Girokonto fuer angebebenen Kunden (mit neuer einzigartiger ID)
     * -Speichert neues Girokonto in Kontenliste
     * @param inhaber
     * @return vergebene Kontonummer
     */
    public long girokontoErstellen (Kunde inhaber){
        long neueGiroKontoNr= neueKontonummer();
        konten.put(neueGiroKontoNr,new Girokonto(inhaber,neueGiroKontoNr, 500));
        //finde Ich ja nicht noetig die Kontonummer zweimal zu speichern, aber Ich programmiere hier ja nur
        return neueGiroKontoNr;
    }
    /**
     * Erstellt:
     * -Sparbuch fuer angebebenen Kunden (mit neuer einzigartiger ID)
     * -Speichert neues Sparbuch in Kontenliste
     * @param inhaber
     * @return vergebene Kontonummer
     */
    public long sparbuchErstellen(Kunde inhaber){
        long neueGiroKontoNr= neueKontonummer();
        konten.put(neueGiroKontoNr,new Sparbuch(inhaber,neueGiroKontoNr));
        //finde Ich ja nicht noetig die Kontonummer zweimal zu speichern, aber Ich programmiere hier ja nur
        return neueGiroKontoNr;
    }

    /**
     * gibt alle Kontonummern
     * @return
     */
    public List<Long> getAlleKontonummern(){
        List<Long> kontonummern = new ArrayList<Long>(konten.keySet());
        return kontonummern;
    }

    /**
     * Hebt Betrag betrag von Konto mit Kontonummer von ab
     * @param von
     * @param betrag
     * @return true/false stellvertretend für Erfolg/Misserfolg
     */
    public boolean geldAbheben(long von, double betrag) throws GesperrtException {
        if(konten.containsKey(von) == true) {
            return konten.get(von).abheben(betrag,Waehrung.EUR);
        } else return false;
    }

    /**
     * Zahlt Betrag betrag auf Konto mit Kontonummer auf ein
     * @param auf
     * @param betrag
     */
    public void geldEinzahlen(long auf, double betrag){
        if(konten.containsKey(auf) == true) {
            konten.get(auf).einzahlen(betrag,Waehrung.EUR);
        }
    }

    /**
     * Loescht Konto von Konto mit Kontonummer nummer
     * @param nummer
     * @return true/false fuer Erfolg/Misserfolg
     */
    public boolean kontoLoeschen(long nummer){
        if(konten.containsKey(nummer) == true){
            konten.remove(nummer);
            return true;
        }else return false;
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
        if (konten.containsKey(vonKontonr)== true & konten.containsKey(nachKontonr) == true){
            double vonkontostand = konten.get(vonKontonr).getKontostand();
            konten.get(vonKontonr).setKontostand(vonkontostand -= betrag);

            double nachKontostand = konten.get(nachKontonr).getKontostand();
            konten.get(vonKontonr).setKontostand(nachKontostand += betrag);
            return true;
        }else return false;
    }


}
