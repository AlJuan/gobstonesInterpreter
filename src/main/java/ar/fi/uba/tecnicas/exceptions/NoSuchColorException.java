package ar.fi.uba.tecnicas.exceptions;

/**
 * 
 * NoSuchColorException
 * 
 * Responsabilidad: Representa una excepcion que ocurre cuando se intenta sacar
 * una bolita de un color que no existe en esa posicion del tablero.
 *
 */

public class NoSuchColorException extends RuntimeException {

	public NoSuchColorException(String colorName) {
		super("There is no " + colorName + " ball on the current cell.");
	}
}
