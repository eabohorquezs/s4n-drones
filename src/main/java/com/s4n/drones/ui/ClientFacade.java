package com.s4n.drones.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.s4n.drones.conf.Configurator;

public class ClientFacade {
	private static final ClientFacade instance = new ClientFacade();
  private static final Configurator configurator = Configurator.getInstance();

	private static Writer writer = null;
	
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	private static final String FINAL_DRONE_POSITION_REPORT_DIR = MessageFormat.format(configurator.getProperty("FINAL_DRONE_POSITION_REPORT_DIR"), FILE_SEPARATOR);
	private static final String ROUTES_FILE_DIR = MessageFormat.format(configurator.getProperty("ROUTES_FILE_DIR"), FILE_SEPARATOR);
	private static final String OUTPUT_FILES_HEADER = "== Reporte de entregas ==";
	
	
	public static ClientFacade getInstance() {		
		return instance;
	}
	
	private ClientFacade() {
	}
	
	public void writeOutputLine(String output) throws IOException {
		if (writer != null) {
			writer.write(output);
			writer.write(System.lineSeparator());
		}
	}
	
	public Writer getOutputFileWriter(File outputFile) throws IOException {
//		writer = new FileWriter(outputFile, true);
		
		writer = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8);
		
		//Writes output file header
		writer.write(OUTPUT_FILES_HEADER);
		writer.write(System.lineSeparator());
		
		return writer;		
	}
	
	public File getOuputFile(String fileName) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(FINAL_DRONE_POSITION_REPORT_DIR).append(FILE_SEPARATOR);
		    
		File outputFile = new File(sb.toString(), fileName);
		outputFile.createNewFile();
			
		return outputFile;
	}
	
	public Stream<String> getInputFileLines(String filePath) throws IOException, URISyntaxException {
	    StringBuilder sb = new StringBuilder();
	    sb.append(ROUTES_FILE_DIR).append(FILE_SEPARATOR).append(filePath);

		return Files
				.newBufferedReader(Paths.get(sb.toString()))
				.lines();
	}
	
	public void clearOuputDirectory() throws IOException {
		Path path = Paths.get(FINAL_DRONE_POSITION_REPORT_DIR);
		File outputDir = new File(FINAL_DRONE_POSITION_REPORT_DIR);
		
		if (outputDir.exists() && outputDir.isDirectory()) {
			List<File> files = Files.list(Paths.get(FINAL_DRONE_POSITION_REPORT_DIR))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)                                    
                    .collect(Collectors.toList());
			
			files.forEach(file -> file.delete());
		} else {
			outputDir.mkdirs();
		}
	}
}
