package verarbeitung;

public enum Waehrung
{
	/**
	 *Konstanten der Klasse verarbeitung.Waehrung
	 */
	EUR (1),
	BGN (1.95583),
	LTL (2.5),
	KM  (3.4528);

	/**
	 *Wert der verarbeitung.Waehrung in Euro
	 */
	private final double value;

	/**
	 *Setzt Waehrungkurs
	 * @Param value kurs der verarbeitung.Waehrung
	 */
	private Waehrung(double value)
	{
		this.value = value;
	}

	/**
	 *gibt den Kurs der verarbeitung.Waehrung zurueck
	 */
	public double getValue()
	{
		return this.value;
	}
	
	public double umrechnen(double betrag)
	{	
		return betrag / this.value;
	}
}