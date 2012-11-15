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
		
		boss = Objects.requireNonNull(factory.createBoss(BOSS_SPAWN_X, BOSS_SPAWN_Y));
		
		Scenario e1 = Foundation.RESOURCES.getScenario(ScenarioLoader.EARTH_1, factory);
		add(e1);
		
	}
	
	@Override
	public long getEstimatedScenarioTime() {
		return 12;
	}

	@Override
	protected Boss getBoss() {
		return boss;
	}
	
}
