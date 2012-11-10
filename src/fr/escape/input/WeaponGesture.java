package fr.escape.input;

import java.util.List;

import fr.escape.app.Input;
import fr.escape.game.entity.CoordinateConverter;

// TODO Comment
public final class WeaponGesture implements Gesture {
	private static int COEFFICIENT = 5;
	
	@Override
	public boolean accept(Input start, List<Input> events, Input end, float[] velocity) {
		if(start.getY() <= end.getY()) return false;
				
		float distanceX = CoordinateConverter.toMeterX(end.getX() - start.getX());
		float distanceY = CoordinateConverter.toMeterX(end.getY() - start.getY());
		
		velocity[0] = 1000.f;
		float max = Math.max(Math.abs(distanceX), Math.abs(distanceY));
		float coeff = COEFFICIENT / max;
		
		velocity[1] = distanceX * coeff;
		velocity[2] = distanceY * coeff;
				
  		return true;
	}

}
