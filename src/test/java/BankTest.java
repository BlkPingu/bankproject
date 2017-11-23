import org.junit.Test;

import verarbeitung.*;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by Tobias on 02/11/17.
 */
public class BankTest {

    Bank b = new Bank(1112131415);
    Kunde k1 = new Kunde("Max","Mustermann","Musterstraße 1", LocalDate.of(2000,3,1));

    @Test
    public void getBankleitzahl() throws Exception {
        assertNotNull("Not NULL", b.getBankleitzahl());
        assertEquals(1112131415,b.bankleitzahl);
    }

    //funktioniert genau wie Sparbuch erstellen
    @Test
    public void girokontoErstellen() throws Exception {
        int vorErstellen =b.getAlleKontonummern().size();
        b.girokontoErstellen(k1);
        int nachErstellen = b.getAlleKontonummern().size();

        assertNotEquals(nachErstellen, vorErstellen);
    }


    //funktioniert wie einzahlen
    @Test
    public void geldAbheben() throws Exception {
        b.girokontoErstellen(k1);
        b.geldEinzahlen(10000000,1000);
        b.geldAbheben(10000000, 1000);
        assertNotEquals(0,b.konten.get(10000000).getKontostand());
    }


    @Test
    public void kontoLoeschen() throws Exception {
        b.girokontoErstellen(k1);
        b.kontoLoeschen(b.getAlleKontonummern().get(1));
        assertEquals(0,b.getAlleKontonummern().size());
    }

    @Test
    public void geldUeberweisen() throws Exception {
        b.girokontoErstellen(k1);
        b.girokontoErstellen(k1);
        b.geldEinzahlen(b.getAlleKontonummern().get(1),500);
        double Kontostand1 = b.getKontostand(b.getAlleKontonummern().get(1));
        assertEquals(0,Kontostand1,0.1);


        b.geldUeberweisen(b.getAlleKontonummern().get(1),b.getAlleKontonummern().get(2),500,"Test");

        double kontostand2 = b.getKontostand(b.getAlleKontonummern().get(1));
        double kontostand3 = b.getKontostand(b.getAlleKontonummern().get(2));
        assertEquals(0,kontostand2,0.1);
        assertEquals(500,kontostand3,0.1);

    }

}