package jgobstones;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ar.fi.uba.tecnicas.commands.ForEachCommand;
import ar.fi.uba.tecnicas.commands.MoveCommand;
import ar.fi.uba.tecnicas.commands.PlaceCommand;
import ar.fi.uba.tecnicas.commands.RemoveCommand;
import ar.fi.uba.tecnicas.commands.RepeatCommand;
import ar.fi.uba.tecnicas.exceptions.NoSuchColorException;
import ar.fi.uba.tecnicas.exceptions.OutOfBoundsException;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.expressions.VariableExpression;
import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.io.ConsolePrinter;
import ar.fi.uba.tecnicas.io.GobstonesFormatPrinter;
import ar.fi.uba.tecnicas.jgobstones.Printer;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.CodeBlock;
import ar.fi.uba.tecnicas.model.Color;
import ar.fi.uba.tecnicas.model.GobstonesNumber;
import ar.fi.uba.tecnicas.model.Orientation;
import ar.fi.uba.tecnicas.model.Position;
import ar.fi.uba.tecnicas.model.ProgramStack;
import ar.fi.uba.tecnicas.range.ValueExpressionRange;

public class GobstonesModelTest {

	private static final Integer TEST_COL_NUMBER = 8;
	private static final Integer TEST_ROW_NUMBER = 8;

