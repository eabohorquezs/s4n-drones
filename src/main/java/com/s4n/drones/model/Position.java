package com.s4n.drones.model;

import java.text.MessageFormat;

import com.s4n.drones.conf.Configurator;

public class Position {
  private static final Configurator configurator = Configurator.getInstance();

	private static final int MAX_BLOCKS_RANGE = Integer.parseInt(configurator.getProperty("MAX_BLOCKS_RANGE"));
	private static final String AREA_ERROR_MSG = "The delivery point is outside of the coverage area.";
	
	private int x;
	private int y;
	private Orientation orientation;
	
	public Position() {
		x = 0;
		y = 0;
		orientation = Orientation.NORTH;
	}
	
	public Position(int x, int y, Orientation orientation) {
		this.x = x;
		this.y = y;
		this.orientation = orientation;
	}
	
	private int getAdvance(int coor, int movement) throws Exception {
		coor += movement;
		
		if((coor) > MAX_BLOCKS_RANGE) {
			throw new Exception(AREA_ERROR_MSG);
		}
		
		return coor;
	}
	
	public Position doAdvance() throws Exception {
		switch(orientation) {
		case NORTH:
			y = getAdvance(y, 1);
			break;
		case WEST:
			x = getAdvance(x, -1);
			break;
		case SOUTH:
			y = getAdvance(y, -1);
			break;
		case EAST:
			x = getAdvance(x, 1);
			break;
		}
		
		return this;
	}
	
	public Position turnLeft() {
		orientation = orientation.getLeftOrientation();
		return this;
	}
	
	public Position turnRight() {
		orientation = orientation.getRightOrientation();
		return this;
	}
	
	public String toString() {
		String msg = "({0},{1}) dirección {2}";
		return MessageFormat.format(msg, this.x, this.y, this.orientation.getName());
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Orientation getOrientation() {
		return orientation;
	}
	
	public void moveToPosition(int newX, int newY, Orientation newOrientation) {
		this.x = newX;
		this.y = newY;
		this.orientation = newOrientation;
	}
}
