package fr.escape.input;

import java.util.List;

import fr.escape.app.Input;

// TODO Comment
public final class WeaponGesture implements Gesture {

	@Override
	public boolean accept(Input start, List<Input> events, Input end, float[] velocity) {
		if(start.getY() <= end.getY()) return false;
		
		float cd = (end.getY() - start.getY()) / (end.getX() - start.getX());
		System.out.println(cd);
		
  		//TODO calcul velocity
  		velocity[0] = 1000.f;
  		velocity[1] = 0.f;
  		velocity[2] = -1.f;
  		
  		return true;
	}

}
