package fr.escape.input;

import java.util.List;

import fr.escape.app.Input;

public class Drift implements Gesture {

	@Override
	public double accept(Input start, List<Input> events,Input end) {
		if(start.getY() <= end.getY()) return 0;
		int faultTolerence = 20;
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

  		if(downCoeffDir < cd && cd < upCoeffDir) {
  			double pUp = (end.getY() + faultTolerence) - (cd * (end.getX() + faultTolerence));
      		double pDown = (end.getY() - faultTolerence) - (cd * (end.getX() - faultTolerence));
      		for(Input event : events) {
      			double yUp = cd * event.getX() + pUp;
      			double yDown = cd * event.getX() + pDown;
      			System.out.println(yUp + " " + event.getY() + " " + yDown);
      			if(yUp < event.getY() || yDown > event.getY()) return 0;
      		}
  		} else {
  			return 0;
  		}
		return cd;
	}	
}
