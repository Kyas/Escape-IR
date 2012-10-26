package fr.escape.input;

import java.util.List;

import fr.escape.app.Input;

public class Drift implements Gesture {

	@Override
	public boolean accept(Input start, List<Input> events,Input end) {
		if(start.getY() <= end.getY()) return false;
		int faultTolerence = 20;
		boolean valid = false;
		boolean isRight = start.getX() < end.getX();
		
		double upCoeffDir, downCoeffDir;
		if(isRight) {
			upCoeffDir = -0.3;
			downCoeffDir = -1.7;
		} else {
			upCoeffDir = 1.7;
			downCoeffDir = 0.3;
		}
  		double cd = (double)(end.getY()-start.getY())/(end.getX()-start.getX());
  		
  		if(cd < upCoeffDir && cd > downCoeffDir) {
  			valid = true;
  			double pUp = (end.getY() + faultTolerence) - (cd * (end.getX() + faultTolerence));
      		double pDown = (end.getY() - faultTolerence) - (cd * (end.getX() - faultTolerence));
      		for(Input event : events) {
      			double yUp = cd * event.getX() + pUp;
      			double yDown = cd * event.getX() + pDown;
      			if(yUp < event.getY() || yDown > event.getY()) return false;
      		}
  		}
		return valid;
	}	
}
