package fr.escape.resources.scenario;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import fr.escape.game.entity.ships.Ship;

class ScenarioConfiguration {
	
	private int id;
	private int time;
	private HashMap<Integer, Ship> ships;
	private List<String> script;
	
	ScenarioConfiguration() {
		id = 0;
		time = 0;
		ships = new HashMap<>();
		script = new LinkedList<>();
	}
	
	public int getID() {
		return id;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void addShip(int shipID, Ship ship) {
		ships.put(shipID, Objects.requireNonNull(ship));
	}
	
	public void addScript(String line) {
		script.add(Objects.requireNonNull(line));
	}
	
	public String[] getScript() {
		return script.toArray(new String[0]);
	}

	public HashMap<Integer, Ship> getShip() {
		HashMap<Integer, Ship> map = ships;
		ships = null;
		return map;
	}
	
}