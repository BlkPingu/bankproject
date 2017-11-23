package main;
import verarbeitung.Bank;
import verarbeitung.Kunde;

import java.time.LocalDate;
import java.time.Month;

/**
 * Created by Tobias on 22/11/17.
 */
public class Banktest {
    public static void main(String[] args) {
        Bank b = new Bank(1112131415);
        Kunde k1 = new Kunde("Max","Mustermann","Musterstr1", LocalDate.of(2001,7,3));
        Kunde k2 = new Kunde("Franz","Mustermann2","Musterstraße 1",LocalDate.of(2001,7,3));
        Kunde k3 = new Kunde("Leif","Mustermann3","Musterstraße 1",LocalDate.of(2001,7,3));
        Kunde k4 = new Kunde("Adi","Mustermann4","Musterstraße 1",LocalDate.of(2001,7,3));


        b.girokontoErstellen(k1);
        b.girokontoErstellen(k2);
        b.girokontoErstellen(k3);
        b.girokontoErstellen(k4);


        /*
        for(Konto k: kontoListe){
            System.out.println(k);
            k.setKontostand(-211);
        }
        b.pleitegeierSperren(1000);
        */

         /*
        for(Konto k: kontoliste){
            System.out.println(k);
            k.setKontostand(3000);
        }
        b.getAlleReichenKunden(2000);

        */

    }
}
