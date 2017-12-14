import org.junit.*;
import verarbeitung.*;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by Tobias on 02/11/17.
 */
public class BankTest {

    Bank b1 = new Bank(1112131415);
    Bank b2 = new Bank(123412445);

    @Test
    public void clonetest() throws Exception{
        b1.girokontoErstellen(Kunde.MUSTERMANN);
        b2 = b1.clone();

        b1.geldEinzahlen(10000001,200);
        b2.geldEinzahlen(10000001,100);
        assertNotEquals(b1.getKontostand(10000001), b2.getKontostand(10000001),0.1);

    }


    @Test
    public void clonetest2() throws Exception{
        b1.girokontoErstellen(Kunde.MUSTERMANN);
        b1.girokontoErstellen(Kunde.MUSTERMANN);
        b2 = b1.clone();

        assertTrue(b1.konten.containsKey(10000001L));
    }


    @Test
    public void getBankleitzahl() throws Exception {
        assertNotNull("Not NULL", b1.getBankleitzahl());
        assertEquals(1112131415,b1.bankleitzahl);
    }

    //funktioniert genau wie Sparbuch erstellen
    @Test
    public void girokontoErstellen() throws Exception {
        int vorErstellen =b1.getAlleKontonummern().size();
        b1.girokontoErstellen(Kunde.MUSTERMANN);
        int nachErstellen = b1.getAlleKontonummern().size();

        assertNotEquals(nachErstellen, vorErstellen);
    }


    //funktioniert wie einzahlen
    @Test
    public void geldAbheben() throws Exception {
        b1.girokontoErstellen(Kunde.MUSTERMANN);
        b1.geldEinzahlen(10000001L,1000.01);
        b1.geldAbheben(10000001L, 1000);
        assertEquals(0,b1.konten.get(10000001L).getKontostand(), 0.5);
    }


    @Test
    public void kontoLoeschen() throws Exception {
        b1.girokontoErstellen(Kunde.MUSTERMANN);


        b1.kontoLoeschen(10000001);


        assertEquals(0,b1.getAlleKontonummern().size());
    }

    @Test
    public void geldUeberweisen() throws Exception {

        b1.girokontoErstellen(Kunde.MUSTERMANN); //0
        b1.girokontoErstellen(Kunde.MUSTERMANN); //1
        b1.geldEinzahlen(b1.getAlleKontonummern().get(0),500);
        assertEquals(500,b1.getKontostand(b1.getAlleKontonummern().get(0)),0.5);
        b1.geldUeberweisen(b1.getAlleKontonummern().get(0),b1.getAlleKontonummern().get(1),500,"test");
        assertEquals(0,b1.getKontostand(b1.getAlleKontonummern().get(0)),0.5);
        assertEquals(500,b1.getKontostand(b1.getAlleKontonummern().get(1)),0.5);

    }

}