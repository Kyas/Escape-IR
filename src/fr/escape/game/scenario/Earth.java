package fr.escape.game.scenario;

import java.util.Objects;

import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.ships.Boss;
import fr.escape.game.entity.ships.ShipFactory;
import fr.escape.resources.scenario.ScenarioLoader;

public final class Earth extends AbstractStage {
	
	private static final float BOSS_SPAWN_X = 5.0f;
	private static final float BOSS_SPAWN_Y = 0.0f;
	
	private final Boss boss;

	public Earth(World world, EntityContainer container, ShipFactory factory) {
		
		super(world, container); 
		
		boss = Objects.requireNonNull(factory.createEarthBoss(BOSS_SPAWN_X, BOSS_SPAWN_Y));
		
		add(Foundation.RESOURCES.getScenario(ScenarioLoader.EARTH_1, factory));
		add(Foundation.RESOURCES.getScenario(ScenarioLoader.EARTH_2, factory));
		add(Foundation.RESOURCES.getScenario(ScenarioLoader.EARTH_3, factory));
		add(Foundation.RESOURCES.getScenario(ScenarioLoader.EARTH_4, factory));
	}
	
	@Override
	public long getEstimatedScenarioTime() {
		return 52;
	}

	@Override
	protected Boss getBoss() {
		return boss;
	}
	
	@Override
	public void resetBoss() {
		boss.reset(BOSS_SPAWN_X, BOSS_SPAWN_Y);
	}
	
}
