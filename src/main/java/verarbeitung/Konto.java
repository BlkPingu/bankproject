package verarbeitung;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * stellt ein allgemeines verarbeitung.Konto dar
 * @author ich wars
 */
public abstract class Konto
{
	/**
	 * Die verarbeitung.Waehrung
	 */
	private Waehrung w;
	/** 
	 * der Kontoinhaber
	 */
	private Kunde inhaber;

	/**
	 * die Kontonummer
	 */
	private final long nummer;

	/**
	 * der aktuelle Kontostand
	 */
	private double kontostand;

	public ArrayList<Kontoaktion> kontoauszug = new ArrayList<>();
	/**
	 * setzt den aktuellen Kontostand
	 * @param kontostand neuer Kontostand
	 */
	protected void setKontostand(double kontostand) 
	{
		this.kontostand = kontostand;
	}


	/**
	 * setzt den neue verarbeitung.Waehrung
	 */
	protected void setWaehrung(Waehrung w) 
	{
		this.w = w;
	}

	public final Waehrung getWaehrung() 
	{
		return this.w;
	} 

	/**
	 * Wenn das verarbeitung.Konto gesperrt ist (gesperrt = true), koennen keine Aktionen daran mehr vorgenommen werden,
	 * die zum Schaden des Kontoinhabers waeren (abheben, Inhaberwechsel)
	 */
	private boolean gesperrt;


	/**
	 * Setzt die beiden Eigenschaften kontoinhaber und kontonummer auf die angegebenen Werte,
	 * der anfaengliche Kontostand wird auf 0 gesetzt.
	 *
	 * @param inhaber verarbeitung.Kunde
	 * @param kontonummer long
	 * @throws IllegalArgumentException wenn der Inhaber null
	 */
	public Konto(Kunde inhaber, long kontonummer) {
		if(inhaber == null)
			throw new IllegalArgumentException("Inhaber darf nicht null sein!");
		this.inhaber = inhaber;
		this.nummer = kontonummer;
		this.kontostand = 0;
		this.gesperrt = false;
		this.w = Waehrung.EUR;
	}


	/**
	 * liefert den Kontoinhaber zurueck
	 * @return   verarbeitung.Kunde
	 */
	public final Kunde getInhaber() {
		return this.inhaber;
	}


	/**
	 * Liefert Kontoauszug als String
	 * @return
	 */
	public String getKontoauszug() {
		String k2 = null;
		for (int i = 0; i < kontoauszug.size(); i++) {
			String beschreibung = kontoauszug.get(i).getBeschreibung().toString();
			double aktionBetrag = kontoauszug.get(i).getBetrag();
			String betrag = Double.toString(aktionBetrag);
			String datum = kontoauszug.get(i).getDatum().toString();
			
			String k1 = betrag + " " + beschreibung + " " + datum;
			k2 += k1 + System.lineSeparator();
		}
		return k2;
	}


	/**
	 * entfernt alle Listeneintraege vor Datum vor aus kontoauszug
	 * @param vor
	 */
	public void alleEintraegeLoeschen(LocalDate vor){
		for (int i = 0; i < kontoauszug.size();i++){
			LocalDate datum = kontoauszug.get(i).getDatum();
			if (datum.isBefore(vor)) {
				kontoauszug.remove(i);
			}
		}
	}
	
	/**
	 * setzt den Kontoinhaber
	 * @param kinh   neuer Kontoinhaber
	 * @throws GesperrtException wenn das verarbeitung.Konto gesperrt ist
	 * @throws IllegalArgumentException wenn kinh null ist
	 */
	public final void setInhaber(Kunde kinh) throws GesperrtException{
		if (kinh == null)
			throw new IllegalArgumentException("Der Inhaber darf nicht null sein!");
		if(this.gesperrt)
			throw new GesperrtException(this.nummer);        
		this.inhaber = kinh;

	}
	
	/**
	 * liefert den aktuellen Kontostand
	 * @return   double
	 */
	public final double getKontostand() {
		return kontostand;
	}

	/**
	 * liefert die Kontonummer zurueck
	 * @return   long
	 */
	public final long getKontonummer() {
		return nummer;
	}

	/**
	 * liefert zurueck, ob das verarbeitung.Konto gesperrt ist oder nicht
	 * @return
	 */
	public final boolean isGesperrt() {
		return gesperrt;
	}
	
