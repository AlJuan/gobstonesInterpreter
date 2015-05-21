package ar.fi.uba.tecnicas.functions;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.OrderedValue;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * PreviousFunction
 * 
 * Responsabilidad: Representa a la funcion previo de Gobstones. Devuelve el
 * elemento anterior en el orden de los elementos del tipo del valor pasado como
 * argumento
 * 
 */

public class PreviousFunction extends Function {

	private final static Integer ARGUMENTS_NUMBER = 1;

	public PreviousFunction() {
		super();
	}

	public PreviousFunction(Expression expression) {
		this.arguments.add(new Argument(expression));
	}

	private Expression executeFunctionLogic(Board board,
			OrderedValue orderedConstant) {
		return new ValueExpression(orderedConstant.getPrevious());
	}

	@Override
	protected Expression executeFunction(Board board, ProgramStack programStack) {
		Expression expression = castArgument(this.getArguments().get(0),
				Expression.class);
		OrderedValue orderedConstant = Expression.castExpressionResult(
				expression.evaluate(board, programStack), OrderedValue.class);
		return executeFunctionLogic(board, orderedConstant);
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

}
