package verarbeitung;

import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.util.*;
import java.util.stream.Collectors;

public class Bank {
    private long bankleitzahl;
    private List<Konto> alleKontenList = new ArrayList<Konto>();

    public Bank(long bankleitzahl){
        this.bankleitzahl = bankleitzahl;
    }

    public long getBankleitzahl(){
        return this.bankleitzahl;
    }

    public void pleitegeierSperren(){
        alleKontenList.stream().forEach((konto) -> {
            if(konto.getKontostand() < 0){
                konto.sperren();
            }
        });
    }

    public List<Konto> getKundenMitVollemKonto(double minimum){
        return alleKontenList.stream()
                .filter((konto) -> konto.getKontostand() >= minimum)
                .collect(Collectors.toList());
    }

    public long kontoErstellen(Kontofabrik fabrik, Kunde inhaber){
        long newKontoNummer = this.createNewKontoNummer();
        Konto konto = fabrik.getKonto();
        alleKontenList.add(konto);
        return konto.getKontonummer();
    }



    public String getAlleKonten(){
        String res = "";
        for(Konto konto : this.alleKontenList){
            res += konto.getKontonummerFormatiert() + " " + konto.getKontostandFormatiert() + System.lineSeparator();
        }
        return res;
    }
    public List<Long> getAlleKontonummern(){
        List<Long> alleKontonummernList = new ArrayList<Long>();
        for(Konto konto : alleKontenList){
            alleKontonummernList.add(konto.getKontonummer());
        }
        return alleKontonummernList;
    }

    public boolean geldAbheben(long von, double betrag) throws GesperrtException {
        Konto konto = findKontoMitKontoNummer(von);
        return konto != null && konto.abheben(betrag);
    }

    public void geldEinzahlen(long von, double betrag) throws GesperrtException, NotFound {
        Konto konto = findKontoMitKontoNummer(von);
        if(konto == null){
            throw new NotFound();
        }
        else {
            konto.einzahlen(betrag);
        }
    }

    public boolean kontoLoeschen(long nummer){
        Konto konto = findKontoMitKontoNummer(nummer);
        return konto != null && alleKontenList.remove(konto);
    }
    public double getKontostand(long nummer) throws NotFound{
        Konto konto = findKontoMitKontoNummer(nummer);
        if(konto == null){
            throw new NotFound();
        }
        return konto.getKontostand();
    }

    public boolean geldUeberweisen(long vonKontonr, long nachKontor, double betrag, String Verwendungszweck){
        Konto vonKonto = this.findKontoMitKontoNummer(vonKontonr);
        Konto nachKonto = this.findKontoMitKontoNummer(nachKontor);
        if(vonKonto == null || nachKonto == null || vonKonto instanceof Girokonto == false || nachKonto instanceof Girokonto == false ||
                vonKonto.isGesperrt() || nachKonto.isGesperrt() || vonKonto.getKontostand() < betrag){
            return false;
        }
        vonKonto.setKontostand(vonKonto.getKontostand() - betrag);
        nachKonto.setKontostand(nachKonto.getKontostand() + betrag);
        return true;
    }

    public Konto findKontoMitKontoNummer(long kontoNummer){
        for(Konto konto : alleKontenList){
            if(konto.getKontonummer() == kontoNummer){
                return konto;
            }
        }
        return null;
    }
    private long createNewKontoNummer(){
        long newKontoNummer = -1;
        Random rnd = new Random();
        boolean foundNewKontoNummer;
        do{
            newKontoNummer = rnd.nextLong();
            foundNewKontoNummer = true;
            for (Konto konto: alleKontenList) {
                if(konto.getKontonummer() == newKontoNummer){
                    foundNewKontoNummer = false;
                }
            }
        } while(foundNewKontoNummer == false);
        return newKontoNummer;
    }

}
