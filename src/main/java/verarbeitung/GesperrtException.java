package verarbeitung;

/**
 * tritt bei einem schaedigenden Zugriff auf ein gesperrtes verarbeitung.Konto auf
 * @author Doro
 *
 */
@SuppressWarnings("serial")
public class GesperrtException extends Exception {
	
	/**
	 * Zugriff auf das verarbeitung.Konto mit der angegebenen Kontonummer
	 * @param kontonummer die Nummer des gesperrten Kontos, auf das zugegriffen wurde
	 */
	public GesperrtException(long kontonummer)
	{
		super("Zugriff auf gesperrtes verarbeitung.Konto mit Kontonummer " + kontonummer);
	}
	

}
