package ar.fi.uba.tecnicas.expressions;

import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * GreaterOrEqualsThanExpression
 * 
 * Responsabilidad: Expresion mayor o igual entre dos objetos comparables.
 * 
 * */
public class GreaterOrEqualsThanExpression extends TwoOperandExpression {

	public GreaterOrEqualsThanExpression(Expression leftExpression,
			Expression rightExpression) {
		super(leftExpression, rightExpression);
	}

	public GreaterOrEqualsThanExpression() {
		super();
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

		Comparable left = this
				.castExpressionResult(leftValue, Comparable.class);
		Comparable right = this.castExpressionResult(rightValue,
				Comparable.class);

		return (left.compareTo(right) > 0 || left.compareTo(right) == 0);
	}

}
