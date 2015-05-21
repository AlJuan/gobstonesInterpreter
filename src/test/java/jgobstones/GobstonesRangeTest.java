package jgobstones;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import ar.fi.uba.tecnicas.expressions.ValueExpression;
import ar.fi.uba.tecnicas.model.Color;
import ar.fi.uba.tecnicas.model.GobstonesNumber;
import ar.fi.uba.tecnicas.model.OrderedValue;
import ar.fi.uba.tecnicas.range.ValueExpressionRange;

public class GobstonesRangeTest {
	
	@Test
	public void numberListRangeTest() throws Exception {
		
		List<ValueExpression> rangeList=new ArrayList<ValueExpression>();
		
		rangeList.add(new ValueExpression(new GobstonesNumber(10)));
		rangeList.add(new ValueExpression(new GobstonesNumber(11)));
		rangeList.add(new ValueExpression(new GobstonesNumber(12)));
		rangeList.add(new ValueExpression(new GobstonesNumber(13)));
		rangeList.add(new ValueExpression(new GobstonesNumber(14)));
		
		ValueExpressionRange range=new ValueExpressionRange(rangeList);
		
		Boolean isRangeFromTenToFourteen=true;
		
		int i=10;
		
		for (Iterator<ValueExpression> iterator = range.iterator(null, null); iterator.hasNext();) {
			ValueExpression numberValue = (ValueExpression) iterator.next();
			GobstonesNumber number= (GobstonesNumber)numberValue.evaluate(null, null);
			
			isRangeFromTenToFourteen = number.intValue()==i?true:false;
			i++;
		}
		
		assertTrue(isRangeFromTenToFourteen);

	}
	
	@Test
	public void numberRangeTest() throws Exception {
		
		ValueExpression startValue=new ValueExpression(new GobstonesNumber(10));
		ValueExpression endValue=new ValueExpression(new GobstonesNumber(14));
		Integer step=1;
		
		ValueExpressionRange range=new ValueExpressionRange(step, startValue, endValue);
		
		Boolean isRangeFromTenToFourteen=true;
		
		int i=10;
		
		for (Iterator<ValueExpression> iterator = range.iterator(null, null); iterator.hasNext();) {
			ValueExpression numberValue = (ValueExpression) iterator.next();
			GobstonesNumber number= (GobstonesNumber)numberValue.evaluate(null, null);
			
			isRangeFromTenToFourteen = number.intValue()==i?true:false;
			i++;
		}
		
		assertTrue(isRangeFromTenToFourteen);

	}
	
	@Test
	public void numberRangeTestWithStepTwo() throws Exception {
		
		ValueExpression startValue=new ValueExpression(new GobstonesNumber(10));
		ValueExpression endValue=new ValueExpression(new GobstonesNumber(14));
		Integer step=2;
		
		ValueExpressionRange range=new ValueExpressionRange(step, startValue, endValue);
		
		Boolean isRangeFromTenToFourteenWithStepTwo=true;
		
		int i=10;
		
		for (Iterator<ValueExpression> iterator = range.iterator(null, null); iterator.hasNext();) {
			ValueExpression numberValue = (ValueExpression) iterator.next();
			GobstonesNumber number= (GobstonesNumber)numberValue.evaluate(null, null);
			
			isRangeFromTenToFourteenWithStepTwo = number.intValue()==i?true:false;
			i=i+2;
		}
		
		assertTrue(isRangeFromTenToFourteenWithStepTwo);

	}
	
	@Test
	public void colorRangeTest() throws Exception {
		
		ValueExpression startValue=new ValueExpression(Color.BLACK);
		ValueExpression endValue=new ValueExpression(Color.GREEN);
		Integer step=1;
		
		ValueExpressionRange range=new ValueExpressionRange(step, startValue, endValue);
		
		Boolean isColorFromBlackToGreen=true;
		
		OrderedValue expectedColor=Color.BLACK;
		
		for (Iterator<ValueExpression> iterator = range.iterator(null, null); iterator.hasNext();) {
			ValueExpression colorValue = (ValueExpression) iterator.next();
			Color rangeColor= (Color)colorValue.evaluate(null, null);
			
			isColorFromBlackToGreen = rangeColor.equals(expectedColor);
			
			expectedColor=expectedColor.getNext();
		}
		
		assertTrue(isColorFromBlackToGreen);

	}
	
	@Test
	public void colorRangeTestWithStepTwo() throws Exception {
		
		ValueExpression startValue=new ValueExpression(Color.BLUE);
		ValueExpression endValue=new ValueExpression(Color.GREEN);
		Integer step=2;
		
		ValueExpressionRange range=new ValueExpressionRange(step, startValue, endValue);
		
		Boolean isColorFromBlueToGreenWithStepTwo=true;
		
		OrderedValue expectedColor=Color.BLUE;
		
		for (Iterator<ValueExpression> iterator = range.iterator(null, null); iterator.hasNext();) {
			ValueExpression colorValue = (ValueExpression) iterator.next();
			Color rangeColor= (Color)colorValue.evaluate(null, null);
			
			isColorFromBlueToGreenWithStepTwo = rangeColor.equals(expectedColor);
			
			expectedColor=expectedColor.getNext();
			expectedColor=expectedColor.getNext();
		}
		
		assertTrue(isColorFromBlueToGreenWithStepTwo);

	}



}
