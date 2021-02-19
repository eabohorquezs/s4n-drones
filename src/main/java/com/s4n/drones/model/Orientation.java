package com.s4n.drones.model;

public enum Orientation {
	NORTH("Norte"), 
	EAST("Este"), 
	SOUTH("Sur"), 
	WEST("Oeste");
	
	private String name;
	
	private Orientation(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Orientation getLeftOrientation() {
		switch(this) {
			case NORTH:
				return WEST;
			case WEST:
				return SOUTH;
			case SOUTH:
				return EAST;
			case EAST:
				return NORTH;
			default: 
				return this;
		}		
	}
	
	public Orientation getRightOrientation() {
		switch(this) {
			case NORTH:
				return EAST;
			case EAST:
				return SOUTH;
			case SOUTH:
				return WEST;
			case WEST:
				return NORTH;			
			default: 
				return this;
		}		
	}

}