	@Test
	public void northMovement() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		board.move(Orientation.NORTH);
		assertEquals(board.getActualPosition(), new Position(0, 1));
	}

	@Test
	public void northMovementCommand() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		Function moveCommand = new MoveCommand(new ValueExpression(
				Orientation.NORTH));
		moveCommand.execute(board, new ProgramStack());
		assertEquals(board.getActualPosition(), new Position(0, 1));
	}

	@Test
	public void eastMovement() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		board.move(Orientation.EAST);
		assertEquals(board.getActualPosition(), new Position(1, 0));
	}

	@Test
	public void eastMovementCommand() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		Function moveCommand = new MoveCommand(new ValueExpression(
				Orientation.EAST));
		moveCommand.execute(board, new ProgramStack());
		assertEquals(board.getActualPosition(), new Position(1, 0));
	}

	@Test(expected = OutOfBoundsException.class)
	public void southMovement() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		board.move(Orientation.SOUTH);
	}

	@Test(expected = OutOfBoundsException.class)
	public void southMovementCommand() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		Function moveCommand = new MoveCommand(new ValueExpression(
				Orientation.SOUTH));
		moveCommand.execute(board, new ProgramStack());
	}

	@Test(expected = OutOfBoundsException.class)
	public void westMovement() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		board.move(Orientation.WEST);
	}

	@Test(expected = OutOfBoundsException.class)
	public void westMovementCommand() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		Function moveCommand = new MoveCommand(new ValueExpression(
				Orientation.WEST));
		moveCommand.execute(board, new ProgramStack());
	}

	@Test
	public void squareMovement() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		board.move(Orientation.EAST);
		board.move(Orientation.NORTH);
		board.move(Orientation.WEST);
		board.move(Orientation.SOUTH);
		assertEquals(board.getActualPosition(), new Position(0, 0));
	}

	@Test
	public void goToEdgeMovement() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		board.goToEdge(Orientation.EAST);
		assertEquals(board.getActualPosition(), new Position(
				TEST_COL_NUMBER - 1, 0));
	}

	@Test
	public void placeBallQuantity() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		board.place(Color.BLACK);
		assertEquals(board.getQuantity(), new Integer(1)); // new Integer solves
															// ambiguity problem
															// on
															// assertEquals @
															// compile time
	}

	@Test
	public void placeBallQuantityCommand() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		Function placeCommand = new PlaceCommand(new ValueExpression(
				Color.BLACK));
		placeCommand.execute(board, new ProgramStack());
		assertEquals(board.getQuantity(), new Integer(1)); // new Integer solves
															// ambiguity problem
															// on
															// assertEquals @
															// compile time
	}

	@Test
	public void removeBall() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		board.place(Color.BLACK);
		board.remove(Color.BLACK);
		assertEquals(board.getQuantity(), new Integer(0)); // new Integer solves
															// ambiguity problem
															// on
															// assertEquals @
															// compile time
	}

	@Test
	public void removeBallCommand() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		board.place(Color.BLACK);
		Function removeCommand = new RemoveCommand(new ValueExpression(
				Color.BLACK));
		removeCommand.execute(board, new ProgramStack());
		assertEquals(board.getQuantity(), new Integer(0)); // new Integer solves
															// ambiguity problem
															// on
															// assertEquals @
															// compile time
	}

	@Test(expected = NoSuchColorException.class)
	public void removeIncorrectColorBall() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		board.place(Color.BLACK);
		board.remove(Color.RED);
	}

	@Test
	public void printBoardWithBalls() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		board.place(Color.BLACK);
		board.place(Color.BLACK);
		board.place(Color.BLACK);
		board.place(Color.RED);
		board.place(Color.BLACK);
		board.move(Orientation.NORTH);
		board.move(Orientation.NORTH);
		board.place(Color.BLUE);
		board.place(Color.BLUE);
		board.move(Orientation.EAST);
		board.move(Orientation.NORTH);
		board.place(Color.GREEN);
		board.place(Color.GREEN);
		board.place(Color.RED);
		board.place(Color.RED);
		board.place(Color.RED);
		board.place(Color.BLUE);
		ConsolePrinter printer = new ConsolePrinter();
		printer.print(board);
	}

	@Test
	public void printBoardWithBallsInGobstonesFormat() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		board.place(Color.BLACK);
		board.place(Color.BLACK);
		board.place(Color.BLACK);
		board.place(Color.RED);
		board.place(Color.BLACK);
		board.move(Orientation.NORTH);
		board.move(Orientation.NORTH);
		board.place(Color.BLUE);
		board.place(Color.BLUE);
		board.move(Orientation.EAST);
		board.move(Orientation.NORTH);
		board.place(Color.GREEN);
		board.place(Color.GREEN);
		board.place(Color.RED);
		board.place(Color.RED);
		board.place(Color.RED);
		board.place(Color.BLUE);
		Printer printer = new GobstonesFormatPrinter();
		printer.print(board);
	}

	@Test(expected = NoSuchColorException.class)
	public void removeIncorrectColorBallCommand() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		board.place(Color.BLACK);
		Function removeCommand = new RemoveCommand(new ValueExpression(
				Color.RED));
		removeCommand.execute(board, new ProgramStack());
	}

	@Test
	public void moveCommandBlockCommand() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		List<Function> commands = new ArrayList<Function>();
		Function moveCommandNorth = new MoveCommand(new ValueExpression(
				Orientation.NORTH));
		Function moveCommandEast = new MoveCommand(new ValueExpression(
				Orientation.EAST));
		commands.add(moveCommandNorth);
		commands.add(moveCommandEast);
		CodeBlock commandBlock = new CodeBlock(commands);
		commandBlock.execute(board, new ProgramStack());
		assertEquals(board.getActualPosition(), new Position(1, 1));
	}

	@Test
	public void repeatMoveCommand() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		List<Function> commands = new ArrayList<Function>();
		Function moveCommandNorth = new MoveCommand(new ValueExpression(
				Orientation.NORTH));
		Function moveCommandEast = new MoveCommand(new ValueExpression(
				Orientation.EAST));
		commands.add(moveCommandNorth);
		commands.add(moveCommandEast);
		Function repeatCommand = new RepeatCommand(new ValueExpression(new GobstonesNumber(2)),
				new CodeBlock(commands));
		repeatCommand.execute(board, new ProgramStack());
		assertEquals(board.getActualPosition(), new Position(2, 2));
	}

	@Test
	public void forEachCommandWithVariables() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		List<Function> commands = new ArrayList<Function>();

		PlaceCommand placeCommand = new PlaceCommand(new VariableExpression(
				"var"));

		commands.add(placeCommand);

		Function foreachCommand = new ForEachCommand(new VariableExpression(
				"var"), new ValueExpressionRange(1, new ValueExpression(
				Color.BLUE), new ValueExpression(Color.GREEN)), new CodeBlock(
				commands));

		foreachCommand.execute(board, new ProgramStack());
		Boolean isThereOneBlueBall = board.getQuantityByColor(Color.BLUE) == 1 ? true
				: false;
		Boolean isThereOneBlackBall = board.getQuantityByColor(Color.BLACK) == 1 ? true
				: false;
		Boolean isThereOneRedBall = board.getQuantityByColor(Color.RED) == 1 ? true
				: false;
		Boolean isThereOneGreenBall = board.getQuantityByColor(Color.GREEN) == 1 ? true
				: false;

		assertTrue(isThereOneBlueBall && isThereOneBlackBall
				&& isThereOneRedBall && isThereOneGreenBall);
	}

	@Test
	public void forEachCommandWithFunction() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		List<Function> commands = new ArrayList<Function>();

		PlaceCommand placeCommand = new PlaceCommand(new VariableExpression(
				"var"));

		commands.add(placeCommand);

		Function foreachCommand = new ForEachCommand(new VariableExpression(
				"var"), new ValueExpressionRange(1, new ValueExpression(
				Color.BLUE), new ValueExpression(Color.GREEN)), new CodeBlock(
				commands));

		foreachCommand.execute(board, new ProgramStack());
		Boolean isThereOneBlueBall = board.getQuantityByColor(Color.BLUE) == 1 ? true
				: false;
		Boolean isThereOneBlackBall = board.getQuantityByColor(Color.BLACK) == 1 ? true
				: false;
		Boolean isThereOneRedBall = board.getQuantityByColor(Color.RED) == 1 ? true
				: false;
		Boolean isThereOneGreenBall = board.getQuantityByColor(Color.GREEN) == 1 ? true
				: false;

		assertTrue(isThereOneBlueBall && isThereOneBlackBall
				&& isThereOneRedBall && isThereOneGreenBall);
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalCastArgumentOnCommand() {
		Function moveCommand = new MoveCommand();
		moveCommand.castArgument(new Argument("Test"), Integer.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalHierarchyCastArgumentOnCommand() {
		Function moveCommand = new MoveCommand();
		moveCommand.castArgument(new Argument(1.0), Integer.class);
	}

	@Test
	public void castArgumentOnCommand() {
		Function moveCommand = new MoveCommand();
		Float number = moveCommand.castArgument(new Argument(new Float(1)),
				Float.class);
		assertEquals(number, new Float(1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalCastArgumentToListOnCommand() {
		Function moveCommand = new MoveCommand();
		List<String> stringList = new ArrayList<String>();
		stringList.add("Test");
		moveCommand.castArgumentToList(new Argument(stringList), Integer.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalHierarchyCastToListArgumentOnCommand() {
		Function moveCommand = new MoveCommand();
		List<Object> commandList = new ArrayList<Object>();
		commandList.add(new RemoveCommand());
		moveCommand.castArgumentToList(new Argument(commandList),
				MoveCommand.class);
	}

	@Test
	public void castArgumentToListOnCommand() {
		Function moveCommand = new MoveCommand();
		List<Float> floatList = new ArrayList<Float>();
		floatList.add(new Float(1));
		List<Float> castedFloatList = moveCommand.castArgumentToList(
				new Argument(floatList), Float.class);
		assertEquals(castedFloatList.get(0), new Float(1));
	}

	@Test
	public void emptyBoardTest() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		board.place(Color.RED);
		board.place(Color.BLACK);
		board.move(Orientation.NORTH);
		board.move(Orientation.NORTH);
		board.place(Color.BLUE);
		board.place(Color.BLUE);
		board.empty();
		assertEquals(board.getActualCell().getQuantity(), new Integer(0));
	}

	@Test
	public void getQuantityByColorTest() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		board.place(Color.RED);
		board.place(Color.BLACK);
		assertEquals(board.getQuantityByColor(Color.RED), new Integer(1));
	}

	@Test
	public void cantMoveTest() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		assertEquals(board.canMove(Orientation.SOUTH), Boolean.FALSE);
	}

	@Test
	public void canMoveTest() {
		Board board = new Board(TEST_COL_NUMBER, TEST_ROW_NUMBER);
		assertEquals(board.canMove(Orientation.NORTH), Boolean.TRUE);
	}

	@Test
	public void previousColorTest() {
		assertEquals(Color.BLUE.getPrevious(), Color.GREEN);
	}

	@Test
	public void nextColorTest() {
		assertEquals(Color.GREEN.getNext(), Color.BLUE);
	}

	@Test
	public void previousOrientationTest() {
		assertEquals(Orientation.NORTH.getPrevious(), Orientation.WEST);
	}

	@Test
	public void nextOrientationTest() {
		assertEquals(Orientation.WEST.getNext(), Orientation.NORTH);
	}

}
