package fr.escape.resources.scenario;

import java.util.Arrays;

import fr.escape.app.Foundation;
import fr.escape.game.scenario.Scenario;

final class ScenarioFactory {
	
	/**
	 * Execute Index
	 */
	private static final int LINE_TIME = 0;
	private static final int LINE_COMMAND = 1;
	private static final int COMMAND_ACTION = 0;
	private static final int COMMAND_ARGS = 1;
	

	static Scenario create(final ScenarioConfiguration scenario) {
		return new Scenario() {
			
			private final int id = scenario.getID();
			private final int start = scenario.getTime();
			private final String[] script = scenario.getScript();
			
			private int cursor = 0;
			
			@Override
			public boolean hasFinished() {
				return cursor == script.length;
			}
			
			@Override
			public int getStart() {
				return start;
			}
			
			@Override
			public int getID() {
				return id;
			}
			
			@Override
			public void action(int time) {
				
				while(!hasFinished() && execute(script[cursor], time)) {
					cursor++;
				}
				
			}
			
			private boolean execute(String line, int time) {
				try {
					
					String[] lineArray = line.split("\\s+", 2);
					
					int timeline = Integer.parseInt(lineArray[LINE_TIME]);
					
					if(timeline > time) {
						return false;
					}
					
					String[] commandArray = lineArray[LINE_COMMAND].split("\\s", 2);
					
					System.err.println(Arrays.toString(commandArray));
					
					String[] commandArgs = commandArray[COMMAND_ARGS].split("\\s+");
					
					switch(commandArray[COMMAND_ACTION]) {
						case "spawn": {
							spawn(commandArgs);
							break;
						}
						case "move": {
							move(commandArgs);
							break;
						}
						case "fire":  {
							fire(commandArgs);
							break;
						}
						default: {
							throw new IllegalArgumentException("Unknwon Command");
						}
					}

				} catch(Exception e) {
					Foundation.ACTIVITY.error("Scenario - "+getID(), "An error has occurred", e);
				}
				return true;
			}
			
			private void fire(String... args) {
				
				int shipID = Integer.parseInt(args[0]);
				
				System.out.println("Ship "+shipID+" fire");
				
			}
			
			private void move(String... args) {
				
				int shipID = Integer.parseInt(args[0]);
				int shipX = Integer.parseInt(args[1]);
				int shipY = Integer.parseInt(args[2]);
				
			}
			
			private void spawn(String[] args) {
				
				int shipID = Integer.parseInt(args[0]);
				
			}
			
		};
	}
	
}
