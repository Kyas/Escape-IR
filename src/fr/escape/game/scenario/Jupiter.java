package fr.escape.game.scenario;

import java.util.Objects;

import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.ships.Boss;
import fr.escape.game.entity.ships.ShipFactory;
import fr.escape.resources.scenario.ScenarioLoader;

public class Jupiter extends AbstractStage {
	
	private static final float BOSS_SPAWN_X = 5.0f;
	private static final float BOSS_SPAWN_Y = 0.0f;
	
	private final Boss boss;

	public Jupiter(World world, EntityContainer container, ShipFactory factory) {
		
		super(world, container);
		
		boss = Objects.requireNonNull(factory.createJupiterBoss(BOSS_SPAWN_X, BOSS_SPAWN_Y));
	
		add(Foundation.RESOURCES.getScenario(ScenarioLoader.JUPITER_1, factory));
		add(Foundation.RESOURCES.getScenario(ScenarioLoader.JUPITER_2, factory));
		
	}
	
	@Override
	public long getEstimatedScenarioTime() {
		return 75;
	}

	@Override
	protected Boss getBoss() {
		return boss;
	}

}
