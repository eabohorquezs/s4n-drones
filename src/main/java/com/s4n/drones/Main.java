package com.s4n.drones;

import java.io.File;
import java.io.Writer;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Stream;

import com.s4n.drones.conf.Configurator;
import com.s4n.drones.processor.Processor;
import com.s4n.drones.ui.ClientFacade;

import com.s4n.drones.model.Drone;
import com.s4n.drones.model.Orientation;
import com.s4n.drones.model.Position;

public class Main {
	private static final ClientFacade clientFacade = ClientFacade.getInstance();
    private static final Configurator configurator = Configurator.getInstance();
	
	private static final int MAX_DRONES_QUANTITY = Integer.parseInt(configurator.getProperty("MAX_DRONES_QUANTITY"));
	private static final int MAX_QTY_OF_DELIVERIES_PER_TRIP = Integer.parseInt(configurator.getProperty("MAX_QTY_OF_DELIVERIES_PER_TRIP"));
	
	private static int productsDeliveredPerTrip = 0;
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		clientFacade.clearOuputDirectory();
		
		for(int i = 1; i <= MAX_DRONES_QUANTITY; i++) {
			Drone drone = new Drone(i);
			File outputFile = clientFacade.getOuputFile(drone.getOutputFileName());
			
			try (Stream<String> inputLines = clientFacade.getInputFileLines(drone.getInputFileName());
					Writer writer = clientFacade.getOutputFileWriter(outputFile) ) {
				
				inputLines.forEach(line -> {
					// The quantity of deliveries per trip is limited.
					// if the drone reaches this limit, it has to return to the restaurant.
					if(productsDeliveredPerTrip == MAX_QTY_OF_DELIVERIES_PER_TRIP) {
						drone.setPosition(new Position(0, 0, Orientation.NORTH));
						productsDeliveredPerTrip = 0;
					}
					
					productsDeliveredPerTrip += Processor.processInputLine(line, drone.getPosition());
					
					try {
						clientFacade.writeOutputLine(drone.getPosition().toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			} 
			
			productsDeliveredPerTrip = 0;
		}
	}
}
