package jgobstones;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import ar.fi.uba.tecnicas.commands.Command;
import ar.fi.uba.tecnicas.expressions.Expression;
import ar.fi.uba.tecnicas.expressions.SubstractExpression;
import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.expressions.VariableExpression;
import ar.fi.uba.tecnicas.functions.BallsQuantityFunction;
import ar.fi.uba.tecnicas.functions.CanMoveFunction;
import ar.fi.uba.tecnicas.functions.ContainsFunction;
import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.functions.MaxBoolFunction;
import ar.fi.uba.tecnicas.functions.MaxColorFunction;
import ar.fi.uba.tecnicas.functions.MaxOrientationFunction;
import ar.fi.uba.tecnicas.functions.MinBoolFunction;
import ar.fi.uba.tecnicas.functions.MinColorFunction;
import ar.fi.uba.tecnicas.functions.MinOrientationFunction;
import ar.fi.uba.tecnicas.functions.NextFunction;
import ar.fi.uba.tecnicas.functions.OppositeFunction;
import ar.fi.uba.tecnicas.functions.PreviousFunction;
import ar.fi.uba.tecnicas.model.Argument;
import ar.fi.uba.tecnicas.model.CodeBlock;
import ar.fi.uba.tecnicas.model.Color;
import ar.fi.uba.tecnicas.model.GobstonesNumber;
import ar.fi.uba.tecnicas.model.OrderedValue;
import ar.fi.uba.tecnicas.model.Orientation;
import ar.fi.uba.tecnicas.model.Program;
import ar.fi.uba.tecnicas.model.ProgramStack;
import ar.fi.uba.tecnicas.parser.GobstonesEvaluator;
import ar.fi.uba.tecnicas.range.ValueExpressionRange;

public class GobstonesEvaluatorTest {

	private static final int ASSIGNMENT_COMMAND_VARIABLE_NAME_POSITION = 0;
	private static final int CAN_MOVE_FUNCTION_ARGUMENT_POSITION = 0;
	private static final int BALLS_QUANTITY_FUNCTION_ARGUMENT_POSITION = 0;
	private static final int CONTAINS_FUNCTION_ARGUMENT_POSITION = 0;
	private static final int ASSIGNMENT_COMMAND_VALUE_EXPRESSION_POSITION = 1;
	private static final int FOREACH_COMMAND_RANGE_ARGUMENT_POSITION = 1;
	private static final int FOREACH_COMMAND_VARIABLE_ARGUMENT_POSITION = 0;
	private static final int REMOVE_COMMAND_ARGUMENT_LIST_POSITION = 0;
	private static final int MOVE_COMMAND_ARGUMENT_LIST_POSTION = 0;
	private static final int PLACE_COMMAND_ARGUMENT_LIST_POSITION = 0;
	private static final int ELSE_COMMAND_BLOCK_LIST_POSITION = 2;
	private static final int IF_COMMAND_BLOCK_LIST_POSITION = 1;
	private static final int REPEAT_COMMAND_BLOCK_LIST_POSITION = 1;
	private static final int REPEAT_COMMAND_ARGUMENT_LIST_POSITION = 0;
	private static final int WHILE_COMMAND_ARGUMENT_LIST_POSITION = 0;
	private static final int WHILE_COMMAND_BLOCK_LIST_POSITION = 1;
	private static final int FIRST_COMMAND_OF_THE_LIST = 0;


	private final GobstonesEvaluator evaluator = new GobstonesEvaluator();

	@Test
	public void testEmptyProgram() {
		String input = new String("program{\n }");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();
		assertTrue(commandList.isEmpty());

	}

	@Test
	public void testNotEmptyProgram() {
		String input = new String(
				"program{\n Mover(Este); repeat(4){\n Poner(Negro)}}");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();
		assertTrue(commandList.size() == 2);

	}

	@Test
	public void testRepeatArgument() {
		String input = new String("program{\n repeat(4){\n}}");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();

		Function repeatCommand = (Function) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);
		Argument argument = repeatCommand.getArguments().get(
				REPEAT_COMMAND_ARGUMENT_LIST_POSITION);
		Expression expression = (Expression) argument.getValue();

