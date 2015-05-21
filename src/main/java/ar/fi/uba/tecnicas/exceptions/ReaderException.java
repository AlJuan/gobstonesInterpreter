package ar.fi.uba.tecnicas.exceptions;

/**
 * ReaderException
 * 
 * Responsabilidad: Exception que ocurre al leer los datos de entrada.
 * 
 * */
public class ReaderException extends Exception {

	public ReaderException(String errorMessage) {
		super("Error while reading program text file:" + errorMessage);
	}

}
