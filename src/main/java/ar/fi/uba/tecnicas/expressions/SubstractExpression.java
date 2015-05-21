package ar.fi.uba.tecnicas.expressions;

import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.GobstonesNumber;
import ar.fi.uba.tecnicas.model.OpposableValue;
import ar.fi.uba.tecnicas.model.Orientation;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * SubstractExpression
 * 
 * Responsabilidad: Representa una expresion de resta entre dos
 * valores numericos.
 * 
 * */
public class SubstractExpression extends TwoOperandExpression {

	public SubstractExpression() {
		super();
	}

	public SubstractExpression(Expression leftExpression, Expression rightExpression) {
		super(leftExpression, rightExpression);
	}

	@Override
	public Object evaluate(Board board, ProgramStack stack) {
		

		Object rightValue = this.getRightExpression().evaluate(board, stack);
		if (rightValue instanceof Function) {
			rightValue = ((Function) rightValue).execute(board, stack)
					.evaluate(board, stack);
		}

		if(this.getLeftExpression()!=null){
			Object leftValue = this.getLeftExpression().evaluate(board, stack);
			if (leftValue instanceof Function) {
				leftValue = ((Function) leftValue).execute(board, stack).evaluate(
						board, stack);
			}
			
			GobstonesNumber left = this.castExpressionResult(leftValue, GobstonesNumber.class);
			GobstonesNumber right = this.castExpressionResult(rightValue, GobstonesNumber.class);
			
			return new GobstonesNumber(left.getValue() - right.getValue());
		} else {
			if(rightValue instanceof Orientation){
				OpposableValue right = this.castExpressionResult(rightValue, OpposableValue.class);
				return right.getOpposite();
			} else {
				GobstonesNumber right = this.castExpressionResult(rightValue, GobstonesNumber.class);
				return new GobstonesNumber(- right.getValue());
			}
		}
		
		
	}

}