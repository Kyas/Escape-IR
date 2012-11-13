package fr.escape.game.scenario;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import fr.escape.game.entity.EntityContainer;

public abstract class AbstractStage implements Stage {

	private final List<Scenario> active;
	private final TreeMap<Integer, Scenario> waiting;
	private final EntityContainer container;
	
	private int lastTime;
	
	public AbstractStage(EntityContainer container) {
		this.container = container;
		this.active = new LinkedList<>();
		this.waiting = new TreeMap<>();
		this.lastTime = -1;
	}
	
	private List<Scenario> getActiveScenario() {
		return active;
	}
	
	protected TreeMap<Integer, Scenario> getWaitingScenario() {
		return waiting;
	}
	
	private int getLastUpdateTime() {
		return lastTime;
	}
	
	private void setLastUpdateTime(int updateTime) {
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
			scenario.setContainer(container);
			active.add(scenario);
			it.remove();
		}
		
	}
	
}
