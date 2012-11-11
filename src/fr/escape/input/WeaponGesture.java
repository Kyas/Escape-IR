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
		
		double cd = (double) (end.getY() - start.getY()) / (end.getX() - start.getX());
		float angle = - (float) (180 * (Math.atan(cd) - Math.atan(0)) / Math.PI);
		
		velocity[0] = (angle < 0)?270-angle:90-angle;
		float max = Math.max(Math.abs(distanceX), Math.abs(distanceY));
		float coeff = COEFFICIENT / max;
		
		velocity[1] = distanceX * coeff;
		velocity[2] = distanceY * coeff;
				
  		return true;
	}

}
