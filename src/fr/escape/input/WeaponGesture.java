package fr.escape.input;

import java.util.List;

import fr.escape.app.Input;

// TODO Comment
public final class WeaponGesture implements Gesture {

	@Override
	public boolean accept(Input start, List<Input> events, Input end, float[] velocity) {
		if(start.getY() <= end.getY()) return false;
		
  		//TODO calcul velocity
  		velocity[0] = 0;
  		velocity[1] = 0;
  		velocity[2] = 0;
  		
  		return true;
	}

}
