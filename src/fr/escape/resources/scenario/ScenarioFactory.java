package fr.escape.resources.scenario;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Map.Entry;

import fr.escape.app.Foundation;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.ships.Ship;
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
			private final HashMap<Integer, Ship> ships = scenario.getShip();
			private final HashSet<Integer> spawns = new HashSet<Integer>();
			private final String[] script = scenario.getScript();

			private EntityContainer container;
			private int cursor = 0;

			@Override
			public boolean setContainer(EntityContainer container) {
				this.container = Objects.requireNonNull(container);
				return true;
			}
			
			@Override
			public boolean hasFinished() {

				// Check if we reach the End of Script
				if(endOfScript()) {
					return true;
				}

				Iterator<Entry<Integer, Ship>> it = ships.entrySet().iterator();

				while (it.hasNext()) {

					Entry<Integer, Ship> row = it.next();

					if(spawns.contains(row.getKey()) && !getContainer().contains(row.getValue())) {
						spawns.remove(row.getKey());
						it.remove();
					}

				}
				
				return ships.isEmpty();
			}

			private boolean endOfScript() {
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

				System.out.println(ships.toString());

				while(!endOfScript() && execute(script[cursor], time)) {
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

				Integer shipID = Integer.parseInt(args[0]);
				
				Ship ship = Objects.requireNonNull(ships.get(shipID));
				
				ship.fireWeapon();

			}

			private void move(String... args) {

				int shipID = Integer.parseInt(args[0]);
				float shipX = Float.parseFloat(args[1]);
				float shipY = Float.parseFloat(args[2]);

			}

			private void spawn(String[] args) {

				Integer shipID = Integer.parseInt(args[0]);

				Ship ship = Objects.requireNonNull(ships.get(shipID));
				
				System.out.println(ship);
				spawns.add(shipID);
				
				ship.getBody().setActive(true);
				getContainer().push(ship);
				
			}
			
			private EntityContainer getContainer() {
				assert container != null;
				return container;
			}

		};
	}

}
