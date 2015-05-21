package ar.fi.uba.tecnicas.commands;

import java.util.Iterator;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.expressions.VariableExpression;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.CodeBlock;
import ar.fi.uba.tecnicas.model.ProgramStack;
import ar.fi.uba.tecnicas.range.ValueExpressionRange;

/**
 * ForEachCommand
 * 
 * Responsabilidad: Representa al comando foreach de Gobstones. Ejecuta un
 * conjunto de comandos iterando una expresion.
 *
 */

public class ForEachCommand extends Command {

	private final static Integer ARGUMENTS_NUMBER = 3;

	public ForEachCommand() {
		super();
	}

	public ForEachCommand(Expression expression, ValueExpressionRange range,
			CodeBlock command) {
		getArguments().add(new Argument(expression));
		getArguments().add(new Argument(range));
		getArguments().add(new Argument(command));
	}

	@Override
	protected void executeCommand(Board board, ProgramStack programStack) {
		Expression expression = castArgument(this.getArguments().get(0),
				Expression.class);
		ValueExpressionRange range = castArgument(this.getArguments().get(1),
				ValueExpressionRange.class);
		CodeBlock command = castArgument(this.getArguments().get(2),
				CodeBlock.class);
		executeCommandLogic(board, expression, range, command, programStack);
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

	private void executeCommandLogic(Board board, Expression expression,
			ValueExpressionRange range, CodeBlock commandBlock,
			ProgramStack programStack) {

		VariableExpression variable = (VariableExpression) expression;
		ValueExpressionRange valueRange = (ValueExpressionRange) range;

		for (Iterator<ValueExpression> iterator = valueRange.iterator(board,
				programStack); iterator.hasNext();) {
			ValueExpression value = (ValueExpression) iterator.next();

			programStack.addVariable(variable.getVariableName(), value);

			commandBlock.execute(board, programStack);
		}


	}

}
