package fr.escape.input;

import java.util.List;

import fr.escape.app.Foundation;
import fr.umlv.zen2.MotionEvent;

public class BackOff implements Gesture {

	@Override
	public boolean accept(MotionEvent start, List<MotionEvent> events, MotionEvent end) {
		if(start.getY() >= end.getY()) return false;
		int height = Foundation.graphics.getHeight();
		int coeff = 150;
		int faultTolerence = 20;
		boolean valid = false;
		
  		double downCoeffDir = ((start.getY() + height)-start.getY())/((start.getX() + coeff)-start.getX());
  		double upCoeffDir = ((start.getY() + height)-start.getY())/((start.getX() - coeff)-start.getX());
  		
  		int div = end.getX()-start.getX();
  		double cd = (end.getY()-start.getY())/((div!=0)?div:1);
  		
  		if(cd < upCoeffDir || cd > downCoeffDir) {
  			valid = true;
  			double pUp = (end.getY() + faultTolerence) - (cd * (end.getX() + faultTolerence));
      		double pDown = (end.getY() - faultTolerence) - (cd * (end.getX() - faultTolerence));
      		for(MotionEvent event : events) {
      			double yUp = cd * event.getX() + pUp;
      			double yDown = cd * event.getX() + pDown;
      			if(yUp > event.getY() || yDown < event.getY()) return false;
      		}
  		}
		return valid;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
	}
	
}
