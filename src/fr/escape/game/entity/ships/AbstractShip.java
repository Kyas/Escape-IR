package fr.escape.game.entity.ships;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import fr.escape.app.Graphics;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;
import fr.escape.game.entity.weapons.Weapon;
import fr.escape.game.entity.weapons.shot.Shot;
import fr.escape.graphics.AnimationTexture;
import fr.escape.graphics.Shapes;

public abstract class AbstractShip implements Ship {
	
	private final Body body;
	private final List<Weapon> weapons;
	private final boolean isPlayer;
	
	private final EdgeNotifier eNotifier;
	private final KillNotifier kNotifier;
	
	private final AnimationTexture coreShip;
	
	private int activeWeapon;
	private boolean isWeaponLoaded;
	private boolean executeLeftLoop;
	private boolean executeRightLoop;
	
	public AbstractShip(Body body, List<Weapon> weapons, boolean isPlayer, EdgeNotifier eNotifier, KillNotifier kNotifier, AnimationTexture textures) {
		
		this.body = body;
		this.weapons = weapons;
		this.isPlayer = isPlayer;
		
		this.eNotifier = eNotifier;
		this.kNotifier = kNotifier;
		
		this.coreShip = textures;
		
		this.activeWeapon = 0;
		this.isWeaponLoaded = false;
		this.executeLeftLoop = false;
		this.executeRightLoop = false;
	}
	
	@Override
	public boolean isPlayer() {
		return isPlayer;
	}
	
	@Override
	public Weapon getActiveWeapon() {
		return weapons.get(activeWeapon);
	}
	
	@Override
	public List<Weapon> getAllWeapons() {
		return weapons;
	}
	
	@Override
	public void setActiveWeapon(int which) {
		if(which < 0 || which >= weapons.size()) {
			throw new IndexOutOfBoundsException();
		}
		getActiveWeapon().unload();
		this.isWeaponLoaded = false;
		this.activeWeapon = which;
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	public int getRadius() {
		return coreShip.getHeight() / 2;
	}
	
	@Override
	public float getX() {
		return body.getPosition().x;
	}
	
	@Override
	public float getY() {
		return body.getPosition().y;
	}
	
	@Override
	public void draw(Graphics graphics) {
		
		int x = CoordinateConverter.toPixelX(body.getPosition().x) - (coreShip.getWidth() / 2);
		int y = CoordinateConverter.toPixelY(body.getPosition().y) - (coreShip.getHeight() / 2);
		
		graphics.draw(coreShip, x, y, x + coreShip.getWidth(), y + coreShip.getHeight());
		getActiveWeapon().draw(graphics);
		graphics.draw(getEdge(), Color.RED);
		
		graphics.draw(Shapes.createCircle(CoordinateConverter.toPixelX(getX()),CoordinateConverter.toPixelY(getY()),CoordinateConverter.toPixelX(body.getFixtureList().getShape().m_radius)),Color.CYAN);
	}
	
	@Override
	public void update(Graphics graphics, long delta) {
		
		if(executeRightLoop || executeLeftLoop) {
			
			if(executeRightLoop) {
				coreShip.forward();
			} else {
				coreShip.reverse();
			}
			
			if(coreShip.hasNext()) {
				coreShip.next();
			} else {
				coreShip.rewind();
				executeLeftLoop = false;
				executeRightLoop = false;
			}
		}
		
		draw(graphics);
		
		if(!eNotifier.isInside(getEdge())) {
			eNotifier.edgeReached(this);
		}
	}
	
	@Override
	public boolean isWeaponLoaded() {
		return isWeaponLoaded;
	}
	
	@Override
	public boolean loadWeapon(World w, EntityContainer ec) {
		
		Weapon activeWeapon = getActiveWeapon();
		
		if(activeWeapon.load(w, ec, getX(), getY() - CoordinateConverter.toMeterY(coreShip.getHeight()))) {
			isWeaponLoaded = true;
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean fireWeapon(World world, EntityContainer ec, float[] velocity) {
		
		Weapon activeWeapon = getActiveWeapon();
		
		if(activeWeapon.fire(world, ec, velocity)) {
			isWeaponLoaded = false;
			return true;
		}
		
		return false;
	}
	
	@Override
	public void toDestroy() {
		if(kNotifier != null) kNotifier.destroy(this);
	}
	
	@Override
	public void setPosition(float x, float y) {
		body.setLinearVelocity(new Vec2(x - getX(),y - getY()));
	}
	
	@Override
	public void setPosition(Graphics graphics, float[] velocity) {
		if(body.isActive()) {			
			Shot shot = getActiveWeapon().getShot();
			
			int x = CoordinateConverter.toPixelX(body.getPosition().x);
			int y = CoordinateConverter.toPixelY(body.getPosition().y);
			int radius = getRadius();
			
			if(isPlayer() && (x <= radius || x >= graphics.getWidth() - radius || y <= (graphics.getHeight() * 2) / 3 + radius || y >= graphics.getHeight() - radius)) {
				velocity[0] = 0.1f;
				velocity[1] *= -1;
				velocity[2] *= -1;
			}
			
			float[] tmp = velocity;
			
			if(velocity[0] > 0) {
				body.setLinearVelocity(new Vec2(velocity[1],velocity[2]));
				velocity[0] -= Math.abs(Math.max(velocity[1],velocity[2]));
				
			} else {
				body.setLinearVelocity(new Vec2(0,0));
			}
			
			if(shot != null) {
				shot.setPosition(graphics,tmp);
			}
			
			draw(graphics);
		}
	}
	
	public void receive(int message) {
		switch(message) {
			case MESSAGE_EXECUTE_LEFT_LOOP: {
				
				System.err.println("TODO: Execute Left Loop");
				
				break;
			}
			case MESSAGE_EXECUTE_RIGHT_LOOP: {
				
				System.err.println("TODO: Execute Right Loop");
				
				break;
			}
		}
		
	}
	
	@Override
	public Rectangle getEdge() {
		int x = CoordinateConverter.toPixelX(getX());
		int y = CoordinateConverter.toPixelY(getY());
		return new Rectangle(x - (coreShip.getWidth() / 2), y - (coreShip.getHeight() / 2), coreShip.getWidth(), coreShip.getHeight());
	}
}