package fr.escape.game.scenario;

import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.ships.Ship;
import fr.escape.game.entity.ships.ShipFactory;
import fr.escape.resources.scenario.ScenarioLoader;

public final class Earth extends AbstractStage {
	private final Ship boss;

	public Earth(ShipFactory factory, EntityContainer container) {
		
		super(container);
	
		Scenario e1 = Foundation.RESOURCES.getScenario(ScenarioLoader.EARTH_1, factory);
		
		add(e1);
		
		boss = factory.createBoss(5.0f, 2.0f);
		
//		getWaitingScenario().put(Integer.valueOf(2), new Scenario() {
//			
//			@Override
//			public int getStart() {
//				return 2;
//			}
//			
//			@Override
//			public boolean hasFinished() {
//				return false;
//			}
//			
//			@Override
//			public void action(int time) {
//				System.out.println(time+" "+this);
//			}
//			
//			public String toString() {
//				return getID()+" -> "+getStart();
//			}
//
//			@Override
//			public int getID() {
//				return 2;
//			}
//			
//		});
	}

	@Override
	public void boss(World world, EntityContainer container) {
		boss.createBody(world);
		container.push(boss);
	}
	
}
