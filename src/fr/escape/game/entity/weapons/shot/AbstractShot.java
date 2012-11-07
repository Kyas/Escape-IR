package fr.escape.game.entity.weapons.shot;

import java.awt.Rectangle;
import java.util.Objects;

import org.jbox2d.dynamics.Body;

import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;
import fr.escape.game.message.Receiver;

public abstract class AbstractShot implements Shot {
	
	public final static int MESSAGE_LOAD = 0;
	public final static int MESSAGE_FIRE = 1;
	public final static int MESSAGE_CRUISE = 2;
	public final static int MESSAGE_HIT = 3;
	public final static int MESSAGE_DESTROY = 4;
	
	private final EdgeNotifier eNotifier;
	private final KillNotifier kNotifier;
	private final Body body;
	
	private int x;
	private int y;
	private int angle;
	
	public AbstractShot(Body body, EdgeNotifier edgeNotifier, KillNotifier killNotifier) {
		
		this.body = body;
		this.eNotifier = Objects.requireNonNull(edgeNotifier);
		this.kNotifier = Objects.requireNonNull(killNotifier);
		
		this.x = 0;
		this.y = 0;
		this.angle = 0;
	}

	/**
	 * <p>
	 * Shot have different state depending on the situation.
	 * 
	 * <p>
	 * If you need to change its state, use this method with the given protocol:
	 * 
	 * <ul>
	 * <li>MESSAGE_LOADED: Shot loaded in Ship.</li>
	 * <li>MESSAGE_FIRE: Shot just shoot from Ship.</li>
	 * <li>MESSAGE_CRUISE: Shot in cruise state.</li>
	 * <li>MESSAGE_HIT: Shot hit something.</li>
	 * <li>MESSAGE_DESTROY: Shot need to be destroyed.</li>
	 * </ul>
	 * 
	 * <b>By default:</b> state is 0.
	 * 
	 * @see Receiver#receive(int)
	 */
	@Override
	public abstract void receive(int message);
	
	@Override
	public void moveBy(int x, int y) {
		this.setPosition(this.x + x, this.y + y);
	}

	@Override
	public void rotateBy(int angle) {
		this.setRotation(this.angle + angle);
	}
	
	@Override
	public void setPosition(int x, int y) {
		
		this.x = x;
		this.y = y;
		
		if(!eNotifier.getEdge().intersects(getEdge())) {
			eNotifier.edgeReached(this);
		}
	}
	
	@Override
	public void setRotation(int angle) {
		angle = angle % 360;
		this.angle = angle;
	}
	
	
//	@Override
//	public void draw(Graphics graphics) {
//		graphics.draw(drawable, (getX() - (drawable.getWidth() / 2)), (getY() - (drawable.getHeight() / 2)), getAngle());
//	}
	
	public Body getBody() {
		return body;
	}
	
	protected int getX() {
		return this.x;
	}
	
	protected int getY() {
		return this.y;
	}
	
	protected int getAngle() {
		return this.angle;
	}
	
	protected void destroy() {
		kNotifier.destroy(this);
	}
	
	protected abstract Rectangle getEdge(); 
	
}
