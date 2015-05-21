package ar.fi.uba.tecnicas.commands;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.CodeBlock;
import ar.fi.uba.tecnicas.model.GobstonesNumber;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * RepeatCommand
 * 
 * Responsabilidad: Representa al comando repeat de Gobstones. Ejecuta un
 * conjunto de comandos la cantidad de veces que indica el argumento
 *
 */

public class RepeatCommand extends Command {

	private final static Integer ARGUMENTS_NUMBER = 2;

	public RepeatCommand() {
		super();
	}

	public RepeatCommand(Expression repetitions, CodeBlock command) {
		getArguments().add(new Argument(repetitions));
		getArguments().add(new Argument(command));
	}

	@Override
	protected void executeCommand(Board board, ProgramStack programStack) {
		Expression repetitionsExpression = castArgument(this.getArguments()
				.get(0), Expression.class);
		GobstonesNumber repetitions = castObject(
				repetitionsExpression.evaluate(board, programStack),
				GobstonesNumber.class);
		CodeBlock command = castArgument(this.getArguments().get(1),
				CodeBlock.class);
		executeCommandLogic(board, repetitions, command, programStack);
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

	private void executeCommandLogic(Board board, GobstonesNumber repetitions,
			CodeBlock commandBlock, ProgramStack programStack) {
		for (Integer i = 0; i < repetitions.getValue(); i++) {
			commandBlock.execute(board, programStack);
		}
	}

}
