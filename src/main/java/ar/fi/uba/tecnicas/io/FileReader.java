package ar.fi.uba.tecnicas.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import ar.fi.uba.tecnicas.exceptions.ReaderException;
import ar.fi.uba.tecnicas.jgobstones.Reader;

/**
 * FileReader
 * 
 * Responsabilidad: Implementacion de Reader que se encarga de leer un programa
 * de Gobstones desde un archivo y convertirlo en un String para el
 * GobstonesEvaluator
 *
 */

public class FileReader implements Reader {
	
	private String filePath;
	
	public FileReader(String filePath) {
		this.filePath=filePath;
	}
	
	public String read() throws ReaderException{
		String programRead="";
		try {
			programRead=new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			throw new ReaderException(e.getMessage());
		}
		
		return programRead;
	}

}
