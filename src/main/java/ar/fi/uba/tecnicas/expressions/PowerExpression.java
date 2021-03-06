package ar.fi.uba.tecnicas.expressions;

import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.GobstonesNumber;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * PowerExpression
 * 
 * Responsabilidad: Representa una expresion de potencia entre dos objetos.
 * 
 * */
public class PowerExpression extends TwoOperandExpression {

	public PowerExpression() {
		super();
	}

	public PowerExpression(Expression leftExpression, Expression rightExpression) {
		super(leftExpression, rightExpression);
	}

	@Override
	public Object evaluate(Board board, ProgramStack stack) {
		Object leftValue = this.getLeftExpression().evaluate(board, stack);
		if (leftValue instanceof Function) {
			leftValue = ((Function) leftValue).execute(board, stack).evaluate(
					board, stack);
		}

		Object rightValue = this.getRightExpression().evaluate(board, stack);
		if (rightValue instanceof Function) {
			rightValue = ((Function) rightValue).execute(board, stack)
					.evaluate(board, stack);
		}

		GobstonesNumber left = this.castExpressionResult(leftValue,
				GobstonesNumber.class);
		GobstonesNumber right = this.castExpressionResult(rightValue,
				GobstonesNumber.class);

		Double result = Math.pow(left.getValue(), right.getValue());
		return new GobstonesNumber(result);
	}
}