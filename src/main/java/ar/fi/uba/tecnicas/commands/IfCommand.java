package ar.fi.uba.tecnicas.commands;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.VoidExpression;
import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.CodeBlock;
import ar.fi.uba.tecnicas.model.GobstonesBoolean;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * IfCommand
 * 
 * Responsabilidad: Representa al comando if de Gobstones. Ejecuta un conjunto
 * de comandos si la expresion es verdadera, sino ejecuta otro
 *
 */

public class IfCommand extends Command {

	private final static Integer ARGUMENTS_NUMBER = 3;

	public IfCommand() {
		super();
	}

	public IfCommand(Expression expression, CodeBlock commandBlockThen,
			CodeBlock commandBlockElse) {
		getArguments().add(new Argument(expression));
		getArguments().add(new Argument(commandBlockThen));
		getArguments().add(new Argument(commandBlockElse));
	}

	@Override
	protected void executeCommand(Board board, ProgramStack programStack) {
		Expression expression = castArgument(this.getArguments().get(0),
				Expression.class);
		CodeBlock commandBlockThen = castArgument(
				this.getArguments().get(1), CodeBlock.class);
		CodeBlock commandBlockElse = castArgument(
				this.getArguments().get(2), CodeBlock.class);
		executeCommandLogic(board, expression, commandBlockThen,
				commandBlockElse, programStack);
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

	private void executeCommandLogic(Board board, Expression expression,
			CodeBlock commandBlockThen, CodeBlock commandBlockElse,
			ProgramStack programStack) {
		if (resolveExpression(expression, board, programStack))
			commandBlockThen.execute(board, programStack);
		else
			commandBlockElse.execute(board, programStack);
	}

	private Boolean resolveExpression(Expression expression, Board board,
			ProgramStack programStack) {
		return Expression.castExpressionResult(
				expression.evaluate(board,programStack), GobstonesBoolean.class)
				.getValue();
	}

}
