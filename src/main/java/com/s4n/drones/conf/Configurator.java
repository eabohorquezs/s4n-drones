package com.s4n.drones.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurator {
	private static final Configurator instance = new Configurator();	
	private static Properties properties;
	
	private static final String PROPERTIES_FILE_PATH="config.properties";
	
	public static Configurator getInstance() {		
		return instance;
	}
	    
	private Configurator() {
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH)) {
			 properties = new Properties();
					 
            //Load properties file from class path
			properties.load(input);
			
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	public String getProperty(String property) {
		return properties.getProperty(property);
	}
	
}
