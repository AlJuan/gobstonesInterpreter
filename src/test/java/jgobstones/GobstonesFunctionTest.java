package jgobstones;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.functions.CanMoveFunction;
import ar.fi.uba.tecnicas.functions.ContainsFunction;
import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.functions.OppositeFunction;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.Color;
import ar.fi.uba.tecnicas.model.GobstonesBoolean;
import ar.fi.uba.tecnicas.model.GobstonesNumber;
import ar.fi.uba.tecnicas.model.Orientation;
import ar.fi.uba.tecnicas.model.ProgramStack;

public class GobstonesFunctionTest {

	private static final Integer TEST_COL_NUMBER = 8;
	private static final Integer TEST_ROW_NUMBER = 8;
	
	@Test
	public void OppositeTest() {
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression opposableValueExp = new ValueExpression(new GobstonesNumber(1.0));
		Function function = new OppositeFunction(opposableValueExp);
		ProgramStack programStack = new ProgramStack();
		Expression result = function.execute(board, programStack);
		assertEquals(result.evaluate(board,programStack), new GobstonesNumber(-1.0));
	}
	
	@Test
	public void CanMoveTest() {
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression valueExpression = new ValueExpression(Orientation.NORTH);
		Function function = new CanMoveFunction(valueExpression);
		ProgramStack programStack = new ProgramStack();
		Expression result = function.execute(board, programStack);
		assertEquals(result.evaluate(board, programStack), GobstonesBoolean.TRUE);
	}
	
	@Test
	public void ContainsFunctionTest() {
		Board board = new Board(TEST_COL_NUMBER , TEST_ROW_NUMBER);
		Expression valueExpression = new ValueExpression(Color.RED);
		Function function = new ContainsFunction(valueExpression);
		ProgramStack programStack = new ProgramStack();
		Expression result = function.execute(board, programStack);
		assertEquals(result.evaluate(board, programStack), GobstonesBoolean.FALSE);
	}
	
}
