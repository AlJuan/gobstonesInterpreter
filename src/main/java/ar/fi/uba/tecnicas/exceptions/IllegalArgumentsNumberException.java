package ar.fi.uba.tecnicas.exceptions;

/**
 * 
 * IllegalArgumentsNumberException
 * 
 * Responsabilidad: Representa una excepcion que ocurre al pasarle a un Command
 * una cantidad de Argument equivocada
 * 
 *
 */

public class IllegalArgumentsNumberException extends RuntimeException {

	public IllegalArgumentsNumberException(Integer expected, Integer received) {
		super("Number of arguments expected: " + expected + ", received "
				+ received + " instead");
	}

}
