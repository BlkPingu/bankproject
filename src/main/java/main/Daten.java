package main;

import java.io.Serializable;

/**
 * main.Daten mit einer ID
 * @author Doro
 *
 * @param <IDTyp> Datentyp der ID
 */
public interface Daten<IDTyp> extends Serializable
{
	/**
	 * liefert die ID des Datenpaketes
	 * @return
	 */
	public IDTyp getId();
}