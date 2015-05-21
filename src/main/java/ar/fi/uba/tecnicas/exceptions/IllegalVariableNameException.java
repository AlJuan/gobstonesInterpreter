package ar.fi.uba.tecnicas.exceptions;

/**
 * 
 * IllegalVariableNameException
 * 
 * Responsabilidad: Exception al invocar una variable que nunca se asigno
 * 
 *
 */

public class IllegalVariableNameException extends RuntimeException {

	public IllegalVariableNameException(String variableName) {
		super(variableName + " does not exist");
	}

}
