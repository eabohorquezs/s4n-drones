package com.s4n.drones.model;

import java.text.MessageFormat;

import com.s4n.drones.conf.Configurator;

public class Drone {
  private static final Configurator configurator = Configurator.getInstance();
	private static final int MAX_DRONES_QUANTITY = Integer.parseInt(configurator.getProperty("MAX_DRONES_QUANTITY"));
	
	private String name;
	private Position position;
	private String inputFileName;
	private String outputFileName;
	
	public Drone(int id) {
		this.name = getName(id);
		this.inputFileName = MessageFormat.format(configurator.getProperty("INPUT_FILE_NAME_FORMAT"), name);
		this.outputFileName = MessageFormat.format(configurator.getProperty("OUTPUT_FILE_NAME_FORMAT"), name);
		position = new Position(0, 0, Orientation.NORTH);
	}
	
	private String getName(int droneId) {
		int droneIdLength = (int) (Math.log10(droneId) + 1);
		int maxQtyDronesLength = (int) (Math.log10(MAX_DRONES_QUANTITY) + 1);
		int requiredZerosQty = maxQtyDronesLength - droneIdLength + 1;
		
		if(requiredZerosQty < 1) {
			return Integer.toString(droneId);
		} else {
			String zerosFormat = MessageFormat.format("%0{0}d", requiredZerosQty);
			
			return String.format(zerosFormat, droneId);
		}		
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
}
