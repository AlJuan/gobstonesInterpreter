package ar.fi.uba.tecnicas.jgobstones;

import java.io.IOException;

import ar.fi.uba.tecnicas.exceptions.NoSuchColorException;
import ar.fi.uba.tecnicas.exceptions.OutOfBoundsException;
import ar.fi.uba.tecnicas.exceptions.ProgramTextEvaluationException;
import ar.fi.uba.tecnicas.exceptions.ReaderException;
import ar.fi.uba.tecnicas.io.ConsolePrinter;
import ar.fi.uba.tecnicas.io.ConsoleReader;
import ar.fi.uba.tecnicas.io.FileReader;
import ar.fi.uba.tecnicas.io.GobstonesFormatPrinter;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.Program;
import ar.fi.uba.tecnicas.parser.GobstonesEvaluator;

/**
 * Gobstones
 * 
 * Responsabilidad: Invocar al Reader para obtener el texto que utilizara el
 * GobstonesEvaluator para parsear el programa y una vez parseado invocar al
 * Printer para imprimir el tablero.
 *
 */

public class Gobstones {
	
	public static final Integer MAX_COL_NUMBER = 8;
	public static final Integer MAX_ROW_NUMBER = 8;

	public static void main(String[] args) throws IOException {
		
		int maxRows = getMaxRows(args);
		int maxCols = getMaxCols(args);
		
		GobstonesEvaluator gobstones = new GobstonesEvaluator();

		Printer printer = null;
		if(useGobstonesFormat(args)){
			printer = new GobstonesFormatPrinter();
		} else {
			printer = new ConsolePrinter();
		}
		
		try {
			String programText=getPParameter(args);
			if(programText.isEmpty()){
				
				String filePath=getFilePath(args);
				Reader reader=null;
				if(filePath.isEmpty()){
					reader = new ConsoleReader();
				} else {
					reader = new FileReader(getFilePath(args));
				}
				
				programText = reader.read();
			} 
			Program program = gobstones.evaluate(programText);
			setProgramBoard(maxRows, maxCols, program);
			program.execute();
			printer.print(program.getBoard());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (NoSuchColorException e) {
			System.out.println(e.getMessage());
		} catch (OutOfBoundsException e) {
			System.out.println(e.getMessage());
		} catch (ProgramTextEvaluationException e) {
			System.out.println(e.getMessage());
		} catch (ReaderException e){
			System.out.println(e.getMessage());
		}
	}

	private static void setProgramBoard(int maxRows, int maxCols,
			Program program) {
		Board board = new Board(MAX_COL_NUMBER, MAX_ROW_NUMBER);
		if(maxCols != 0 && maxRows != 0){
			board = new Board(maxCols, maxRows);
		} 
		program.setBoard(board);
	}

	private static String getFilePath(String[] args) {
		int i = 0;
		String arg;
		String inputFile = "";

		if(args.length==0){
			return inputFile;
		}
		
		while (args != null && args.length > i && args[i].startsWith("-")) {
			arg = args[i++];
			// use this type of check for arguments that require arguments
			if (arg.equals("-f")) {
				if (i < args.length) {
					inputFile = args[i++];
				} else {
					System.err.println("-f requires a filename");
				}
			}
		}
		return inputFile;
	}
	

	private static String getPParameter(String[] args) {
		int i = 0;
		String arg;
		String programText = "";

		while (args != null && args.length > i && args[i].startsWith("-")) {
			arg = args[i++];
			// use this type of check for arguments that require arguments
			if (arg.equals("-p")) {
				if (i < args.length) {
					programText = args[i++];
				} else {
					System.err.println("-p requires a programtext");
				}
			} 
		}
		

		return programText;
	}
	
	private static Integer getMaxRows(String[] args) {
		int i = 0;
		String arg;
		int maxRows = 0;

		while (args != null && args.length > i && args[i].startsWith("-")) {
			arg = args[i++];
			// use this type of check for arguments that require arguments
			if (arg.equals("-r")) {
				if (i < args.length) {
					maxRows = Integer.parseInt(args[i++]);
				} else {
					System.err.println("-r requires a programtext");
				}
			} 
		}
		Integer maxRowsI = new Integer(maxRows);
		return maxRowsI;
	}
	
	private static Integer getMaxCols(String[] args) {
		int i = 0;
		String arg;
		int maxCols = 0;

		while (args != null && args.length > i && args[i].startsWith("-")) {
			arg = args[i++];
			// use this type of check for arguments that require arguments
			if (arg.equals("-c")) {
				if (i < args.length) {
					maxCols = Integer.parseInt(args[i++]);
				} else {
					System.err.println("-c requires a programtext");
				}
			} 
		}
		Integer maxColsI = new Integer(maxCols);
		return maxColsI;
	}
	
	private static boolean useGobstonesFormat(String[] args) {
		int i = 0;
		String arg;
		boolean useGobstonesFormat = false;

		while (args != null && args.length > i && args[i].startsWith("-")) {
			arg = args[i++];
			if(arg.equals("-gout")){
				useGobstonesFormat = true;
			}
		}
		return useGobstonesFormat;
	}

}