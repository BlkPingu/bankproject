import java.time.LocalDate;
import verarbeitung.*;


/**
 * Testprogramm fuer Konten
 * @author Doro
 *
 */
public class Kontentest {

	/**
	 * Testprogramm fuer Konten
	 * @param args wird nicht benutzt
	 */
	public static void main(String[] args) {
		Kunde ich = new Kunde("Dorothea", "Hubrich", "zuhause", LocalDate.parse("1976-07-13"));
		
		// verarbeitung.Konto k = new verarbeitung.Konto();
		
		
		Girokonto meinGiro = new Girokonto(ich, 1234, 1000.0);
		meinGiro.einzahlen(50, Waehrung.EUR);
		System.out.println(meinGiro);
		
		Sparbuch meinSpar = new Sparbuch(ich, 9876);
		meinSpar.einzahlen(50,Waehrung.EUR);
		try
		{
			boolean hatGeklappt = meinSpar.abheben(70);
			System.out.println("Abhebung hat geklappt: " + hatGeklappt);
			System.out.println(meinSpar);
		}
		catch (GesperrtException e)
		{
			System.out.println("Zugriff auf gesperrtes verarbeitung.Konto - Polizei rufen!");
		}
		
		Konto k = new Girokonto();  //kontostand muesste 0 sein
		try {
			k.abheben(100);  //-100
			//k.aufKonsole();
		} catch (GesperrtException e) {}
		
		int a= 100;
		int b = a;  //Kopie
		a = a+50;
		System.out.println(b);  // 100
		
		Konto zweites = k;  //Referenz, keine Kopie
		k.einzahlen(300,Waehrung.EUR);  //200
		System.out.println(zweites);   //hier auch 200!!!!
		
		ich.setAdresse("woandershin");
		System.out.println(meinGiro);
		
		Kontoart art, art2 = Kontoart.SPARBUCH;
		art = Kontoart.GIROKONTO;
		if(art == Kontoart.GIROKONTO)
		{ 
			System.out.println("Sie wollen ein verarbeitung.Girokonto!");
		}
		else
			System.out.println("Sie kriegen ein verarbeitung.Sparbuch!");
		
		System.out.println(art.name()); // "GIROKONTO"
		System.out.println(art.ordinal()); // 0
		
		art = Kontoart.valueOf("FESTGELDKONTO");
		System.out.println(art.getBeschreibung()); // "nur fuer die Fantasie
		
		System.out.println("Unser Prospekt:");
		for(int i=0; i < Kontoart.values().length; i++)
		{
			System.out.println(Kontoart.values()[i]);
		}

	}

}
