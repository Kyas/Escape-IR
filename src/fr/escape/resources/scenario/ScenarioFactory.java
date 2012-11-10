package fr.escape.resources.scenario;

import fr.escape.game.scenario.Scenario;

final class ScenarioFactory {

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
				System.out.println(line);
				return true;
			}
			
		};
	}
	
}
