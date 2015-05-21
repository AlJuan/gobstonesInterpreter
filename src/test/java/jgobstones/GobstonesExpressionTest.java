package jgobstones;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ar.fi.uba.tecnicas.expressions.AddExpression;
import ar.fi.uba.tecnicas.expressions.AndExpression;
import ar.fi.uba.tecnicas.expressions.DivExpression;
import ar.fi.uba.tecnicas.expressions.EqualsThanExpression;
import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.GreaterOrEqualsThanExpression;
import ar.fi.uba.tecnicas.expressions.GreaterThanExpression;
import ar.fi.uba.tecnicas.expressions.LesserOrEqualsThanExpression;
import ar.fi.uba.tecnicas.expressions.LesserThanExpression;
import ar.fi.uba.tecnicas.expressions.ModExpression;
import ar.fi.uba.tecnicas.expressions.NotEqualsThanExpression;
import ar.fi.uba.tecnicas.expressions.NotExpression;
import ar.fi.uba.tecnicas.expressions.OrExpression;
import ar.fi.uba.tecnicas.expressions.PowerExpression;
import ar.fi.uba.tecnicas.expressions.ProductExpression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.expressions.VariableExpression;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.GobstonesBoolean;
import ar.fi.uba.tecnicas.model.GobstonesNumber;
import ar.fi.uba.tecnicas.model.ProgramStack;

public class GobstonesExpressionTest {
	
	private static final Integer TEST_COL_NUMBER = 8;
	private static final Integer TEST_ROW_NUMBER = 8;


	@Test
	public void NumberVaribableAssignment() {
		ProgramStack programStack = new ProgramStack();
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		programStack.addVariable("numberEight", new ValueExpression(8)); // numberEight
																			// =
																			// 8
		Expression variableExpression = new VariableExpression("numberEight");
		assertEquals(variableExpression.evaluate(board, programStack), new Integer(8));
	}

	@Test
	public void BooleanVaribableAssignment() {
		ProgramStack programStack = new ProgramStack();
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		programStack.addVariable("itsTrue", new ValueExpression(Boolean.TRUE)); // itsTrue
																				// =
																				// True
		Expression variableExpression = new VariableExpression("itsTrue");
		assertEquals(variableExpression.evaluate(board, programStack), Boolean.TRUE);
	}

	@Test
	public void AddNumbersTest() {
		ProgramStack programStack = new ProgramStack();
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression addExpression = new AddExpression(new ValueExpression(
				new GobstonesNumber(2.0)), new ValueExpression(
				new GobstonesNumber(2.0)));// 2 + 2
		assertEquals(addExpression.evaluate(board, programStack),
				new GobstonesNumber(4.0));
	}

	@Test
	public void DivideNumbersTest() {
		ProgramStack programStack = new ProgramStack();
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression divExpression = new DivExpression(new ValueExpression(
				new GobstonesNumber(4.0)), new ValueExpression(
				new GobstonesNumber(2.0)));// 4 / 2
		assertEquals(divExpression.evaluate(board, programStack),
				new GobstonesNumber(2.0));
	}
	
	@Test
	public void ModNumbersTest() {
		ProgramStack programStack = new ProgramStack();
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression modExpression = new ModExpression(new ValueExpression(
				new GobstonesNumber(4.0)), new ValueExpression(
				new GobstonesNumber(3.0)));// 4 % 3
		assertEquals(modExpression.evaluate(board, programStack),
				new GobstonesNumber(1.0));
	}

	@Test
	public void MultiplyNumbersTest() {
		ProgramStack programStack = new ProgramStack();
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression addExpression = new ProductExpression(new ValueExpression(
				new GobstonesNumber(4.0)), new ValueExpression(
				new GobstonesNumber(3.0)));// 4 * 3
		assertEquals(addExpression.evaluate(board, programStack),
				new GobstonesNumber(12.0));
	}

