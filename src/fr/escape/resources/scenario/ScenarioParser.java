package fr.escape.resources.scenario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import fr.escape.game.entity.ships.Ship;
import fr.escape.game.entity.ships.ShipFactory;
import fr.escape.game.scenario.Scenario;

public final class ScenarioParser {

	/**
	 * Section 2 index
	 */
	private static final int SECTION_2_SHIP_ID = 0;
	private static final int SECTION_2_SHIP_TYPE = SECTION_2_SHIP_ID + 1;
	private static final int SECTION_2_SHIP_X = SECTION_2_SHIP_TYPE + 1;
	private static final int SECTION_2_SHIP_Y = SECTION_2_SHIP_X + 1;
	
	/**
	 * Exception default message
	 */
	private static final String EXCEPTION_MESSAGE = "Scenario file is corrupt";
	
	/**
	 * EOF Section
	 */
	private static final int END_SECTION = 5;
	
	/**
	 * Cannot be instantiate
	 */
	private ScenarioParser() {}
	
	/**
	 * <p>
	 * Parse the given file and return a {@link Scenario}.
	 * 
	 * @param file File to parse
	 * @return Scenario
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Scenario parse(File file) throws FileNotFoundException, IOException {

		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String line;
		int section = 0;
		ScenarioConfiguration config = new ScenarioConfiguration();
		
		try {
			
			while((line = reader.readLine()) != null) {
				
				if(line.equals("%%")) {
					
					section++;
					
					if(section == END_SECTION) {
						return ScenarioFactory.create(config);
					}
					
				} else {
					
					System.out.println(section);
					
					switch(section) {
						case 1: {
							section1(line, config);
							break;
						}
						case 2: {
							section2(line, config);
							break;
						}
						case 3: {
							section3(line, config);
							break;
						}
						case 4: {
							section4(line, config);
							break;
						}
						default: {
							throw new IOException(EXCEPTION_MESSAGE+": Unknown Section %%"+section);
						}
					}
					
				}
				
			}
			
		} finally {
			reader.close();
		}
		
		throw new IOException(EXCEPTION_MESSAGE+": Information for Scenario are missing");
	}
	
	private static void section1(String line, ScenarioConfiguration configuration) throws IOException {
		try {
			configuration.setID(Integer.parseInt(line));
		} catch(Exception e) {
			throw new IOException(EXCEPTION_MESSAGE, e);
		}
	}
	
	private static void section2(String line, ScenarioConfiguration configuration) throws IOException {
		try {
			configuration.setTime(Integer.parseInt(line));
		} catch(Exception e) {
			throw new IOException(EXCEPTION_MESSAGE, e);
		}
	}
	
	private static void section3(String line, ScenarioConfiguration configuration) throws IOException {
		try {
			
			String[] inputConfig = line.split("\\s+");
			
			int shipID = Integer.parseInt(inputConfig[SECTION_2_SHIP_ID]);
			int shipType = Integer.parseInt(inputConfig[SECTION_2_SHIP_TYPE]);
			float shipX = Float.parseFloat(inputConfig[SECTION_2_SHIP_X]);
			float shipY = Float.parseFloat(inputConfig[SECTION_2_SHIP_Y]);
			
			// ShipFactory sf;
			
		} catch(Exception e) {
			throw new IOException(EXCEPTION_MESSAGE, e);
		}
	}
	
	private static void section4(String line, ScenarioConfiguration configuration) throws IOException {
		try {
			configuration.addScript(line);
		} catch(Exception e) {
			throw new IOException(EXCEPTION_MESSAGE, e);
		}
	}
	
}