		Integer repeatArgument = new Integer(4);
		assertTrue(repeatArgument.equals(((GobstonesNumber) expression
				.evaluate(null, null)).intValue()));

	}

	@Test
	public void testRepeatCommandBlockEmpty() {
		String input = new String("program{\n repeat(4){\n}}");

		Program program = evaluator.evaluate(input);
		List programCommandList = program.getCodeBlock().getCodeLines();

		Function repeatCommand = (Function) programCommandList
				.get(FIRST_COMMAND_OF_THE_LIST);
		Argument argument = repeatCommand.getArguments().get(
				REPEAT_COMMAND_BLOCK_LIST_POSITION);
		List repeatCommandList = ((CodeBlock) argument.getValue())
				.getCodeLines();

		assertTrue(repeatCommandList.isEmpty());

	}

	@Test
	public void testRepeatCommandBlockNotEmpty() {
		String input = new String(
				"program{\n repeat(4){\n Mover(Norte); Poner(Rojo);}}");

		Program program = evaluator.evaluate(input);
		List programCommandList = program.getCodeBlock().getCodeLines();

		Function repeatCommand = (Function) programCommandList
				.get(FIRST_COMMAND_OF_THE_LIST);
		Argument argument = repeatCommand.getArguments().get(
				REPEAT_COMMAND_BLOCK_LIST_POSITION);
		List repeatCommandList = ((CodeBlock) argument.getValue())
				.getCodeLines();

		assertTrue(repeatCommandList.size() == 2);

	}

	@Test
	public void testIfCommandBlockEmpty() {
		String input = new String("program{\n if(nroBolitas(Negro)>2){}}");

		Program program = evaluator.evaluate(input);
		List programCommandList = program.getCodeBlock().getCodeLines();

		Function ifCommand = (Function) programCommandList
				.get(FIRST_COMMAND_OF_THE_LIST);
		Argument argument = ifCommand.getArguments().get(
				IF_COMMAND_BLOCK_LIST_POSITION);
		List ifCommandList = ((CodeBlock) argument.getValue()).getCodeLines();

		assertTrue(ifCommandList.isEmpty());

	}

	@Test
	public void testIfCommandBlockNotEmpty() {
		String input = new String(
				"program{\n if(1256<1){\n Mover(Norte);Poner(Rojo);} }");

		Program program = evaluator.evaluate(input);
		List programCommandList = program.getCodeBlock().getCodeLines();

		Function ifCommand = (Function) programCommandList
				.get(FIRST_COMMAND_OF_THE_LIST);
		Argument argument = ifCommand.getArguments().get(
				IF_COMMAND_BLOCK_LIST_POSITION);
		List ifCommandList = ((CodeBlock) argument.getValue()).getCodeLines();

		assertTrue(ifCommandList.size() == 2);

	}

	@Test
	public void testIfThenCommandBlockNotEmpty() {
		String input = new String(
				"program{\n if(1256<1) then {\n Mover(Norte); Poner(Rojo);} }");

		Program program = evaluator.evaluate(input);
		List programCommandList = program.getCodeBlock().getCodeLines();

		Function ifCommand = (Function) programCommandList
				.get(FIRST_COMMAND_OF_THE_LIST);
		Argument argument = ifCommand.getArguments().get(
				IF_COMMAND_BLOCK_LIST_POSITION);
		List ifCommandList = ((CodeBlock) argument.getValue()).getCodeLines();

		assertTrue(ifCommandList.size() == 2);

	}

	@Test
	public void testIfThenElseCommandBlockNotEmpty() {
		String input = new String(
				"program{\n if(1256<1) then {\n Mover(Norte);} else {\n Poner(Rojo); Mover(Sur);}}");

		Program program = evaluator.evaluate(input);
		List programCommandList = program.getCodeBlock().getCodeLines();

		Function ifCommand = (Function) programCommandList
				.get(FIRST_COMMAND_OF_THE_LIST);
		Argument elseArgument = ifCommand.getArguments().get(
				ELSE_COMMAND_BLOCK_LIST_POSITION);
		List elseCommandList = ((CodeBlock) elseArgument.getValue())
				.getCodeLines();

		assertTrue(elseCommandList.size() == 2);

	}

	@Test
	public void testIfThenElseCommandBlockEmpty() {
		String input = new String("program{\n if(1256<1) then {\n } else {}}");

		Program program = evaluator.evaluate(input);
		List programCommandList = program.getCodeBlock().getCodeLines();

		Function ifCommand = (Function) programCommandList
				.get(FIRST_COMMAND_OF_THE_LIST);

		Argument ifargument = ifCommand.getArguments().get(
				IF_COMMAND_BLOCK_LIST_POSITION);
		List ifCommandList = ((CodeBlock) ifargument.getValue()).getCodeLines();

		Argument elseArgument = ifCommand.getArguments().get(
				ELSE_COMMAND_BLOCK_LIST_POSITION);
		List elseCommandList = ((CodeBlock) elseArgument.getValue())
				.getCodeLines();

		assertTrue(ifCommandList.isEmpty() && elseCommandList.isEmpty());

	}

	@Test
	public void testWhileArgument() {
		String input = new String("program{\n while(1+(12+14)) {\n}}");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();

		Function whileCommand = (Function) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);
		Argument argument = whileCommand.getArguments().get(
				WHILE_COMMAND_ARGUMENT_LIST_POSITION);
		Expression expression = (Expression) argument.getValue();

		Integer whileArgument = new Integer(27);
		assertTrue(whileArgument.equals(((GobstonesNumber) expression
				.evaluate(null,null)).intValue()));

	}

	@Test
	public void testWhileCommandBlockEmpty() {
		String input = new String("program{\n while((1<2) || not True) {\n}}");

		Program program = evaluator.evaluate(input);
		List programCommandList = program.getCodeBlock().getCodeLines();

		Function whileCommand = (Function) programCommandList
				.get(FIRST_COMMAND_OF_THE_LIST);
		Argument argument = whileCommand.getArguments().get(
				WHILE_COMMAND_BLOCK_LIST_POSITION);
		List whileCommandList = ((CodeBlock) argument.getValue())
				.getCodeLines();

		assertTrue(whileCommandList.isEmpty());

	}

	@Test
	public void testWhileCommandBlockNotEmpty() {
		String input = new String(
				"program{\n while(1<7)\n {\n Mover(Norte); Poner(Rojo);\n}\n}");

		Program program = evaluator.evaluate(input);
		List programCommandList = program.getCodeBlock().getCodeLines();

		Function whileCommand = (Function) programCommandList
				.get(FIRST_COMMAND_OF_THE_LIST);
		Argument argument = whileCommand.getArguments().get(
				WHILE_COMMAND_BLOCK_LIST_POSITION);
		List whileCommandList = ((CodeBlock) argument.getValue())
				.getCodeLines();

		assertTrue(whileCommandList.size() == 2);

	}

	@Test
	public void testForEachCommandVariableArgument() {
		String input = new String("program{\n foreach var in [1..5] {\n}}");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();

		Function foreachCommand = (Function) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);
		Argument argument = foreachCommand.getArguments().get(
				FOREACH_COMMAND_VARIABLE_ARGUMENT_POSITION);

		String variableName = "var";

		assertTrue(argument.getValue().toString().equals(variableName));
	}

	@Test
	public void testForEachCommandRangeArgument() {
		String input = new String("program{\n foreach var in [1..5] {\n}}");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();

		Command foreachCommand = (Command) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);
		Argument argument = foreachCommand.getArguments().get(
				FOREACH_COMMAND_RANGE_ARGUMENT_POSITION);

		ValueExpressionRange range = (ValueExpressionRange) argument.getValue();

		Boolean isRangeFromOneToFive = true;

		int expectedNumber = 1;

		for (Iterator<ValueExpression> iterator = range.iterator(null, null); iterator
				.hasNext();) {
			GobstonesNumber gobInteger = (GobstonesNumber) iterator.next()
					.evaluate(null, null);

			Integer number = (Integer) gobInteger.intValue();

			isRangeFromOneToFive = number == expectedNumber ? true : false;
			expectedNumber++;
		}

		assertTrue(isRangeFromOneToFive);
	}

	@Test
	public void testForEachCommandRangeFunctionArgument() {
		String input = new String(
				"program{\n foreach var in [minColor()..maxColor()] {\n}}");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();

		Command foreachCommand = (Command) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);
		Argument argument = foreachCommand.getArguments().get(
				FOREACH_COMMAND_RANGE_ARGUMENT_POSITION);

		ValueExpressionRange range = (ValueExpressionRange) argument.getValue();

		Boolean isFullRangeColor = true;

		OrderedValue expectedColor = Color.BLUE;

		for (Iterator<ValueExpression> iterator = range.iterator(null, null); iterator
				.hasNext();) {
			Color rangeColor = (Color) iterator.next().evaluate(null, null);

			isFullRangeColor = expectedColor.equals(rangeColor) ? true : false;
			expectedColor = expectedColor.getNext();

		}

		assertTrue(isFullRangeColor);
	}

	@Test
	public void testMoveCommand() {
		String input = new String("program{\n Mover(Sur)}");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();

		Function moveCommand = (Function) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);
		Argument argument = moveCommand.getArguments().get(
				MOVE_COMMAND_ARGUMENT_LIST_POSTION);
		ValueExpression orientation = (ValueExpression) argument.getValue();

		assertTrue(orientation.evaluate(null, null).equals(
				Orientation.SOUTH));

	}

	@Test
	public void testPlaceCommand() {
		String input = new String("program{\n Poner(Azul)}");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();

		Function placeCommand = (Function) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);
		Argument argument = placeCommand.getArguments().get(
				PLACE_COMMAND_ARGUMENT_LIST_POSITION);
		ValueExpression color = (ValueExpression) argument.getValue();

		assertTrue(color.evaluate(null, null).equals(Color.BLUE));

	}

	@Test
	public void testRemoveCommand() {
		String input = new String("program{\n Sacar(Verde)}");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();

		Function removeCommand = (Function) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);
		Argument argument = removeCommand.getArguments().get(
				REMOVE_COMMAND_ARGUMENT_LIST_POSITION);
		ValueExpression color = (ValueExpression) argument.getValue();

		assertTrue(color.evaluate(null, null).equals(Color.GREEN));

	}

	@Test
	public void testContainsFunction() throws Exception {
		String input = new String("program {\n var:=hayBolitas(Verde);} ");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();

		Function assignmentCommand = (Function) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);

		ValueExpression valueExp = (ValueExpression) assignmentCommand
				.getArguments()
				.get(ASSIGNMENT_COMMAND_VALUE_EXPRESSION_POSITION).getValue();

		ContainsFunction containsFunction = (ContainsFunction) valueExp
				.evaluate(null, null);

		ValueExpression color = (ValueExpression) containsFunction
				.getArguments().get(CONTAINS_FUNCTION_ARGUMENT_POSITION)
				.getValue();

		assertTrue(color.evaluate(null,null).equals(Color.GREEN));

	}

	@Test
	public void testBallsQuantityFunction() throws Exception {
		String input = new String("program {\n var:=nroBolitas(Rojo);} ");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();

		Function assignmentCommand = (Function) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);

		ValueExpression valueExp = (ValueExpression) assignmentCommand
				.getArguments()
				.get(ASSIGNMENT_COMMAND_VALUE_EXPRESSION_POSITION).getValue();

		BallsQuantityFunction ballsQuantityFunction = (BallsQuantityFunction) valueExp
				.evaluate(null, null);

		ValueExpression color = (ValueExpression) ballsQuantityFunction
				.getArguments().get(BALLS_QUANTITY_FUNCTION_ARGUMENT_POSITION)
				.getValue();

		assertTrue(color.evaluate(null, null).equals(Color.RED));

	}

	@Test
	public void testCanMoveFunction() throws Exception {
		String input = new String("program {\n var:=puedeMover(Norte); \n} ");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();

		Function assignmentCommand = (Function) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);

		ValueExpression valueExp = (ValueExpression) assignmentCommand
				.getArguments()
				.get(ASSIGNMENT_COMMAND_VALUE_EXPRESSION_POSITION).getValue();

		CanMoveFunction canMoveFunction = (CanMoveFunction) valueExp
				.evaluate(null, null);

		ValueExpression color = (ValueExpression) canMoveFunction
				.getArguments().get(CAN_MOVE_FUNCTION_ARGUMENT_POSITION)
				.getValue();

		assertTrue(color.evaluate(null, null).equals(Orientation.NORTH));

	}

	@Test
	public void testMaxBool() throws Exception {
		String input = new String("program {\n var:=maxBool(); \n} ");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();

		Function assignmentCommand = (Function) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);

		ValueExpression valueExp = (ValueExpression) assignmentCommand
				.getArguments()
				.get(ASSIGNMENT_COMMAND_VALUE_EXPRESSION_POSITION).getValue();

		MaxBoolFunction maxBoolFunction = (MaxBoolFunction) valueExp
				.evaluate(null, null);

		ValueExpression maxBoolValue = (ValueExpression) maxBoolFunction
				.execute(null, null);

		assertTrue(maxBoolValue.evaluate(null, null).equals(Boolean.TRUE));

	}

	@Test
	public void testMinBool() throws Exception {
		String input = new String("program {\n var:=minBool(); \n} ");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();

		Function assignmentCommand = (Function) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);

		ValueExpression valueExp = (ValueExpression) assignmentCommand
				.getArguments()
				.get(ASSIGNMENT_COMMAND_VALUE_EXPRESSION_POSITION).getValue();

		MinBoolFunction minBoolFunction = (MinBoolFunction) valueExp
				.evaluate(null, null);

		ValueExpression minBoolValue = (ValueExpression) minBoolFunction
				.execute(null, null);

		assertTrue(minBoolValue.evaluate(null, null).equals(Boolean.FALSE));

	}

	@Test
	public void testMinColor() throws Exception {
		String input = new String("program {\n var:=minColor(); \n} ");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();

		Function assignmentCommand = (Function) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);

		ValueExpression valueExp = (ValueExpression) assignmentCommand
				.getArguments()
				.get(ASSIGNMENT_COMMAND_VALUE_EXPRESSION_POSITION).getValue();

		MinColorFunction minColorFunction = (MinColorFunction) valueExp
				.evaluate(null, null);

		ValueExpression minColorValue = (ValueExpression) minColorFunction
				.execute(null, null);

		assertTrue(minColorValue.evaluate(null, null).equals(Color.BLUE));

	}

	@Test
	public void testMaxColor() throws Exception {
		String input = new String("program {\n var:=maxColor(); \n} ");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();

		Function assignmentCommand = (Function) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);

		ValueExpression valueExp = (ValueExpression) assignmentCommand
				.getArguments()
				.get(ASSIGNMENT_COMMAND_VALUE_EXPRESSION_POSITION).getValue();

		MaxColorFunction maxColorFunction = (MaxColorFunction) valueExp
				.evaluate(null, null);

		ValueExpression maxColorValue = (ValueExpression) maxColorFunction
				.execute(null, null);

		assertTrue(maxColorValue.evaluate(null, null).equals(Color.GREEN));

	}

	@Test
	public void testMaxOrientation() throws Exception {
		String input = new String("program {\n var:=maxDir(); \n} ");

		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();

		Function assignmentCommand = (Function) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);

		ValueExpression valueExp = (ValueExpression) assignmentCommand
				.getArguments()
				.get(ASSIGNMENT_COMMAND_VALUE_EXPRESSION_POSITION).getValue();

		MaxOrientationFunction maxOrientationFunction = (MaxOrientationFunction) valueExp
				.evaluate(null, null);

		ValueExpression maxOrientationValue = (ValueExpression) maxOrientationFunction
				.execute(null, null);

		assertTrue(maxOrientationValue.evaluate(null, null).equals(Orientation.WEST));

	}

	@Test
	public void testMinOrientation() throws Exception {
		String input = new String("program {\n var:=minDir(); \n} ");

		Program program = evaluator.evaluate(input);
		List<Function> commandList = program.getCodeBlock().getCodeLines();

		Function assignmentCommand = commandList.get(FIRST_COMMAND_OF_THE_LIST);

		ValueExpression valueExp = (ValueExpression) assignmentCommand
				.getArguments()
				.get(ASSIGNMENT_COMMAND_VALUE_EXPRESSION_POSITION).getValue();

		MinOrientationFunction minOrientationFunction = (MinOrientationFunction) valueExp
				.evaluate(null, null);

		ValueExpression minOrientationValue = (ValueExpression) minOrientationFunction
				.execute(null, null);

		assertTrue(minOrientationValue.evaluate(null, null).equals(Orientation.NORTH));

	}

	@Test
	public void testNextFunction() throws Exception {
		String input = new String("program {\n var:=siguiente(Azul); \n} ");

		Program program = evaluator.evaluate(input);
		List<Function> commandList = program.getCodeBlock().getCodeLines();

		Function assignmentCommand = commandList.get(FIRST_COMMAND_OF_THE_LIST);

		ValueExpression valueExp = (ValueExpression) assignmentCommand
				.getArguments()
				.get(ASSIGNMENT_COMMAND_VALUE_EXPRESSION_POSITION).getValue();

		NextFunction nextFunction = (NextFunction) valueExp
				.evaluate(null, null);

		ValueExpression nextValue = (ValueExpression) nextFunction.execute(
				null, null);

		assertTrue(nextValue.evaluate(null, null).equals(Color.BLACK));

	}

	@Test
	public void testPreviousFunction() throws Exception {
		String input = new String("program {\n var:=previo(Verde); \n} ");

		Program program = evaluator.evaluate(input);
		List<Function> commandList = program.getCodeBlock().getCodeLines();

		Function assignmentCommand = commandList.get(0);

		ValueExpression valueExp = (ValueExpression) assignmentCommand
				.getArguments().get(1).getValue();

		PreviousFunction previousFunction = (PreviousFunction) valueExp
				.evaluate(null, null);

		ValueExpression previousValue = (ValueExpression) previousFunction
				.execute(null, null);

		assertTrue(previousValue.evaluate(null, null).equals(Color.RED));

	}

	@Test
	public void testOppositeFunction() throws Exception {
		String input = new String("program {\n var:=opuesto(Norte); \n} ");

		Program program = evaluator.evaluate(input);
		List<Function> commandList = program.getCodeBlock().getCodeLines();

		Function assignmentCommand = commandList.get(FIRST_COMMAND_OF_THE_LIST);

		ValueExpression valueExp = (ValueExpression) assignmentCommand
				.getArguments()
				.get(ASSIGNMENT_COMMAND_VALUE_EXPRESSION_POSITION).getValue();

		OppositeFunction oppositeFunction = (OppositeFunction) valueExp
				.evaluate(null, null);

		ValueExpression oppositeValue = (ValueExpression) oppositeFunction
				.execute(null, null);

		assertTrue(oppositeValue.evaluate(null, null).equals(Orientation.SOUTH));

	}

	@Test
	public void testSubstraction() throws Exception {
		String input = new String("program {\n Mover(-Oeste); \n} ");

		Program program = evaluator.evaluate(input);

		List commandList = program.getCodeBlock().getCodeLines();
		Function moveCommand = (Function) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);
		Argument argument = moveCommand.getArguments().get(
				MOVE_COMMAND_ARGUMENT_LIST_POSTION);
		Expression orientation = (Expression) argument.getValue();

		assertTrue(orientation.evaluate(null, null).equals(
				Orientation.EAST));

	}

	@Test
	public void testNumberSubstraction() throws Exception {
		String input = new String("program{\n var:=12-3; \n}");

		Program program = evaluator.evaluate(input);

		List commandList = program.getCodeBlock().getCodeLines();

		Function assignmentCommand = (Function) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);

		VariableExpression varExp = (VariableExpression) assignmentCommand
				.getArguments().get(ASSIGNMENT_COMMAND_VARIABLE_NAME_POSITION)
				.getValue();
		SubstractExpression valueExp = (SubstractExpression) assignmentCommand
				.getArguments()
				.get(ASSIGNMENT_COMMAND_VALUE_EXPRESSION_POSITION).getValue();

		Boolean isSameNameVariable = varExp.getVariableName().equals("var");
		Boolean isSameValue = ((GobstonesNumber) valueExp
				.evaluate(null, null)).intValue()
				.equals(new Integer(9));

		assertTrue(isSameNameVariable && isSameValue);
	}

	@Test
	public void testSimpleAssignmentCommand() throws Exception {
		String input = new String("program{\n var:=123; \n}");

		Program program = evaluator.evaluate(input);

		List commandList = program.getCodeBlock().getCodeLines();

		Function assignmentCommand = (Function) commandList
				.get(FIRST_COMMAND_OF_THE_LIST);

		VariableExpression varExp = (VariableExpression) assignmentCommand
				.getArguments().get(ASSIGNMENT_COMMAND_VARIABLE_NAME_POSITION)
				.getValue();
		ValueExpression valueExp = (ValueExpression) assignmentCommand
				.getArguments()
				.get(ASSIGNMENT_COMMAND_VALUE_EXPRESSION_POSITION).getValue();

		Boolean isSameNameVariable = varExp.getVariableName().equals("var");
		Boolean isSameValue = ((GobstonesNumber) valueExp
				.evaluate(null, null)).intValue().equals(
				new Integer(123));

		assertTrue(isSameNameVariable && isSameValue);
	}
	
	@Test
	public void testName() throws Exception {
		
		String input=new String("program   {	IrAlBorde(Norte);IrAlBorde(Oeste);	Poner(Negro);Poner(Negro);	repeat(nroBolitas(Negro))  {   		Mover(Este);Poner(Rojo);	  }   }");
		
		Program program = evaluator.evaluate(input);
		List commandList = program.getCodeBlock().getCodeLines();
		
	}

}
