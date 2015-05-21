package ar.fi.uba.tecnicas.functions;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.OrderedValue;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * NextFunction
 * 
 * Responsabilidad: Representa a la funcion siguente de Gobstones. Devuelve el
 * elemento siguiente en el orden de los elementos del tipo del valor pasado
 * como argumento
 * 
 */

public class NextFunction extends Function {

	private final static Integer ARGUMENTS_NUMBER = 1;

	public NextFunction() {
		super();
	}

	public NextFunction(Expression expression) {
		this.arguments.add(new Argument(expression));
	}

	private Expression executeFunctionLogic(OrderedValue orderedConstant) {
		return new ValueExpression(orderedConstant.getNext());
	}

	@Override
	protected Expression executeFunction(Board board, ProgramStack programStack) {
		Expression expression = castArgument(this.getArguments().get(0),
				Expression.class);
		OrderedValue orderedConstant = Expression.castExpressionResult(
				expression.evaluate(board, programStack), OrderedValue.class);
		return executeFunctionLogic(orderedConstant);
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

}
