package fr.escape.game.entity.weapons.shot;

import java.awt.Rectangle;
import java.util.Objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;

import fr.escape.app.Foundation;
import fr.escape.game.User;
import fr.escape.game.entity.Entity;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;

public abstract class AbstractShot implements Shot {
	
	private static final int PLAYER_SHOT_MASK = NPC_TYPE;
	private static final int NPC_SHOT_MASK = PLAYER_TYPE;
	private static final String TAG = AbstractShot.class.getSimpleName();
	
	private final EdgeNotifier eNotifier;
	private final KillNotifier kNotifier;
	private final Body body;
	private int state;
	
	private int angle;
	private int damage;
	
	public AbstractShot(Body body, EdgeNotifier edgeNotifier, KillNotifier killNotifier,int defaultDamage) {
		
		this.body = Objects.requireNonNull(body);
		this.eNotifier = Objects.requireNonNull(edgeNotifier);
		this.kNotifier = Objects.requireNonNull(killNotifier);
		
		this.angle = 0;
		this.damage = defaultDamage;
		
		this.state = MESSAGE_LOAD;
		
	}

	@Override
	public void rotateBy(int angle) {
		this.setRotation(this.angle + angle);
	}
	
	@Override
	public void moveTo(float x, float y) {
		System.out.println(x + " " + y);
		getBody().setLinearVelocity(new Vec2(x,y));
	}
	
	@Override
	public void moveBy(float[] velocity) {
		if(body.isActive()) {
			setRotation((int)velocity[0]);
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

	protected EdgeNotifier getEdgeNotifier() {
		return eNotifier;
	}
	
	@Override
	public void collision(User user, int whoami, Entity e, int whois) {
		getBody().setType(BodyType.STATIC);
		this.receive(MESSAGE_HIT);
		switch(whois) {
			case PLAYER_TYPE: {
				// TODO
				Foundation.ACTIVITY.debug(TAG, "Shot touch Player");
				System.err.println("Player lost a life");
				break;
			}
			case NPC_TYPE: {
				e.toDestroy();
				Foundation.ACTIVITY.debug(TAG, "Shot touch NPC");
				break;
			}
			default: {
				Foundation.ACTIVITY.error(TAG, "Unknown touch contact {"+this+", "+e+"}");
				break;
			}
		}
	}
	
	@Override
	public void setFireMask(boolean isPlayer) {
		int mask = (isPlayer)?PLAYER_SHOT_MASK:NPC_SHOT_MASK;
		Objects.requireNonNull(getBody().getFixtureList()).m_filter.maskBits = mask;
	}
	
}
