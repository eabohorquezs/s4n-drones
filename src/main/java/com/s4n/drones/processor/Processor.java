package com.s4n.drones.processor;

import java.text.MessageFormat;

import com.s4n.drones.model.Orientation;
import com.s4n.drones.model.Position;

public class Processor {
	private static final Character A = new Character('A');
	private static final Character I = new Character('I');
	private static final Character D = new Character('D');
	
	/**
	 * This method process the characters in each instructions line configured in input files.
	 * 
	 * @param deliveryRoute Instructions to get to the delivery point.
	 * @param position Initial position of the drone.
	 * @return 1 if the product is delivered, 0 if the product is not delivered 
	 * 	because the delivery point is outside of the coverage area.
	 */
	public static int processInputLine(String deliveryRoute, Position position) {
		int processed = 1;
		int initialX = position.getX();
		int initialY = position.getY();
		Orientation initialOrientation = position.getOrientation();
		
		for(char charAt : deliveryRoute.toCharArray()) {
        	if(A.equals(charAt)) {
        		try {
        			position.doAdvance();
        		} catch(Exception e) {
        			// If the delivery gets outside of the coverage area the delivery is not performed
        			// The positions remains in its original point
        			System.out.println(MessageFormat.format("Delivery route: {0} - {1}", deliveryRoute, e.getMessage()));
        			processed = 0;
        			position.moveToPosition(initialX, initialY, initialOrientation);
        			break;
        		}
        	} else if(I.equals(charAt)) {
        		position.turnLeft();
        	} else if(D.equals(charAt)) {
        		position.turnRight();
        	}
		}
		
		return processed;
	}

}
