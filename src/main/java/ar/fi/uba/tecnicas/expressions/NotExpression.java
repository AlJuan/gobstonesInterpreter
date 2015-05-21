package ar.fi.uba.tecnicas.expressions;

import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.GobstonesBoolean;
import ar.fi.uba.tecnicas.model.ProgramStack;

/**
 * NotExpression
 * 
 * Responsabilidad: Representa una expresion de negacion.
 * 
 * */
public class NotExpression extends OneOperandExpression {

	public NotExpression(Expression expression) {
		super(expression);
	}

	public NotExpression() {
		super();
	}

	@Override
	public Object evaluate(Board board, ProgramStack stack) {

		Object expValue = this.getExpression().evaluate(board, stack);
		if (expValue instanceof Function) {
			expValue = ((Function) expValue).execute(board, stack).evaluate(
					board, stack);
		}

		GobstonesBoolean exp = this.castExpressionResult(expValue,
				GobstonesBoolean.class);

		if (exp.getValue().equals(Boolean.TRUE)) {
			return GobstonesBoolean.FALSE;
		} else {
			return GobstonesBoolean.TRUE;
		}

	}

}
