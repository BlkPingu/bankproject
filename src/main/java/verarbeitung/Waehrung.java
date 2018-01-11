package verarbeitung;

public class Waehrung{
	public static final Waehrung EUR = new Waehrung(1.0);
	public static final Waehrung BGN = new Waehrung(1.95583);
	public static final Waehrung KM = new Waehrung(1.95583);
	public static final Waehrung LTL = new Waehrung(3.4528);
	
	private final double wertEur;
	private Waehrung(double wertEur){
		this.wertEur = wertEur;
	}
	
	 /**
	  * @param betrag in euro
	  * @return betrag in angegebener W�hrung
	  */
	 public double umrechnen(double betrag){
		 return betrag * this.wertEur;
	 }
	 
}