	@Test
	public void PowerNumbersTest() {
		ProgramStack programStack = new ProgramStack();
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression addExpression = new PowerExpression(new ValueExpression(
				new GobstonesNumber(2.0)), new ValueExpression(
				new GobstonesNumber(3.0)));// 2 ^ 3
		assertEquals(addExpression.evaluate(board, programStack),
				new GobstonesNumber(8.0));
	}

	@Test
	public void BooleanOrTest() {
		ProgramStack programStack = new ProgramStack();
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression orExpression = new OrExpression(new ValueExpression(
				GobstonesBoolean.TRUE), new ValueExpression(
				GobstonesBoolean.FALSE)); // true || false
		assertEquals(orExpression.evaluate(board, programStack), GobstonesBoolean.TRUE);
	}

	@Test
	public void BooleanAndTest() {
		ProgramStack programStack = new ProgramStack();
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression orExpression = new AndExpression(new ValueExpression(
				GobstonesBoolean.TRUE), new ValueExpression(
				GobstonesBoolean.FALSE)); // true && false
		assertEquals(orExpression.evaluate(board, programStack),
				GobstonesBoolean.FALSE);
	}

	@Test
	public void BooleanLessTest() {
		ProgramStack programStack = new ProgramStack();
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression lessExpression = new LesserThanExpression(
				new ValueExpression(1), new ValueExpression(2)); // 1 < 2
		assertEquals(lessExpression.evaluate(board, programStack), Boolean.TRUE);
	}

	@Test
	public void BooleanLessOrEqualsTest() {
		ProgramStack programStack = new ProgramStack();
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression lessExpression = new LesserOrEqualsThanExpression(
				new ValueExpression(2), new ValueExpression(2)); // 2 <= 2
		assertEquals(lessExpression.evaluate(board, programStack), Boolean.TRUE);
	}

	@Test
	public void BooleanGreaterTest() {
		ProgramStack programStack = new ProgramStack();
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression lessExpression = new GreaterThanExpression(
				new ValueExpression(1), new ValueExpression(2)); // 1 > 2
		assertEquals(lessExpression.evaluate(board, programStack), Boolean.FALSE);
	}

	@Test
	public void BooleanGreaterOrEqualsTest() {
		ProgramStack programStack = new ProgramStack();
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression lessExpression = new GreaterOrEqualsThanExpression(
				new ValueExpression(2), new ValueExpression(2)); // 2 >= 2
		assertEquals(lessExpression.evaluate(board, programStack), Boolean.TRUE);
	}

	@Test
	public void BooleanEqualsTest() {
		ProgramStack programStack = new ProgramStack();
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression lessExpression = new EqualsThanExpression(
				new ValueExpression(2), new ValueExpression(2)); // 2 == 2
		assertEquals(lessExpression.evaluate(board, programStack), Boolean.TRUE);
	}

	@Test
	public void BooleanNotEqualsTest() {
		ProgramStack programStack = new ProgramStack();
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression lessExpression = new NotEqualsThanExpression(
				new ValueExpression(3), new ValueExpression(2)); // 2 == 2
		assertEquals(lessExpression.evaluate(board, programStack), Boolean.TRUE);
	}

	@Test
	public void NotTest() {
		ProgramStack programStack = new ProgramStack();
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression booleanExpression = new ValueExpression(
				GobstonesBoolean.TRUE);
		Expression notExpression = new NotExpression(booleanExpression);
		assertEquals(notExpression.evaluate(board, programStack),
				GobstonesBoolean.FALSE);
	}
	
	@Test
	public void CombinedExpressionTest() {
		ProgramStack programStack = new ProgramStack();
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression booleanExpression = new ValueExpression(
				GobstonesBoolean.TRUE);
		Expression notExpression = new NotExpression(booleanExpression);
		assertEquals(notExpression.evaluate(board, programStack),
				GobstonesBoolean.FALSE);
	}
	
	
	

}
