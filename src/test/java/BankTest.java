import org.junit.Test;
import org.mockito.Mockito;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import verarbeitung.*;

import java.util.List;

public class BankTest {

    @Test
    public void girokontoErstellenTest(){
        Kunde k = Mockito.mock(Kunde.class);
        Bank bank = new Bank(1234);
        Konto girokonto = Mockito.mock(Girokonto.class);
        long kontoNr = bank.kontoErstellen(new Kontofabrik(girokonto), k);
        assert(bank.getAlleKontonummern().isEmpty() == false);
        assert(bank.getAlleKontonummern().get(0) == kontoNr);
        assert(bank.findKontoMitKontoNummer(kontoNr) instanceof Girokonto);
    }


    @Test
    public void sparbuchErstellenTest(){
        Kunde k = Mockito.mock(Kunde.class);
        Bank bank = new Bank(1234);
        Konto sparbuch = Mockito.mock(Sparbuch.class);
        long kontoNr = bank.kontoErstellen(new Kontofabrik(sparbuch), k);
        assert(bank.getAlleKontonummern().isEmpty() == false);
        assert(bank.getAlleKontonummern().get(0) == kontoNr);
        assert(bank.findKontoMitKontoNummer(kontoNr) instanceof Sparbuch);
    }

    @Test
    public void getAlleKontenTest(){
        Bank bank = new Bank(1234);

        Kunde k1 = Mockito.mock(Kunde.class);
        Konto girokonto = Mockito.mock(Girokonto.class);
        bank.kontoErstellen(new Kontofabrik(girokonto), k1);

        Kunde k2 = Mockito.mock(Kunde.class);
        Konto sparbuch = Mockito.mock(Sparbuch.class);
        bank.kontoErstellen(new Kontofabrik(sparbuch), k2);

        Mockito.when(girokonto.getKontonummerFormatiert()).
                thenReturn("12345");
        Mockito.when(girokonto.getKontostandFormatiert()).
                thenReturn("-50");

        Mockito.when(sparbuch.getKontonummerFormatiert()).
                thenReturn("54321");
        Mockito.when(sparbuch.getKontostandFormatiert()).
                thenReturn("100");

        System.out.println(bank.getAlleKonten());
        assert(bank.getAlleKonten().contains("12345 -50"));
        assert(bank.getAlleKonten().contains("54321 100"));
    }

    @Test
    public void getAlleKontonummern(){
        Bank bank = new Bank(1234);

        long KontoNr = 54256;
        Konto girokonto = Mockito.mock(Girokonto.class);
        Mockito.when(girokonto.getKontonummer()).thenReturn(KontoNr);
        bank.kontoErstellen(new Kontofabrik(girokonto), Mockito.mock(Kunde.class));

        long KontoNr2 = 97457;
        Konto girokonto2 = Mockito.mock(Girokonto.class);
        Mockito.when(Mockito.mock(Girokonto.class).getKontonummer()).thenReturn(KontoNr2);
        bank.kontoErstellen(new Kontofabrik(girokonto2), Mockito.mock(Kunde.class));


        List<Long> kontoNrList = bank.getAlleKontonummern();
        //assert(kontoNrList.size() != 2);
        //assert(kontoNrList.get(0) == KontoNr);
        //assert(kontoNrList.get(1) == KontoNr2);
    }


    @Test
    public void getdAbhebenUndEinzahlen() throws GesperrtException, NotFound {
        Bank bank = new Bank(1234);
        Kunde k1 = Mockito.mock(Kunde.class);
        Konto girokonto = Mockito.mock(Girokonto.class);
        long KontoNr = 54256;
        Mockito.when(girokonto.getKontonummer()).thenReturn(KontoNr);
        Mockito.when(girokonto.abheben(200)).thenReturn(true);

        bank.kontoErstellen(new Kontofabrik(girokonto), k1);

        bank.geldEinzahlen(KontoNr, 500);
        //assert(bank.geldAbheben(KontoNr, 200) == true);
    }

    @Test
    public void kontoLoeschen(){
        Bank bank = new Bank(1234);
        Kunde k1 = Mockito.mock(Kunde.class);
        Konto girokonto = Mockito.mock(Girokonto.class);
        long KontoNr = 54256;
        Mockito.when(girokonto.getKontonummer()).thenReturn(KontoNr);


        bank.kontoErstellen(new Kontofabrik(girokonto), k1);

        //assert(bank.kontoLoeschen(-54321) == false);
        //assert(bank.kontoLoeschen(KontoNr) == true);
        //assert(bank.getAlleKontonummern().size() == 0);
    }

    @Test
    public void getKontostand() throws NotFound, GesperrtException{
        Bank bank = new Bank(1234);
        Kunde k1 = Mockito.mock(Kunde.class);
        Konto girokonto = Mockito.mock(Girokonto.class);
        long KontoNr = 54256;
        Mockito.when(girokonto.getKontonummer()).thenReturn(KontoNr);
        Mockito.when(girokonto.getKontostand()).thenReturn((double)0);
        bank.kontoErstellen(new Kontofabrik(girokonto), k1);

        //assert(bank.getKontostand(KontoNr) == 0);
        bank.geldEinzahlen(KontoNr, 500);
        Mockito.when(girokonto.getKontostand()).thenReturn((double)500);
        //assert(bank.getKontostand(KontoNr) == 500);
    }

}
