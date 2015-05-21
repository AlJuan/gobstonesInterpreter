package ar.fi.uba.tecnicas.jgobstones;

import ar.fi.uba.tecnicas.exceptions.ReaderException;

/**
 * Reader
 * 
 * Responsabilidad: Interfaz que permite leer un programa de Gobstones.
 *
 */

public interface Reader {

	public String read() throws ReaderException;
}
