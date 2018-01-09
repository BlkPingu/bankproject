package verarbeitung;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.*;

public abstract class Konto implements Serializable{

	private Waehrung w;
	private Kunde inhaber;
	private final long nummer;
	private ReadOnlyDoubleWrapper kontostand;
	private BooleanProperty gesperrt;
	private BooleanProperty imMinus = new SimpleBooleanProperty(false);

	public ArrayList<Kontoaktion> kontoauszug = new ArrayList<>();

	protected void setKontostand(double kontostand)
	{
		this.kontostand.set(kontostand);
		this.imMinus.set(this.kontostand.doubleValue() < 0);
	}

	protected void setWaehrung(Waehrung w)
	{
		this.w = w;
	}

	public final Waehrung getWaehrung()
	{
		return this.w;
	}

	public Konto(Kunde inhaber, long kontonummer) {
		if(inhaber == null)
			throw new IllegalArgumentException("Inhaber darf nicht null sein!");
		this.inhaber = inhaber;
		this.nummer = kontonummer;
		this.kontostand.setValue(0);
		this.gesperrt.setValue(false);
		this.w = Waehrung.EUR;
	}

	public void setImMinus(boolean imMinus) {
		this.imMinus.setValue(imMinus);
	}

	public boolean isImMinus() {
		return this.imMinus.getValue();
	}

	public void minusErmitteln(double kontostand){
		if (kontostand < 0){
			setImMinus(true);
		}
		else setImMinus(false);
	}

	public final boolean getImMinusProperty() {
		return imMinus.get();
	}

	public BooleanProperty imMinusProperty(){
		return this.imMinus;
	}

	public final boolean getgesperrt() {
		return gesperrt.get();
	}

	public BooleanProperty gesperrtProperty(){
		return this.gesperrt;
	}


	public final double getkontostandProperty() {
		if (kontostand != null)
			return kontostand.get();
		return 0;
	}

	public final ReadOnlyDoubleProperty kontostandProperty(){
		return this.kontostand.getReadOnlyProperty();
	}

	public final Kunde getInhaber() {
		return this.inhaber;
	}

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
	public void alleEintraegeLoeschen(LocalDate vor){
		for (int i = 0; i < kontoauszug.size();i++){
			LocalDate datum = kontoauszug.get(i).getDatum();
			if (datum.isBefore(vor)) {
				kontoauszug.remove(i);
			}
		}
	}
	public final void setInhaber(Kunde kinh) throws GesperrtException{
		if (kinh == null)
			throw new IllegalArgumentException("Der Inhaber darf nicht null sein!");
		if(this.gesperrt)
			throw new GesperrtException(this.nummer);
		this.inhaber = kinh;

	}
	public final double getKontostand() {
		return this.kontostand.get();
	}
	public final long getKontonummer() {
		return nummer;
	}
	public final boolean isGesperrt() {
		return this.gesperrt.getValue();

	}
	public void einzahlen(double betrag, Waehrung w) {
		if (betrag < 0) {
			throw new IllegalArgumentException("Negativer Betrag");
		}
		setKontostand(getKontostand() + betrag);
		Kontoaktion k = new Kontoaktion("Abgebung", betrag, LocalDate.now());
		kontoauszug.add(k);
	}

	public Waehrung getAktuelleWaehrung()
	{
		return w;
	}

	public void waehrungswechsel(Waehrung neu)
	{
		// selbstdokumentierender code ist was fuer anfaenger
		this.setKontostand(this.getKontostand() * this.getWaehrung().umrechnen(neu.getValue()));
		this.setWaehrung(neu);
	}



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

	public final void sperren() {
		this.gesperrt = true;
	}

	public final void entsperren() {
		this.gesperrt = false;
	}

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

	public String getKontonummerFormatiert()
	{
		return String.format("%10d", this.nummer);
	}

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

	abstract boolean abheben(double betrag, Waehrung waehrung) throws GesperrtException;


}