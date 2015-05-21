package ar.fi.uba.tecnicas.io;

import java.util.List;

import ar.fi.uba.tecnicas.jgobstones.ProgramPrinter;
import ar.fi.uba.tecnicas.model.Program;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.commands.Command;

public class FormatProgramPrinter implements ProgramPrinter {
	
	public void print(Program program){
		/*System.out.println("program {");
		CommandBlock commandBlock = program.getCommandBlock();
		List<Command> listOfCommands = commandBlock.getCommands();
		for (Command command : listOfCommands) {
			List<Argument> listOfArguments = command.getArguments();
			for (Argument argument : listOfArguments) {
				System.out.println("\t" + argument.getValue());
			}
		}
		System.out.println("}");*/
	}

}
