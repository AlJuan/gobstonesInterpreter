package ar.fi.uba.tecnicas.expressions;

/**
 * TwoOperandExpression
 * 
 * Responsabilidad: Abstraccion de expresion de dos operandos.
 * 
 * */
public abstract class TwoOperandExpression extends Expression {
	private Expression leftExpression;
	private Expression rightExpression;
	
	public TwoOperandExpression(Expression leftExpression, Expression rightExpression) {
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
	}

	public TwoOperandExpression() {
		super();
	}

	public Expression getLeftExpression() {
		return leftExpression;
	}

	public void setLeftExpression(Expression leftExpression) {
		this.leftExpression = leftExpression;
	}

	public Expression getRightExpression() {
		return rightExpression;
	}

	public void setRightExpression(Expression rightExpression) {
		this.rightExpression = rightExpression;
	}
}
