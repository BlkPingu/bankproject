package verarbeitung;

import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Tobias on 29/10/17.
 */
public class Bank implements Serializable, Cloneable{
    public long bankleitzahl;
    long aktuelleKontonummer=10000000;
    public HashMap<Long, Konto> konten = new HashMap<>();
    private List<Konto> kontoListe = new ArrayList<>(konten.values());

    private List<Kunde> vollesKontoKunden = new ArrayList<>();
    private List<Kunde> reicheKunden = new ArrayList<>();

    /*
    File path = new File("bankData.ser");
    File clearTextBank = new File("clearBankData.ser");


    public void clone(File path, Bank b) throws IOException,ClassNotFoundException{
        OutputStream outStream = new FileOutputStream(path);
        outStream.write(Serializer.serialize(this));
        outStream.close();


        InputStream inputStream = new FileInputStream(path);
        inputStream.read(Serializer.deserialize());
        InputStream ois = new ObjectInputStream(inputStream);
        ois.close();

    }


    public void toFile() throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(clearTextBank);
        printWriter.printf("Bank: %i", KontoNummer);

        printWriter.printf

    }
    */




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

    /**
     * sperrt Kunden mit negativem Kontostand
     */
    public void pleitegeierSperren(){
        konten.values().stream().filter(konto -> konto.getKontostand()<0).forEach(konto -> konto.sperren());
    }

    /**
     * gibt Liste mit Kunden deren Kontostand groeßer/gleich  minimum ist zurueck
     * @param minimum
     * @return Liste mit groeßer/gleich minimum auf dem Konto
     */
    public List<Kunde> getKundenMitVollemKonto(double minimum){
        konten.values().stream()
                .filter(k -> k.getKontostand()>=minimum)
                .forEach(k -> vollesKontoKunden.add(k.getInhaber()));
        return vollesKontoKunden;
    }

    @Override
    public Bank clone(){
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            try(ObjectOutputStream oos = new ObjectOutputStream(baos)){
                oos.writeObject(this);
            }
            byte[] baosArray = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(baosArray);
            ObjectInputStream oois = new ObjectInputStream(bais);
            return (Bank) oois.readObject();
        }catch(Exception e){}
        return null;
    }

    //getAlleReichenKunden(double minimum)

    //Idee:
    // 1) Einzigartige Inhaber der Konten aus konten in Hashmap<Kunde, Double> "Kundenwert" speichen mit Guthaben 0.
    // 2) Jeden eintrag in konten mit getKontostand nach Guthaben durchsuchen und dem Key in "Kundenwert als Value aufaddieren.
    // 3) Kundenwert nach Keys (Kunden) durchsuchen die eine Value > minimum haben. Namen als Liste "reicheKunden" zurueckgeben.

    //Issues:
    //Probleme mit Hashmap Kundenwert. Hashmap hat auf aufruf
    // des Keys mit Kundenwert.get() ein Double zurueckgegeben.

    /*
    public List<Kunde> getAlleReichenKunden(double minimum){
        int gesamtwert;

        Map<Kunde, Double> Kundenwert = new HashMap<>();

        for (Konto value : konten.values()) {
            if(Kundenwert.containsKey(value.getInhaber())){

            }
            else {
                Kundenwert.put(value.getInhaber(), 0.0);
            }
        }
        for(Konto : kontoListe){
            Kundenwert.get konto.getKontostand()
        }

        return ;
    }
    */

    public List<Kunde> getAlleReichenKunden(double minimum){
        return konten.values().stream().collect(Collectors.groupingBy
                                                                (Konto::getInhaber))
                .entrySet().stream()
                .filter(
                        kundeListEntry -> kundeListEntry.getValue().stream()
                        .map(Konto::getKontostand)
                        .reduce(0.0, (a,b) -> a + b)
                        >= minimum
                )
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
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
     * Ueberweist einen Betrag betrag von Konto mit Kontonummer vonKontonr nach Konto mit Kontonummer nach
     * versehen mit einem Verwendungszweck als String verwendungszweck
     * @param von
     * @param nach
     * @param betrag
     * @param verwendungszweck
     * @return true/false fuer Erfolg oder Misserfolg
     */
    public boolean geldUeberweisen(long von, long nach,double betrag, String verwendungszweck){
        if (this.konten.containsKey(von) & konten.containsKey(nach)){
            if (this.konten.get(von).getKontostand() >= betrag) {


                this.konten.get(von).setKontostand(konten.get(von).getKontostand() - betrag);

                this.konten.get(nach).setKontostand(konten.get(nach).getKontostand() + betrag);

                return true;
            }else return false;
        }else return false;
    }


}
