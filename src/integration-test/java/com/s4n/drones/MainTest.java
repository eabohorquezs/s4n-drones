package com.s4n.drones;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.stream.Stream;

import org.junit.Test;

import com.s4n.drones.conf.Configurator;

public class MainTest {
	private static final Configurator configurator = Configurator.getInstance();
	
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	private static final String FINAL_DRONE_POSITION_REPORT_DIR = MessageFormat.format(configurator.getProperty("FINAL_DRONE_POSITION_REPORT_DIR"), FILE_SEPARATOR);
	private static final String RESULTS_FILE_PATH = new StringBuilder(FINAL_DRONE_POSITION_REPORT_DIR).append(FILE_SEPARATOR).append("out1.txt").toString();
	
	private static int resultIndex = 0;
	
	@Test
	public void firstTest() throws IOException, URISyntaxException {
		String expectedResults[] = {"== Reporte de entregas ==", "(-1,-1) dirección Oeste", "(-5,-3) dirección Sur", "(-1,3) dirección Este"};
		
		Main.main(null);
		
		try (Stream<String> inputLines = getResults(RESULTS_FILE_PATH) ) {
			inputLines.forEach(line -> {				
				assertTrue(line.contains(expectedResults[resultIndex]));
				
				resultIndex++;
			});
		}
	}
	
	private Stream<String> getResults(String filePath) throws IOException {
		return Files
				.newBufferedReader(Paths.get(filePath))
				.lines();
	}
}
