package ar.fi.uba.tecnicas.exceptions;

/**
 * OutOfBoundsException
 * 
 * Responsabilidad: Representa a una excepcion que ocurre cuando se intenta
 * mover el cabezal a una posicion del tablero inexistente.
 *
 */

public class OutOfBoundsException extends RuntimeException {

	public OutOfBoundsException(Integer posX, Integer posY) {
		super("Invalid position (" + posX + "," + posY + ")");
	}
}
