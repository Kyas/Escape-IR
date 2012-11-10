package fr.escape.input;

import java.util.List;

import fr.escape.app.Input;
import fr.escape.game.entity.CoordinateConverter;

// TODO Comment
public final class WeaponGesture implements Gesture {

	@Override
	public boolean accept(Input start, List<Input> events, Input end, float[] velocity) {
		if(start.getY() <= end.getY()) return false;
		
		float cd = (end.getX() == start.getX())
				?((end.getY() - start.getY()) / 1.0f)
				:((end.getY() - start.getY()) / (end.getX() - start.getX()));
		
		System.out.println(cd);
		
		float distanceX = CoordinateConverter.toMeterX(end.getX() - start.getX()) / 10;
		float distanceY = CoordinateConverter.toMeterX(end.getY() - start.getY()) / 10;
				
  		//TODO calcul velocity
  		velocity[0] = 1000.f;
  		velocity[1] = distanceX;
  		velocity[2] = distanceY;
  		
  		return true;
	}

}
