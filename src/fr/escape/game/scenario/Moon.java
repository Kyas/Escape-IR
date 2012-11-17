package fr.escape.game.scenario;

import java.util.Objects;

import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.ships.Boss;
import fr.escape.game.entity.ships.ShipFactory;
import fr.escape.resources.scenario.ScenarioLoader;

public class Moon extends AbstractStage {

	private static final float BOSS_SPAWN_X = 5.0f;
	private static final float BOSS_SPAWN_Y = 0.0f;
	
	private final Boss boss;

	public Moon(World world, EntityContainer container, ShipFactory factory) {
		
		super(world, container);
		
		boss = Objects.requireNonNull(factory.createMoonBoss(BOSS_SPAWN_X, BOSS_SPAWN_Y));
		
		add(Foundation.RESOURCES.getScenario(ScenarioLoader.MOON_1, factory));
		add(Foundation.RESOURCES.getScenario(ScenarioLoader.MOON_2, factory));
		add(Foundation.RESOURCES.getScenario(ScenarioLoader.MOON_3, factory));
	}
	
	@Override
	public long getEstimatedScenarioTime() {
		return 55;
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
