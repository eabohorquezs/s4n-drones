package com.s4n.drones.conf;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConfiguratorTest {
  private static final Configurator configurator = Configurator.getInstance();

	@Test
	public void shouldLoadPropertiesOnConstruction() {
		Configurator.getInstance();
		
		assertEquals("1", configurator.getProperty("MAX_DRONES_QUANTITY"));		
	}

}
