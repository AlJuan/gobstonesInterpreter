package ar.fi.uba.tecnicas.exceptions;

/**
 * 
 * ProgramTextEvaluationException
 * 
 * Responsabilidad: Representa una excepcion que ocurre cuando el
 * GobstonesEvaluator no puede parsear correctamente el texto del programa.
 *
 */

public class ProgramTextEvaluationException extends RuntimeException {

	public ProgramTextEvaluationException() {
		super("Error on parsing. Malformed program file.");
	}

}
