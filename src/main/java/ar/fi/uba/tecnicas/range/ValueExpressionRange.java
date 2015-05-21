package ar.fi.uba.tecnicas.range;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.functions.Function;
import ar.fi.uba.tecnicas.model.Board;
import ar.fi.uba.tecnicas.model.GobstonesNumber;
import ar.fi.uba.tecnicas.model.ProgramStack;

public class ValueExpressionRange {

	ValueExpression start;
	ValueExpression end;
	
	List<ValueExpression> rangeList=new ArrayList<ValueExpression>();
	Integer rangeStep = 1;

	public ValueExpressionRange() {

	}
	
	public ValueExpressionRange(Integer step, ValueExpression start, ValueExpression end){
		this.rangeStep=step;
		this.start=start;
		this.end=end;
	}

	public ValueExpressionRange(List<ValueExpression> rangeList) {
		this.rangeList = rangeList;
	}

	public ValueExpressionRange(Integer step, List<ValueExpression> rangeList) {
		this.rangeStep = step;
		this.rangeList = rangeList;
	}

	public Iterator<ValueExpression> iterator(Board board, ProgramStack stack) {
		
		
		if(rangeList.isEmpty()){
			
			Object startValue=start.evaluate(board, stack);
			Object endValue=end.evaluate(board, stack);
			
			if(startValue instanceof Function){
				startValue=((Function) startValue).execute(board, stack).evaluate(board, stack);
			}
			if(endValue instanceof Function){
				endValue=((Function) endValue).execute(board, stack).evaluate(board, stack);
			}
			
			if(startValue instanceof Enum){
				rangeList=this.createEnumList(rangeStep, (Enum)startValue, (Enum)endValue);
			}
			
			if(startValue instanceof GobstonesNumber){
				rangeList=this.createIntegerRangeList(rangeStep, ((GobstonesNumber)startValue).intValue(), ((GobstonesNumber)endValue).intValue());
			}
			
		}
		
		return new RangeIterator<ValueExpression>(rangeStep, rangeList);
	}
	
	private <E extends Enum<E>> List<ValueExpression> createEnumList(Integer step, E start, E end) {
		List<ValueExpression> rangeList = new ArrayList<ValueExpression>();
		EnumSet<E> enumSet = EnumSet.range((E) start,
				(E) end);
		
		enumSet.forEach(match -> {
			rangeList.add(new ValueExpression(match));
		});
		
		return rangeList;
	}
	

	
	public List<ValueExpression> createIntegerRangeList(Integer step, Integer start, Integer end){
		
		IntStream numberStream = IntStream.rangeClosed(start, end);
		List<Integer> integerList = numberStream.boxed().collect(Collectors.toList());
		List<ValueExpression> rangeList = new ArrayList<ValueExpression>();
		integerList.forEach(match ->{
			rangeList.add(new ValueExpression(new GobstonesNumber(match)));
		});
		
		return rangeList;

	}

	
}
