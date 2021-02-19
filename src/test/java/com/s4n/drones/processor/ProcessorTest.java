package com.s4n.drones.processor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.s4n.drones.model.Orientation;
import com.s4n.drones.model.Position;

public class ProcessorTest {
	@Test
	public void shouldProcessFirstExampleLine() {
		String firstLineExample = "AAAAIAA";
		Position actualPosition = new Position();
		
		int qtyOfLinesProcessed = Processor.processInputLine(firstLineExample, actualPosition);
		
		assertEquals(1, qtyOfLinesProcessed);		
	}
	
	@Test
	public void shouldModifyCorrectlyFirstExampleLinePosition() {
		String firstLineExample = "AAAAIAA";
		Position position = new Position();
		
		Processor.processInputLine(firstLineExample, position);
		
		assertEquals("(-2,4) dirección Oeste", position.toString());		
	}
	
	@Test
	public void shouldModifyCorrectlySecondExampleLinePosition() {
		String secondLineExample = "DDDAIAD";
		Position position = new Position();
		position.moveToPosition(-2, 4, Orientation.WEST);
		
		Processor.processInputLine(secondLineExample, position);
		
		assertEquals("(-1,3) dirección Sur", position.toString());		
	}
	
	@Test
	public void shouldModifyCorrectlyThirdExampleLinePosition() {
		String thirdLineExample = "AAIADAD";
		Position position = new Position();
		position.moveToPosition(-1, 3, Orientation.SOUTH);
		
		Processor.processInputLine(thirdLineExample, position);
		
		assertEquals("(0,0) dirección Oeste", position.toString());		
	}
	
	@Test
	public void shouldNotProcessLineifItGoesOutFromCoverageArea() {
		String routeWhichGoesOutFromArea = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIAA";
		Position position = new Position();
		
		int qtyOfLinesProcessed = Processor.processInputLine(routeWhichGoesOutFromArea, position);
		
		assertEquals(0, qtyOfLinesProcessed);		
	}

}
