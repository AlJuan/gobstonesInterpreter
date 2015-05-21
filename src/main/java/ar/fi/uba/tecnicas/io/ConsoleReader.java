package ar.fi.uba.tecnicas.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ar.fi.uba.tecnicas.exceptions.ReaderException;
import ar.fi.uba.tecnicas.jgobstones.Reader;

/**
 * ConsoleReader
 * 
 * Responsabilidad: Implementacion de Reader que se encarga de leer un programa
 * de Gobstones desde un archivo y convertirlo en un String para el
 * GobstonesEvaluator
 *
 */

public class ConsoleReader implements Reader {
	
	
	public String read() throws ReaderException{
		StringBuffer programRead=new StringBuffer();
		try {
			//programRead=new String(Files.readAllBytes(Paths.get(filePath)));
			
			BufferedReader bi=new BufferedReader(new InputStreamReader(System.in));
			String line;
			while((line=bi.readLine())!=null){
				programRead.append(line);
			}
			
		} catch (IOException e) {
			throw new ReaderException(e.getMessage());
		}
		
		return programRead.toString();
	}

}