	/**
	 * Erhoeht den Kontostand um den eingezahlten Betrag.
	 *
	 * @param betrag double
	 * @throws IllegalArgumentException wenn der betrag negativ ist 
	 */
	public void einzahlen(double betrag, Waehrung w) {
		if (betrag < 0) {
			throw new IllegalArgumentException("Negativer Betrag");
		}
		setKontostand(getKontostand() + betrag);
		Kontoaktion k = new Kontoaktion("Abgebung", betrag, LocalDate.now());
		kontoauszug.add(k);
	}

	/**
	 *Liefert die aktuelle verarbeitung.Waehrung, in der das verarbeitung.Konto gefuehrt wird zurueck
	 */
	public Waehrung getAktuelleWaehrung()
	{
		return w;
	}
	/**
	 *Aendert Kontotyp(verarbeitung.Waehrung) und rechnet die Waehnung in neue verarbeitung.Waehrung um
	 */
	public void waehrungswechsel(Waehrung neu)
	{
  	// selbstdokumentierender code ist was fuer anfaenger
  		this.setKontostand(this.getKontostand() * this.getWaehrung().umrechnen(neu.getValue()));
  		this.setWaehrung(neu);
	}
	/**
	 *hebt den Betrag einer verarbeitung.Waehrung vom verarbeitung.Konto in verarbeitung.Waehrung ab,
	 *falls dieser nicht groesser als das Guthaben des Kontos ist.
	 */
	public boolean abheben(double betrag, Waehrung w) throws GesperrtException
	{
		double betragInWaehrung = w.umrechnen(betrag);
		if (betragInWaehrung < w.umrechnen(kontostand)) 
			return false;
		else setKontostand(getKontostand() - betragInWaehrung);
		Kontoaktion k = new Kontoaktion("Abgebung", betrag, LocalDate.now());
			kontoauszug.add(k);
			return true;
	}
	/**
	 * Gibt eine Zeichenkettendarstellung der Kontodaten zurueck.
	 */
	@Override
	public String toString() {
		String ausgabe;
		ausgabe = "Kontonummer: " + this.getKontonummerFormatiert()
				+ System.getProperty("line.separator");
		ausgabe += "Inhaber: " + this.inhaber;
		ausgabe += "Aktueller Kontostand: " + this.kontostand + " Euro ";
		ausgabe += this.getGesperrtText() + System.getProperty("line.separator");
		return ausgabe;
	}

	/**
	 * sperrt das verarbeitung.Konto, Aktionen zum Schaden des Benutzers sind nicht mehr moeglich.
	 */
	public final void sperren() {
		this.gesperrt = true;
	}

	/**
	 * entsperrt das verarbeitung.Konto, alle Kontoaktionen sind wieder moeglich.
	 */
	public final void entsperren() {
		this.gesperrt = false;
	}
	
	
	/**
	 * liefert eine String-Ausgabe, wenn das verarbeitung.Konto gesperrt ist
	 * @return "GESPERRT", wenn das verarbeitung.Konto gesperrt ist, ansonsten ""
	 */
	public final String getGesperrtText()
	{
		if (this.gesperrt)
		{
			return "GESPERRT";
		}
		else
		{
			return "";
		}
	}
	
	/**
	 * liefert die ordentlich formatierte Kontonummer
	 * @return auf 10 Stellen formatierte Kontonummer
	 */
	public String getKontonummerFormatiert()
	{
		return String.format("%10d", this.nummer);
	}
	
	/**
	 * liefert den ordentlich formatierten Kontostand
	 * @return formatierter Kontostand mit 2 Nachkommastellen und Waehrungssymbol Euro
	 */
	public String getKontostandFormatiert()
	{
		return String.format("%10.2f Euro" , this.getKontostand());
	}
	/**
	 * Vergleich von this mit other; Zwei Konten gelten als gleich,
	 * wen sie die gleiche Kontonummer haben
	 * @param other
	 * @return true, wenn beide Konten die gleiche Nummer haben
	 */
	@Override
	public boolean equals(Object other)
	{
		if(this == other)
			return true;
		if(other == null)
			return false;
		if(this.getClass() != other.getClass())
			return false;
		if(this.nummer == ((Konto)other).nummer)
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode()
	{
		return 31 + (int) (this.nummer ^ (this.nummer >>> 32));
	}

	/*
	public void aufKonsole()
	{
		System.out.println(this.toString());
	}
	*/
}
