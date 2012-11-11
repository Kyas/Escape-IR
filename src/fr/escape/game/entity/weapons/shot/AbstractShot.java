package fr.escape.game.entity.weapons.shot;

import java.awt.Rectangle;
import java.util.Objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;

public abstract class AbstractShot implements Shot {
	
	private final EdgeNotifier eNotifier;
	private final KillNotifier kNotifier;
	private final Body body;
	private int state;
	
	private int x;
	private int y;
	private int angle;
	
	public AbstractShot(Body body, EdgeNotifier edgeNotifier, KillNotifier killNotifier) {
		
		this.body = Objects.requireNonNull(body);
		this.eNotifier = Objects.requireNonNull(edgeNotifier);
		this.kNotifier = Objects.requireNonNull(killNotifier);
		
		this.angle = 0;
		
		this.state = MESSAGE_LOAD;
		
	}

	@Override
	public void rotateBy(int angle) {
		this.setRotation(this.angle + angle);
	}
	
	@Override
	public void moveTo(float x, float y) {
		
		/*this.x = x;
		this.y = y;
		
		if(!eNotifier.isInside(getEdge())) {
			eNotifier.edgeReached(this);
		}*/
	}
	
	@Override
	public void moveBy(float[] velocity) {
		if(body.isActive()) {
			body.setLinearVelocity(new Vec2(velocity[1], velocity[2]));
		}
	}
	
	@Override
	public void setRotation(int angle) {
		angle = angle % 360;
		this.angle = angle;
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	protected float getX() {
		return getBody().getPosition().x;
	}
	
	protected float getY() {
		return getBody().getPosition().y;
	}
	
	protected int getAngle() {
		return this.angle;
	}
	
	@Override
	public void toDestroy() {
		kNotifier.destroy(this);		
	}
	
	@Override
	public int getState() {
		return state;
	}
	
	public  void setState(int state) {
		this.state = state;
	}
	
	protected abstract Rectangle getEdge();

	public EdgeNotifier getEdgeNotifier() {
		return eNotifier;
	} 
	
}
