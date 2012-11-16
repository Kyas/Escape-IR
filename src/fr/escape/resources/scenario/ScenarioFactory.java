package fr.escape.resources.scenario;

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
			
			private final String tag = "Scenario#"+scenario.getID();
			
			private final int id = scenario.getID();
			private final int start = scenario.getTime();
			private final HashMap<Integer, Ship> ships = scenario.getShip();
			private final HashSet<Integer> spawns = new HashSet<>();
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

				// Check if we need to remove some Ship from Scenario
				Iterator<Entry<Integer, Ship>> it = ships.entrySet().iterator();
				while (it.hasNext()) {

					Entry<Integer, Ship> row = it.next();

					if(spawns.contains(row.getKey()) && !getContainer().contains(row.getValue())) {
						Foundation.ACTIVITY.debug(tag, "Remove "+row.getValue());
						spawns.remove(row.getKey());
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

				Integer shipID = Integer.valueOf(args[0]);
				
				Ship ship = selectShip(shipID);
				
				if(ship != null) {
					ship.fireWeapon();
				}

			}

			private void move(String... args) {

				Integer shipID = Integer.valueOf(args[0]);
				float shipX = Float.parseFloat(args[1]);
				float shipY = Float.parseFloat(args[2]);
				
				Ship ship = selectShip(shipID);
				
				if(ship != null) {
					ship.moveTo(shipX, shipY);
				}
				
			}

			private void spawn(String[] args) {

				Integer shipID = Integer.valueOf(args[0]);

				Ship ship = fetchShip(shipID);
				
				spawns.add(shipID);
				getContainer().pushShip(ship);
				
			}
			
			/**
			 * <p>
			 * Select Ship in EntityContainer.
			 * 
			 * @param shipID The Ship ID
			 * @return Ship
			 */
			private Ship selectShip(Integer shipID) {
				
				Ship ship = fetchShip(shipID);
				
				if(spawns.contains(shipID)) {
					return ship;
				}
				
				return null;
			}
			
			/**
			 * <p>
			 * Fetch Ship from Collection.
			 * 
			 * <p>
			 * This is unattached with EntityContainer and Game state.
			 * 
			 * @param shipID The Ship ID
			 * @return Ship
			 */
			private Ship fetchShip(Integer shipID) {
				return Objects.requireNonNull(ships.get(shipID));
			}
			
			private EntityContainer getContainer() {
				assert container != null;
				return container;
			}

			@Override
			public boolean reset() {
				cursor = 0;
				spawns.clear();
				return false;
			}

		};
	}

}
