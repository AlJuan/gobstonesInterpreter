package ar.fi.uba.tecnicas.functions;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.OpposableValue;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * OppositeFunction
 * 
 * Responsabilidad: Representa a la funcion opuesto de Gobstones. Devuelve el
 * elemento opuesto, si es una direccion o un numero.
 * 
 */

public class OppositeFunction extends Function {

	private final static Integer ARGUMENTS_NUMBER = 1;

	public OppositeFunction() {
		super();
	}

	public OppositeFunction(Expression expression) {
		this.arguments.add(new Argument(expression));
	}

	private Expression executeFunctionLogic(Board board,
			OpposableValue opposableConstant) {
		return new ValueExpression(opposableConstant.getOpposite());
	}

	@Override
	protected Expression executeFunction(Board board, ProgramStack programStack) {
		Expression expression = castArgument(this.getArguments().get(0),
				Expression.class);
		OpposableValue opposableConstant = Expression.castExpressionResult(
				expression.evaluate(board, programStack), OpposableValue.class);
		return executeFunctionLogic(board, opposableConstant);
	}

	@Override
	protected Integer getArgumentsNumber() {
		return ARGUMENTS_NUMBER;
	}

}
