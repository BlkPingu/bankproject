package verarbeitung;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * ein verarbeitung.Sparbuch
 * @author Doro
 *
 */
public class Sparbuch extends Konto implements Serializable{
	/**
	 * Zinssatz, mit dem das verarbeitung.Sparbuch verzinst wird. 0,03 entspricht 3%
	 */
	private double zinssatz;
	
	/**
	 * Monatlich erlaubter Gesamtbetrag fuer Abhebungen
	 */
	public static final double ABHEBESUMME = 2000;
	
	/**
	 * Betrag, der im aktuellen Monat bereits abgehoben wurde
	 */
	private double bereitsAbgehoben = 0;
	/**
	 * Monat und Jahr der letzten Abhebung
	 */
	private LocalDate zeitpunkt = LocalDate.now();


	public Sparbuch(Kunde inhaber, long kontonummer) {
		super(inhaber, kontonummer);
		zinssatz = 0.03;
	}
	
	@Override
	public String toString()
	{
    	String ausgabe = "-- SPARBUCH --" + System.lineSeparator() +
    	super.toString()
    	+ "Zinssatz: " + this.zinssatz * 100 +"%" + System.lineSeparator();
    	return ausgabe;
	}

	@Override
	protected boolean canAbheben(double betrag) {
		return false;
	}

	@Override
	protected void successfulAbhebungHook(double betrag) {

	}

	@Override
	public boolean abheben (double betrag, Waehrung waehrung) throws GesperrtException{
		if (betrag < 0 ) {
			throw new IllegalArgumentException();
		}
		if(this.isGesperrt())
		{
			GesperrtException e = new GesperrtException(this.getKontonummer());
			throw e;
		}
		LocalDate heute = LocalDate.now();
		if(heute.getMonth() != zeitpunkt.getMonth() || heute.getYear() != zeitpunkt.getYear())
		{
			this.bereitsAbgehoben = 0;
		}
		if (getKontostand() - betrag >= 0.50 &&
				bereitsAbgehoben + betrag <= Sparbuch.ABHEBESUMME)
		{
			double toRetrieve = betrag * this.getWaehrung().umrechnen(1.0);
			this.zeitpunkt = LocalDate.now();
			bereitsAbgehoben += toRetrieve;
			setKontostand(this.getKontostand() - toRetrieve);
			return true;
		}
		else {
			return false;
		}
	}
}
