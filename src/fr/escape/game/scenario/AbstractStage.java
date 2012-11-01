package fr.escape.game.scenario;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public abstract class AbstractStage implements Stage {

	private final List<Scenario> active;
	private final TreeMap<Integer, Scenario> waiting;
	
	private int lastTime;
	
	public AbstractStage() {
		active = new LinkedList<>();
		waiting = new TreeMap<>();
		lastTime = -1;
	}
	
	public List<Scenario> getActiveScenario() {
		return active;
	}
	
	public TreeMap<Integer, Scenario> getWaitingScenario() {
		return waiting;
	}
	
	public int getLastUpdateTime() {
		return lastTime;
	}
	
	public void setLastUpdateTime(int updateTime) {
		lastTime = updateTime;
	}
	
	@Override
	public void start() {
		update(0);
	}
	
	@Override
	public void update(int time) {
		if(time != getLastUpdateTime()) {
		
			fetchActivableScenario(time);
			
			Iterator<Scenario> it = getActiveScenario().iterator();
			
			while(it.hasNext()) {
				
				Scenario sc = it.next();
				
				if(sc.hasFinished()) {
					it.remove();
				} else {
					sc.action(time);
				}
			}
			
			setLastUpdateTime(time);
		}
	}
	
	private Collection<Scenario> getListOfActivableScenario(int time) {
		return waiting.headMap(Integer.valueOf(time), true).values();
	}
	
	private void fetchActivableScenario(int time) {

		Iterator<Scenario> it = getListOfActivableScenario(time).iterator();
		
		while(it.hasNext()) {
			Scenario scenario = it.next();
			active.add(scenario);
			it.remove();
		}
		
	}
}
