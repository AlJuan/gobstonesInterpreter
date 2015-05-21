package ar.fi.uba.tecnicas.exceptions;

/**
 * 
 * IllegalVariableNameException
 * 
 * Responsabilidad: Exception al invocar una variable que nunca se asigno
 * 
 *
 */

public class IllegalExpressionResultException extends RuntimeException {

	public IllegalExpressionResultException(String expected, String received) {
		super("Expected type: " + expected + " received: " + received);
	}

}
