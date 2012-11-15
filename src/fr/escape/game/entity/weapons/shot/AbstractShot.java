package fr.escape.game.entity.weapons.shot;

import java.awt.Rectangle;
import java.util.Objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import fr.escape.app.Foundation;
import fr.escape.game.User;
import fr.escape.game.entity.CollisionBehavior;
import fr.escape.game.entity.Entity;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;

//TODO Comment
public abstract class AbstractShot implements Shot {
	
	private static final int PLAYER_SHOT_MASK = NPC_TYPE;
	private static final int NPC_SHOT_MASK = PLAYER_TYPE;
	
	private final EdgeNotifier eNotifier;
	private final KillNotifier kNotifier;
	
	private CollisionBehavior collisionBehavior;
	
	private Body body;
	private int state;
	
	private int angle;
	private int damage;
	
	private int width;
	private int height;
	private boolean player;
	
	public AbstractShot(Body body, EdgeNotifier edgeNotifier, KillNotifier killNotifier, CollisionBehavior collisionBehavior, int defaultDamage) {
		
		this.body = Objects.requireNonNull(body);
		this.eNotifier = Objects.requireNonNull(edgeNotifier);
		this.kNotifier = Objects.requireNonNull(killNotifier);
		this.collisionBehavior = Objects.requireNonNull(collisionBehavior);
		
		this.angle = 0;
		this.damage = defaultDamage;
		
		this.state = MESSAGE_LOAD;
	}

	@Override
	public void rotateBy(int angle) {
		this.setRotation(this.angle + angle);
	}
	
	@Override
	public void setPosition(float x, float y) {
		getBody().setTransform(new Vec2(x, y), getBody().getAngle());
	}
	
	@Override
	public void moveTo(float x, float y) {
		throw new UnsupportedOperationException();
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
		this.angle = angle % 360;
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	@Override
	public void setBody(Body body) {
		this.body = body;
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
	public int getDamage() {
		return damage;
	}
	
	@Override
	public void collision(final User user, int whoami, final Entity e, final int whois) {
		Foundation.ACTIVITY.post(new Runnable() {
			
			@Override
			public void run() {
				getCollisionBehavior().applyCollision(user, AbstractShot.this, e, whois);
			}
			
		});
	}
	
	CollisionBehavior getCollisionBehavior() {
		return collisionBehavior;
	}
	
	@Override
	public boolean setShotConfiguration(ShotContext configuration) {
		
		int mask = (Objects.requireNonNull(configuration).isPlayer())?PLAYER_SHOT_MASK:NPC_SHOT_MASK;
		Objects.requireNonNull(getBody().getFixtureList()).m_filter.maskBits = mask;
		
		this.width = configuration.getWidth();
		this.height = configuration.getHeight();
		this.player = configuration.isPlayer();
		
		return true;
	}
	
	int getWidth() {
		return width;
	}

	int getHeight() {
		return height;
	}
	
	boolean isPlayer() {
		return player;
	}
	
	@Override
	public void setUntouchable() {
		Objects.requireNonNull(getBody().getFixtureList()).m_filter.maskBits = 0x0001;
	}
	
}
