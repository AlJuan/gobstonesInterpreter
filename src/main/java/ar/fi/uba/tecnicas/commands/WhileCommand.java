package ar.fi.uba.tecnicas.commands;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.CodeBlock;
import ar.fi.uba.tecnicas.model.GobstonesBoolean;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * WhileCommand
 * 
 * Responsabilidad: Representa al comando while de Gobstones. Ejecuta un
 * conjunto de comandos mientras la expresion sea verdadera.
 *
 */

public class WhileCommand extends Command {

	private final static Integer ARGUMENTS_NUMBER = 2;

	public WhileCommand() {
		super();
	}

	public WhileCommand(Expression expression, CodeBlock command) {
		getArguments().add(new Argument(expression));
		getArguments().add(new Argument(command));
	}

	@Override
	protected void executeCommand(Board board, ProgramStack programStack) {
		Expression expression = castArgument(this.getArguments().get(0),
				Expression.class);
		CodeBlock command = castArgument(this.getArguments().get(1),
				CodeBlock.class);
		executeCommandLogic(board, expression, command, programStack);
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

	private void executeCommandLogic(Board board, Expression expression,
			CodeBlock commandBlock, ProgramStack programStack) {
		while (resolveExpression(expression, board, programStack))
			commandBlock.execute(board, programStack);
	}

	private Boolean resolveExpression(Expression expression, Board board,
			ProgramStack programStack) {
		return Expression.castExpressionResult(
				expression.evaluate(board, programStack),
				GobstonesBoolean.class).getValue();
	}

}
