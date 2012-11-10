package fr.escape.game.scenario;

public final class Earth extends AbstractStage {

	public Earth() {
		
		super();
	
		getWaitingScenario().put(Integer.valueOf(1), new Scenario() {
			
			@Override
			public int getStart() {
				return 1;
			}
			
			@Override
			public boolean hasFinished() {
				return false;
			}
			
			@Override
			public void action(int time) {
				System.out.println(time+" "+this);
			}
			
			public String toString() {
				return getID()+" -> "+getStart();
			}

			@Override
			public int getID() {
				return 1;
			}
			
		});
		
		getWaitingScenario().put(Integer.valueOf(2), new Scenario() {
			
			@Override
			public int getStart() {
				return 2;
			}
			
			@Override
			public boolean hasFinished() {
				return false;
			}
			
			@Override
			public void action(int time) {
				System.out.println(time+" "+this);
			}
			
			public String toString() {
				return getID()+" -> "+getStart();
			}

			@Override
			public int getID() {
				return 2;
			}
			
		});
	}
}
