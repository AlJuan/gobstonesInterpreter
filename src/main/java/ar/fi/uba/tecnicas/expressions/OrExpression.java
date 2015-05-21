package ar.fi.uba.tecnicas.expressions;

import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.GobstonesBoolean;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * OrExpression
 * 
 * Responsabilidad: Expresion de or entre dos objetos.
 * 
 * */
public class OrExpression extends TwoOperandExpression{

	public OrExpression() {
		super();
	}
	
	public OrExpression(Expression leftExpression, Expression rightExpression) {
		super(leftExpression, rightExpression);
	}
	
	@Override
	public Object evaluate(Board board,ProgramStack stack) {
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

		GobstonesBoolean left = this.castExpressionResult(leftValue,
				GobstonesBoolean.class);
		GobstonesBoolean right = this.castExpressionResult(rightValue,
				GobstonesBoolean.class);
		if(left.getValue() || right.getValue()){
			return GobstonesBoolean.TRUE;
		} else {
			return GobstonesBoolean.FALSE;
		}
		
	}


}
